# An adaption of Dr. Stonedahl's code (with some debugging from ChatGPT)
# All of the print statements came from ChatGPT to help debug.
import nest_asyncio
import uvicorn
from fastapi import FastAPI, WebSocket, WebSocketDisconnect
from fastapi.middleware.cors import CORSMiddleware

# Apply nest_asyncio to patch the current running event loop
nest_asyncio.apply()

app = FastAPI()

# Dictionary to store connected WebSocket clients
connected_users = {}

# Add CORS middleware
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # Allows all origins
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

@app.websocket("/ws/{user_id}")
async def websocket_endpoint(user_id: str, websocket: WebSocket):
    await websocket.accept()
    connected_users[user_id] = websocket
    print(f"User {user_id} connected")

    try:
        while True:
            data = await websocket.receive_json()  # Receive the JSON message from the web client
            print(f"Received message from {user_id}: {data}")

            # Broadcast the received message to other connected users
            for other_user, other_ws in connected_users.items():
                if other_user != user_id:
                    await other_ws.send_json(data)  # Send the message to other users
    except WebSocketDisconnect:
        print(f"User {user_id} disconnected")
        del connected_users[user_id]  # Remove user from connected list
        await websocket.close()
    except Exception as e:
        print(f"Unexpected error: {e}")
        await websocket.close()

# Run the FastAPI app using uvicorn
if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)