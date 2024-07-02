package websocket;

import bean.GroupChatBean;
import bean.ProjectBean;
import bean.UserBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.GroupChatDto;
import entities.ProjectEntity;
import entities.UserEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import service.ObjectMapperContextResolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Singleton
@ServerEndpoint("/websocket/groupchat/{ProjectName}/{token}")
public class GroupChat {
    @EJB
    private UserBean userBean;
    @EJB
    private GroupChatBean groupChatBean;
    @EJB
    private ProjectBean projectBean;
    private final HashMap<String, Session> sessions = new HashMap<>();
    private final ObjectMapperContextResolver contextResolver = new ObjectMapperContextResolver();
    private final ObjectMapper mapper = contextResolver.getContext(ObjectMapper.class);
    public void send(String projectName, String msg) {
        System.out.println("sending.......... " + msg);
    }
    public void sendChat(String projectName, GroupChatDto groupChatDto) {
        List<Session> projectSessions = getSessionsByProjectName(projectName);
        for (Session session : projectSessions) {
            try {
                session.getBasicRemote().sendText(mapper.writeValueAsString(groupChatDto));
            } catch (Exception e) {
                System.out.println("Something went wrong!");
            }
        }
    }
    public List<Session> getSessionsByProjectName(String projectName) {
        List<Session> projectSessions = new ArrayList<>();
        for (String key : sessions.keySet()) {
            if (key.startsWith(projectName + "/")) {
                projectSessions.add(sessions.get(key));
            }
        }
        return projectSessions;
    }
    @OnOpen
    public void toDoOnOpen(Session session, @PathParam("projectName") String projectName, @PathParam("token") String token) {
        System.out.println("A new group chat WebSocket session is opened for client with token: " + token);
        String conversationToken = projectName + "/" + token;
        sessions.put(conversationToken, session);
    }
    @OnClose
    public void toDoOnClose(Session session, @PathParam("projectName") String projectName, @PathParam("token") String token) {
        System.out.println("A group chat WebSocket session is closed for client with token: " + token);
        String conversationToken = projectName + "/" + token;
        sessions.remove(conversationToken);
    }
    @OnError
    public void toDoOnError(Session session, @PathParam("projectName") String projectName, @PathParam("token") String token, Throwable error) {
        System.out.println("An error occurred in group chat WebSocket session for client with token: " + token);
        String conversationToken = projectName + "/" + token;
        sessions.remove(conversationToken);
    }
    @OnMessage

    public  void toDoOnMessage( String message, @PathParam("projectName") String projectName, @PathParam("token") String token) {
        UserEntity sender = userBean.findUserByToken(token);
        ProjectEntity project = projectBean.findProjectByName(projectName);
        boolean created = groupChatBean.createChat(projectName, sender.getId(), message);
        if(!created) {
            return;
        }
        GroupChatDto groupChatDto = new GroupChatDto(project.getName(), sender.getFirstName(), sender.getId(), message);
        sendChat(projectName, groupChatDto);


    }

}
