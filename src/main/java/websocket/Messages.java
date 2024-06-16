package websocket;

import bean.MessageBean;
import bean.NotificationBean;
import bean.UserBean;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import service.ObjectMapperContextResolver;

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

    HashMap<String, Session> sessions = new HashMap<String, Session>();
    private ObjectMapperContextResolver contextResolver = new ObjectMapperContextResolver();

    public Session getSession(String token, String id) {
        String conversationToken = token + "/" + id;
        return sessions.get(conversationToken);
    }

}
