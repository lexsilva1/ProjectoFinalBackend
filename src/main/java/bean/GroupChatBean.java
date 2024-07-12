package bean;

import dao.GroupChatDao;
import dto.GroupChatDto;
import entities.ChatEntity;
import entities.ProjectEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class GroupChatBean {

    @Inject
    private GroupChatDao groupChatDao;
    @EJB
    private ProjectBean projectBean;
    @EJB
    private UserBean userBean;
    private static final org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(GroupChatBean.class);

    public GroupChatBean() {
    }
    public GroupChatDto convertToDto(ChatEntity chat) {

        GroupChatDto chatDto = new GroupChatDto();
        chatDto.setProjectName(chat.getProject().getName());
        chatDto.setSender(chat.getSender().getFirstName());
        chatDto.setMessage(chat.getMessage());
        chatDto.setTime(chat.getTime());
        chatDto.setSenderId(chat.getSender().getId());
        chatDto.setUserPhoto(chat.getSender().getUserPhoto());

        return chatDto;
    }
    public List<GroupChatDto> fetchProjectChat(String projectName) {

       ProjectEntity project= projectBean.findProjectByName(projectName);
        List<ChatEntity> chats = groupChatDao.getAllChatByProject(project);
        List<GroupChatDto> chatDtos = new ArrayList<>();
        for (ChatEntity chat : chats) {
            chatDtos.add(convertToDto(chat));
        }

        return chatDtos;
    }
    public boolean createChat(String projectName, int senderId, String message) {
        boolean created = false;
        logger.info("Creating chat for project {}", projectName);
        ProjectEntity project = projectBean.findProjectByName(projectName);
        if(project == null) {
            logger.error("Project {} not found", projectName);
            return created;
        }
        ChatEntity chat = new ChatEntity();
        chat.setProject(project);
        chat.setSender(userBean.findUserById(senderId));
        if(chat.getSender() == null) {
            logger.error("User with id {} not found", senderId);
            return created;
        }
        chat.setMessage(message);
        chat.setTime(LocalDateTime.now());
        groupChatDao.create(chat);
        created = true;
        logger.info("Chat created successfully");
        return created;
    }
}
