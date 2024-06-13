package bean;

import dao.MessageDao;
import dto.MessageDto;
import entities.MessageEntity;
import entities.UserEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
public class MessageBean {
    @EJB
    private UserBean userBean;
    @EJB
    private MessageDao messageDao;

    public MessageBean() {
    }

    public void createMessage(MessageDto message) {
        UserEntity sender = userBean.findUserById(message.getSender().getId());
        UserEntity receiver = userBean.findUserById(message.getReceiver().getId());
        MessageEntity messageEntity = new MessageEntity(message, sender, receiver);
        messageDao.createMessage(messageEntity);
    }

}
