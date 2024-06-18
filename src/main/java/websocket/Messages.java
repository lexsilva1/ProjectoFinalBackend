package websocket;

import bean.MessageBean;
import bean.NotificationBean;
import bean.TokenBean;
import bean.UserBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.SingleString;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import dto.LastMessageDto;
import dto.MessageDto;
import dto.MessageUserDto;
import entities.TokenEntity;
import entities.UserEntity;
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
    private final HashMap<String, Session> sessions = new HashMap<>();
    private final ObjectMapperContextResolver contextResolver = new ObjectMapperContextResolver();
    private final ObjectMapper mapper = contextResolver.getContext(ObjectMapper.class);

    public void send(String conversationToken, String msg) {
        Session session = sessions.get(conversationToken);
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
        System.out.println("A new messages WebSocket session is opened for client with token: " + token);
        String conversationToken = token + "/" + id;
        sessions.put(conversationToken, session);
    }

    @OnClose
    public void toDoOnClose(Session session, CloseReason reason) {
        System.out.println("Websocket session is closed with CloseCode: " + reason.getCloseCode() + ": " + reason.getReasonPhrase());
        sessions.values().removeIf(s -> s == session);
    }

    @OnMessage
    public void toDoOnMessage(@PathParam("token") String token, @PathParam("id") int id, String message) {

        System.out.println("Received message: " + message);
        UserEntity sender = userBean.findUserByToken(token);
        UserEntity receiver = userBean.findUserById(id);
        if (sender != null && receiver != null) {
            MessageUserDto senderDto = new MessageUserDto(sender);
            MessageUserDto receiverDto = new MessageUserDto(receiver);
            MessageDto messageDto = new MessageDto(message, senderDto, receiverDto);
            messageBean.createMessage(messageDto);
            List<TokenEntity> senderTokens = tokenBean.findActiveTokensByUser(sender);
            List<TokenEntity> receiverTokens = tokenBean.findActiveTokensByUser(receiver);

            for (TokenEntity receiverToken : receiverTokens) {;
                if (notifications.getSession(receiverToken.getToken()) != null){

                    System.out.println("receiver is online");
                    LastMessageDto lastMessageDto = new LastMessageDto(senderDto, message);
                    String lastMessageJson = serializeToJson(lastMessageDto);
                    notifications.send(receiverToken.getToken(), lastMessageJson);
                    System.out.println("sending last message to receiver");

                    String conversationToken = receiverToken.getToken() + "/" + sender.getId();
                    if (sessions.containsKey(conversationToken)) {
                        System.out.println("receiver is online with token: " + receiverToken.getToken());
                        messageDto.setIsRead(true);
                        String messageJson = serializeToJson(messageDto);
                        send(conversationToken, messageJson);
                        System.out.println("sending message to sender");
                    }
                }
            }

            for (TokenEntity senderToken : senderTokens) {
                LastMessageDto lastMessageDto = new LastMessageDto(receiverDto, message);
                String lastMessageJson = serializeToJson(lastMessageDto);
                notifications.send(senderToken.getToken(), lastMessageJson);
                System.out.println("sending last message to sender");
                String conversationToken = senderToken.getToken() + "/" + receiver.getId();
                if (sessions.containsKey(conversationToken)) {
                    String messageJson = serializeToJson(messageDto);
                    send(conversationToken, messageJson);
                    System.out.println("sending message to receiver");
                }
            }
        }
    }

    private String serializeToJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (IOException e) {
            e.printStackTrace();
            return "{}";
        }
    }
}

