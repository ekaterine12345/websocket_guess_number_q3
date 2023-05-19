package com.example.jvm2_quizz.webSocketUtil;

import com.example.jvm2_quizz.models.Message;
import jakarta.websocket.server.ServerEndpoint;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(
        value = "/chat/{username}",
        decoders = MessageDecoder.class,
        encoders = MessageEncoder.class
)
public class WebEndpoint {
    private Session session;

    private static Set<WebEndpoint> webEndpoints = new CopyOnWriteArraySet<>();

    private static HashMap<String, String> users = new HashMap<>();

    private static Map<Session, Integer> numberMap = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username ) throws EncodeException, IOException {
        this.session = session;
        users.put(session.getId(), username);
        webEndpoints.add(this);

        Message message = new Message();
        message.setFrom(username);
        message.setContent(username + " Welcome to my Guess Number Game app! range => [1,100]");
        //message.setContent("Connected...");

        int number = generateRandomNumber();
        numberMap.put(session, number);
        sendMessage(session, message);
        // broadcast(message);
    }


    @OnMessage
    public void onMessage(Session session, Message message) throws EncodeException, IOException {
        String username = users.get(session.getId());
        message.setFrom(username);

        int guess_input = Integer.parseInt(message.getContent());
        int number = numberMap.get(session);

        if (guess_input == number){
            message.setContent("YOU guessed the number "+guess_input);
            sendMessage(session, message);
            closeSession(session);
        }
        else
        if (guess_input > number){
            message.setContent("NOO! "+guess_input+" is to High! Try lower number");
            sendMessage(session, message);
        }
        else{
            message.setContent("NOO! "+guess_input+" is to Low! Try higher number");
            sendMessage(session, message);
        }

        // broadcast(message);
    }

    @OnClose
    public void onClose(Session session){

        Message message = new Message();
        String username = users.get(session.getId());


        message.setFrom(username);
        message.setContent("Disconnected...");

        webEndpoints.remove(this);
        numberMap.remove(session);
      //  broadcast(message);
    }


    private void closeSession(Session session) throws IOException {
        session.close();
    }
    private void sendMessage(Session session, Message message) throws EncodeException, IOException {
        session.getBasicRemote().sendObject(message);
    }
    private int generateRandomNumber(){
        Random random = new Random();
        return random.nextInt(100)+1;
    }

//    private void broadcast(Message message) {
//        webEndpoints.forEach(webEndpoint -> {
//            synchronized (webEndpoint){
//                try{
//                    webEndpoint.session.getBasicRemote().sendObject(message);
//                } catch (EncodeException | IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//    }

}
