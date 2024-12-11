package edu.augustana;

import javafx.application.Platform;
import org.json.JSONObject;

import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;

public class WebSocketListener implements WebSocket.Listener{

    private MultiplayerModeController controller;

    public WebSocketListener(MultiplayerModeController controller) {
        this.controller = controller;
    }

    @Override
    public void onOpen(WebSocket webSocket) {
        System.out.println("WebSocket connection opened");
        webSocket.request(1);
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        System.out.println("Received message: " + data);

        // Initialize sender and text with default error values
        String sender = "Unknown";
        String text = "Invalid message";

        try {
            // Parse the JSON message
            JSONObject jsonMessage = new JSONObject(data.toString());
            sender = jsonMessage.getString("sender");
            text = jsonMessage.getString("text");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String finalSender = sender;
        String finalText = text;
        Platform.runLater(() -> {

            // Update the MainMessageBox with the formatted message
            String formattedMessage = finalSender + ": " + finalText;
            controller.updateMainMessage("Server", formattedMessage);

        });

        webSocket.request(1);
        return null;
    }

    @Override
    public void onError(WebSocket webSocket, Throwable error) {
        System.err.println("WebSocket error: " + error.getMessage());
    }

    @Override
    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        System.out.println("WebSocket closed: " + reason);
        return WebSocket.Listener.super.onClose(webSocket, statusCode, reason);
    }
}


