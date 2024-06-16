package websocket;

import bean.MessageBean;
import bean.NotificationBean;
import bean.TokenBean;
import bean.UserBean;
import com.google.gson.Gson;
import dto.LastMessageDto;
import dto.MessageDto;
import dto.MessageUserDto;
import entities.TokenEntity;
import entities.UserEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import service.ObjectMapperContextResolver;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Singleton
@ServerEndpoint("/websocket/messages/{token}/{id}")
public class Messages {
    @EJB
    private MessageBean messageBean;

    @EJB
    private NotificationBean notificationBean;
    @EJB
    private UserBean userBean;
    @Inject
    Notifications notifications;
    @EJB
    private TokenBean tokenBean;
    HashMap<String, Session> sessions = new HashMap<String, Session>();
    private ObjectMapperContextResolver contextResolver = new ObjectMapperContextResolver();
    Gson gson = new Gson();
    public void send(String conervsationToken, String msg) {
        Session session = sessions.get(conervsationToken);
        if (session != null) {
            System.out.println("sending.......... " + msg);
            try {
                session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                System.out.println("Something went wrong!");
            }
        }
    }
    @OnOpen
    public void toDoOnOpen(Session session, @PathParam("token") String token, @PathParam("id") int id) {
        System.out.println("A new WebSocket session is opened for client with token: " + token);
        String conversationToken = token + "/" + id;
        sessions.put(conversationToken, session);
    }

    @OnClose
    public void toDoOnClose(Session session, CloseReason reason) {
        System.out.println("Websocket session is closed with CloseCode: " + reason.getCloseCode() + ": " + reason.getReasonPhrase());
        for (String key : sessions.keySet()) {
            if (sessions.get(key) == session)
                sessions.remove(key);
        }
    }
    @OnMessage
    public void toDoOnMessage( String message, @PathParam("token") String token, @PathParam("id") int id) {
        UserEntity sender = userBean.findUserByToken(token);
        UserEntity receiver = userBean.findUserById(id);
        if (sender != null && receiver != null) {
            MessageUserDto senderDto = new MessageUserDto(sender);
            MessageUserDto receiverDto = new MessageUserDto(receiver);
            MessageDto messageDto = new MessageDto(message, senderDto, receiverDto);
            messageBean.createMessage(messageDto);
            List<TokenEntity> senderTokens = tokenBean.findActiveTokensByUser(sender);
            List<TokenEntity> receiverTokens = tokenBean.findActiveTokensByUser(receiver);

            for (TokenEntity receiverToken : receiverTokens) {
                if(notifications.sessions.containsKey(receiverToken.getToken())) {
                    LastMessageDto lastMessageDto = new LastMessageDto(senderDto, message);
                    String lastMessageJson = gson.toJson(lastMessageDto);
                    notifications.send(receiverToken.getToken(), lastMessageJson);

                    String conversationToken = receiverToken.getToken() + "/" + sender.getId();
                    if (sessions.containsKey(conversationToken)) {
                        messageDto.setIsRead(true);
                        String messageJson = gson.toJson(messageDto);
                        send(conversationToken, messageJson);
                    }
                }

            }
            for (TokenEntity senderToken : senderTokens) {
                LastMessageDto lastMessageDto = new LastMessageDto(receiverDto, message);
                String lastMessageJson = gson.toJson(lastMessageDto);
                notifications.send(senderToken.getToken(), lastMessageJson);
                String conversationToken = senderToken.getToken() + "/" + receiver.getId();
                if (sessions.containsKey(conversationToken)) {
                    String messageJson = gson.toJson(messageDto);
                    send(conversationToken, messageJson);
                }
            }
        }


    }
}
