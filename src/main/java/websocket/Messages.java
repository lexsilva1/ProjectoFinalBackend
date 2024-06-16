package websocket;

import bean.MessageBean;
import bean.NotificationBean;
import bean.UserBean;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import jakarta.ws.rs.PathParam;
import service.ObjectMapperContextResolver;

import java.io.IOException;
import java.util.HashMap;

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
    HashMap<String, Session> sessions = new HashMap<String, Session>();
    private ObjectMapperContextResolver contextResolver = new ObjectMapperContextResolver();



}
