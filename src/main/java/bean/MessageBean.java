package bean;

import dao.MessageDao;
import dto.LastMessageDto;
import dto.MessageDto;
import dto.MessageUserDto;
import entities.MessageEntity;
import entities.UserEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import utilities.DatesGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class MessageBean {
    @EJB
    private UserBean userBean;
    @EJB
    private MessageDao messageDao;
    @Inject
    DatesGenerator datesGenerator;
    private static final org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(MessageBean.class);
    public MessageBean() {
    }
public void createDefaultMessage() {
        logger.info("Creating default messages");
        UserEntity sender = userBean.findUserById(1);
        UserEntity receiver = userBean.findUserById(2);
        UserEntity receiver2 = userBean.findUserById(3);
        UserEntity sender2 = userBean.findUserById(4);
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setSender(sender);
        messageEntity.setReceiver(receiver);
        messageEntity.setMessage("Hello");
        messageEntity.setRead(true);
        messageEntity.setTime(datesGenerator.generateRandomDate());
        messageDao.createMessage(messageEntity);
        MessageEntity messageEntity2 = new MessageEntity();
        messageEntity2.setSender(receiver);
        messageEntity2.setReceiver(sender);
        messageEntity2.setMessage("Hi");
        messageEntity2.setRead(true);
        messageEntity2.setTime(datesGenerator.generateRandomDate());
        messageDao.createMessage(messageEntity2);
        MessageEntity messageEntity3 = new MessageEntity();
        messageEntity3.setSender(sender);
        messageEntity3.setReceiver(receiver2);
        messageEntity3.setMessage("Hello");
        messageEntity3.setRead(true);
        messageEntity3.setTime(datesGenerator.generateRandomDate());
        messageDao.createMessage(messageEntity3);
        MessageEntity messageEntity4 = new MessageEntity();
        messageEntity4.setSender(receiver2);
        messageEntity4.setReceiver(sender);
        messageEntity4.setMessage("Hi");
        messageEntity4.setRead(true);
        messageEntity4.setTime(datesGenerator.generateRandomDate());
        messageDao.createMessage(messageEntity4);
        MessageEntity messageEntity5 = new MessageEntity();
        messageEntity5.setSender(sender2);
        messageEntity5.setReceiver(receiver);
        messageEntity5.setMessage("Hello");
        messageEntity5.setRead(true);
        messageEntity5.setTime(datesGenerator.generateRandomDate());
        messageDao.createMessage(messageEntity5);
        MessageEntity messageEntity6 = new MessageEntity();
        messageEntity6.setSender(receiver);
        messageEntity6.setReceiver(sender2);
        messageEntity6.setMessage("Hi");
        messageEntity6.setRead(true);
        messageEntity6.setTime(datesGenerator.generateRandomDate());
        messageDao.createMessage(messageEntity6);
        MessageEntity messageEntity7 = new MessageEntity();
        messageEntity7.setSender(sender2);
        messageEntity7.setReceiver(receiver2);
        messageEntity7.setMessage("Hello");
        messageEntity7.setRead(true);
        messageEntity7.setTime(datesGenerator.generateRandomDate());
        messageDao.createMessage(messageEntity7);
        MessageEntity messageEntity8 = new MessageEntity();
        messageEntity8.setSender(receiver2);
        messageEntity8.setReceiver(sender2);
        messageEntity8.setMessage("Hi");
        messageEntity8.setRead(true);
        messageEntity8.setTime(datesGenerator.generateRandomDate());
        messageDao.createMessage(messageEntity8);


    }
    public MessageEntity createMessage(MessageDto message) {
        logger.info("Creating message");
        UserEntity sender = userBean.findUserById(message.getSender().getId());
        UserEntity receiver = userBean.findUserById(message.getReceiver().getId());
        MessageEntity messageEntity = new MessageEntity(message, sender, receiver);
        messageDao.createMessage(messageEntity);
        logger.info("Message created successfully");
        return messageEntity;
    }
    public List <MessageDto> findUserMessages(String token, int id) {

        UserEntity user = userBean.findUserByToken(token);
        UserEntity user2 = userBean.findUserById(id);
        List <MessageEntity> messages = messageDao.findMessagesByUser(user.getId(), user2.getId());
        List <MessageDto> messageDtos = new ArrayList<>();
        for (MessageEntity message : messages) {
            if(!message.isRead() && message.getReceiver().getId() == user.getId()) {
                markAsRead(message);
            }
            messageDtos.add(convertToDto(message));
        }

        return messageDtos;
    }
    public MessageDto convertToDto(MessageEntity message) {

        MessageDto messageDto = new MessageDto();
        UserEntity sender = message.getSender();
        UserEntity receiver = message.getReceiver();
        MessageUserDto senderDto = new MessageUserDto();
        MessageUserDto receiverDto = new MessageUserDto();
        senderDto.setId(sender.getId());
        senderDto.setFirstName(sender.getFirstName());
        senderDto.setLastName(sender.getLastName());
        senderDto.setImage(sender.getUserPhoto());
        receiverDto.setId(receiver.getId());
        receiverDto.setFirstName(receiver.getFirstName());
        receiverDto.setLastName(receiver.getLastName());
        receiverDto.setImage(receiver.getUserPhoto());
        messageDto.setMessage(message.getMessage());
        messageDto.setSender(senderDto);
        messageDto.setReceiver(receiverDto);
        messageDto.setTime(message.getTime());
        messageDto.setIsRead(message.isRead());

        return messageDto;
    }
    public List <LastMessageDto> findLastMessages(String token) {
        UserEntity user = userBean.findUserByToken(token);

        List <MessageEntity> messages = messageDao.findLastMessagesByUser(user.getId());
        List <LastMessageDto> messageDtos = new ArrayList<>();
        for (MessageEntity message : messages) {
            LastMessageDto lastMessageDto = new LastMessageDto();
            MessageUserDto senderDto = new MessageUserDto();
            if(message.getSender().getId() == user.getId()) {
                UserEntity receiver = message.getReceiver();
                senderDto.setId(receiver.getId());
                senderDto.setFirstName(receiver.getFirstName());
                senderDto.setLastName(receiver.getLastName());
                senderDto.setImage(receiver.getUserPhoto());
                lastMessageDto.setSender(senderDto);
                lastMessageDto.setRead(true);
            } else {
                UserEntity sender = message.getSender();
                senderDto.setId(sender.getId());
                senderDto.setFirstName(sender.getFirstName());
                senderDto.setLastName(sender.getLastName());
                senderDto.setImage(sender.getUserPhoto());
                lastMessageDto.setSender(senderDto);
                lastMessageDto.setRead(message.isRead());
            }
            lastMessageDto.setMessage(message.getMessage());
            lastMessageDto.setTime(message.getTime());

            messageDtos.add(lastMessageDto);
        }

        return messageDtos;
    }
    public void markAsRead(MessageEntity message) {
        logger.info("Marking message as read");
        message.setRead(true);
        messageDao.merge(message);
    }


}
