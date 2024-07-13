package websocket;

import bean.NotificationBean;
import bean.ProjectBean;
import bean.TokenBean;
import bean.UserBean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.NotificationDao;
import dto.NotificationDto;
import entities.NotificationEntity;
import entities.ProjectEntity;
import entities.TokenEntity;
import entities.UserEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import service.ObjectMapperContextResolver;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
/**
 * The WebSocket class for the notifications.
 */
@Singleton
@ServerEndpoint("/websocket/notifications/{token}")
public class Notifications {

    @EJB
    private NotificationBean notificationBean;

    @EJB
    private UserBean userBean;
    @EJB
    ProjectBean projectBean;
    @EJB
    TokenBean tokenBean;
    /**
     * The map of sessions.
     */
    HashMap<String, Session> sessions = new HashMap<String, Session>();
    private ObjectMapperContextResolver contextResolver = new ObjectMapperContextResolver();

    /**
     *  The method to send a notification.
     * @param token
     * @param msg
     */
    public void send(String token, String msg) {
        Session session = sessions.get(token);
        if (session != null) {
            System.out.println("sending.......... " + msg);
            try {
                session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                System.out.println("Something went wrong!");
            }
        }
    }
    /**
     * The method to send a notification.
     * @param notificationDto The notification DTO.
     */
    public void sendNotification(NotificationDto notificationDto) {
        ObjectMapper mapper = contextResolver.getContext(null);
        String msg = null;
        try {
            msg = mapper.writeValueAsString(notificationDto);
        } catch (JsonProcessingException e) {
            System.out.println("Error in processing JSON: " + e.getMessage());
        }
        if (msg != null) {
            UserEntity user = userBean.findUserById(notificationDto.getUserId());
            List<TokenEntity> tokens = tokenBean.findActiveTokensByUser(user);
            if(tokens != null){
            for (TokenEntity token : tokens) {
                if (sessions.containsKey(token.getToken())) {
                    send(token.getToken(), msg);
                }
            }
            }
        }
    }

    /**
     * The method to get a session.
     * @param token
     * @return
     */
    public Session getSession(String token) {
        return sessions.get(token);
    }

    /**
     *  The method to open a WebSocket session.
     * @param session
     * @param token
     */
    @OnOpen
    public void toDoOnOpen(Session session, @PathParam("token") String token) {
        System.out.println("A new Notifications WebSocket session is opened for client with token: " + token);
        sessions.put(token, session);
        System.out.println(sessions.keySet());
    }
/**
     * The method to close a WebSocket session.
     * @param session
     * @param reason
     */
    @OnClose
    public void toDoOnClose(Session session, CloseReason reason) {
        System.out.println("Websocket session is closed with CloseCode: " + reason.getCloseCode() + ": " + reason.getReasonPhrase());
        for (String key : sessions.keySet()) {
            if (sessions.get(key) == session ) {
                sessions.remove(key);
                return;
            }

        }
    }
/**
     * This method is called when a message is received from the client
     * @param session
     * @param msg
     */
    @OnMessage
    public void toDoOnMessage(Session session, String msg) {
        ObjectMapper mapper = contextResolver.getContext(null);
        NotificationDto notificationDto = null;
        try {
            notificationDto = mapper.readValue(msg, NotificationDto.class);
        } catch (JsonProcessingException e) {
            System.out.println("Error in processing JSON: " + e.getMessage());
        }
        if (notificationDto != null) {

        notificationBean.createNotification(notificationDto);
        }
        UserEntity user = userBean.findUserById(notificationDto.getUserId());
        List<TokenEntity> tokens = tokenBean.findTokensByUser(user);
        for (TokenEntity token : tokens) {
            if (sessions.containsKey(token.getToken())) {
                send(token.getToken(), msg);
            }
        }
        if (session != null) {
            System.out.println("sending.......... " + msg);
            try {
                session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                System.out.println("Something went wrong!");
            }


        }
    }

}

