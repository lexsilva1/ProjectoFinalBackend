package bean;

import dao.GroupChatDao;
import dto.GroupChatDto;
import entities.ChatEntity;
import entities.ProjectEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class GroupChatBean {
    @Inject
    private GroupChatDao groupChatDao;
    @EJB
    private ProjectBean projectBean;

    public GroupChatBean() {
    }
    public GroupChatDto convertToDto(ChatEntity chat) {
        return new GroupChatDto(chat.getProject().getName(), chat.getSender().getFirstName(),chat.getSender().getId(), chat.getMessage(), chat.getTime());
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
}
