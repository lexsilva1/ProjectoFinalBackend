package websocket;

import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.websocket.server.ServerEndpoint;
@Singleton
@ServerEndpoint("/websocket/groupchat/{ProjectName}/{token}")
public class GroupChat {

    public void send(String projectName, String msg) {
        System.out.println("sending.......... " + msg);
    }
}
