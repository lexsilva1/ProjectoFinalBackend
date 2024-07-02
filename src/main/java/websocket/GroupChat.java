package websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.GroupChatDto;
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
    public void sendChat(String projectName, GroupChatDto groupChatDto) {
        String conversationToken = projectName + "/" + groupChatDto.getSender();

        if (sessions.containsKey(conversationToken)) {
            try {
                String msg = mapper.writeValueAsString(groupChatDto);
                sessions.get(conversationToken).getAsyncRemote().sendText(msg);
            } catch (Exception e) {
                System.out.println("Something went wrong!");
            }
        }
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
    public  void toDoOnMessage(Session session, String projectName, String token, String message) {
        System.out.println("A new message is received in group chat WebSocket session for client with token: " + token);
        String conversationToken = projectName + "/" + token;
        sessions.get(conversationToken).getAsyncRemote().sendText(message);
    }

}
