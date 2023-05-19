package com.example.jvm2_quizz.webSocketUtil;

import com.example.jvm2_quizz.models.Message;
import com.google.gson.Gson;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;

public class MessageEncoder implements Encoder.Text<Message> {
    private static Gson gson = new Gson();

    @Override
    public String encode(Message message) throws EncodeException {
        return gson.toJson(message);
    }
}
