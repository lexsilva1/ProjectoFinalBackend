package websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import service.ObjectMapperContextResolver;

import java.util.HashMap;

@Singleton
@ServerEndpoint("/websocket/groupchat/{ProjectName}/{token}")
public class GroupChat {
    private final HashMap<String, Session> sessions = new HashMap<>();
    private final ObjectMapperContextResolver contextResolver = new ObjectMapperContextResolver();
    private final ObjectMapper mapper = contextResolver.getContext(ObjectMapper.class);
    public void send(String projectName, String msg) {
        System.out.println("sending.......... " + msg);
    }

    public void toDoOnOpen(Session session, String projectName, String token) {
        System.out.println("A new group chat WebSocket session is opened for client with token: " + token);
        String conversationToken = projectName + "/" + token;
        sessions.put(conversationToken, session);
    }

    public void toDoOnClose(Session session, String projectName, String token) {
        System.out.println("A group chat WebSocket session is closed for client with token: " + token);
        String conversationToken = projectName + "/" + token;
        sessions.remove(conversationToken);
    }

    public void toDoOnError(Session session, String projectName, String token, Throwable error) {
        System.out.println("An error occurred in group chat WebSocket session for client with token: " + token);
        String conversationToken = projectName + "/" + token;
        sessions.remove(conversationToken);
    }


}
