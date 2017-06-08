# chat.py

import signal
from curio import run, spawn, SignalQueue, TaskGroup, Queue, tcp_server, CancelledError
from curio.socket import *
import selectors
from socket import AF_INET, SOCK_DGRAM
import json

dummy_socket = socket(AF_INET, SOCK_DGRAM)

messages = Queue()
users = { }
# messages = Queue((to:int, from:int, msg:bytes) ...)
#
# usrs {
#   int: {
#       'usrs'        : set(Queue((from:int, msg:bytes) ...) ...)
#       'max_players' : int
#       'size'        : (int, int)
#       'grid'        : [[int ...] ...]
#   } ...
# }

IP = "127.0.0.1"
PORT = 25000

MSG_STRUCKTURES = {
    "host": ["max_players", "size"],
    "join": ["id"]
}

def makejsonresponse(success, msg):
    response = {
        "success": success,
        "msg": msg
    }
    return json.dumps(response).encode("ascii") + b'\n'

def valid_json(json, msg_type):
    return all([key in json for key in MSG_STRUCKTURES[msg_type]])

async def dispatcher():
    async for msg in messages:
        if msg[0] in users:
            for msg_queue in users[msg[0]]['usrs']:
                await msg_queue.put(msg[1:])

async def publish(msg):
    await messages.put(msg)

# Task that writes chat messages to clients
async def outgoing(client_stream, queue, usr_id):
    try:
        async for from_id, msg in queue:
            if from_id != usr_id:
                await client_stream.write(str(from_id).encode('ascii') + b': ' + msg)
    except ConnectionResetError:
        pass

# Task that reads chat messages and publishes them
async def incoming(client_stream, usr_id, too):
    try:
        async for line in client_stream:
            await publish((too, usr_id, line))
    except CancelledError:
        await client_stream.write(b'SERVER IS GOING AWAY!\n')
        raise
    except ConnectionResetError:
        pass

# Supervisor task for each connection
async def chat_handler(client, addr):
    print('Connection from', addr)
    async with client:
        client_stream = client.as_stream()
        #await client_stream.write(b'Action: ')
        try:
            json_obj = json.loads((await client_stream.readline()).strip())
        except json.JSONDecodeError as e:
            await client_stream.write(makejsonresponse(False, str(e)))
            return
        queue = Queue()
        too = 0
        if json_obj['action'] == 'host':
            if not valid_json(json_obj, 'host'):
                await client_stream.write(makejsonresponse(False, "Invalid json format"))
                print("Closing connection")
                return

            await client_stream.write(makejsonresponse(True, addr[1]))
            users[addr[1]] = {
                                'usrs': {queue},
                                'max_players': json_obj['max_players'],
                                'size': tuple(json_obj['size']),
                                'grid': [[-1 for w in range(json_obj['size'][0])] for h in range(json_obj['size'][1])]
                            }
            too = addr[1]
        elif json_obj['action'] == 'join':
            if not valid_json(json_obj, 'join'):
                await client_stream.write(makejsonresponse(False, "Invalid json format"))
                print("Closing connection")
                return
            too = json_obj['id']
            if too in users and len(users[too]['usrs']) < users[too]['max_players']:
                users[too]['usrs'].add(queue)
            else:
                await client_stream.write(makejsonresponse(False, "No game with given id or game is full"))
                return
            await client_stream.write(makejsonresponse(True, "Joined game!"))
        else:
            await client_stream.write(makejsonresponse(False, "Invalid action"))

        async with TaskGroup(wait=any) as workers:
            await workers.spawn(outgoing, client_stream, queue, addr[1])
            await workers.spawn(incoming, client_stream, addr[1], too)
        users[too]["usrs"].discard(queue)
    print('Connection closed')

async def chat_server(host, port):
    async with TaskGroup() as g:
        await g.spawn(dispatcher)
        await g.spawn(tcp_server, host, port, chat_handler)

async def main(host, port):
    async with SignalQueue(signal.SIGINT) as restart:
        #while True:
        print('Starting the server')
        serv_task = await spawn(chat_server, host, port)
        await restart.get()
        print('Server shutting down')
        await serv_task.cancel()

if __name__ == '__main__':
    selector = selectors.DefaultSelector()
    selector.register(dummy_socket, selectors.EVENT_READ)
    run(main(IP, PORT), with_monitor=True, selector=selector)
