import bean.MessageBean;
import bean.UserBean;
import dao.MessageDao;
import dto.MessageDto;
import dto.LastMessageDto;
import entities.MessageEntity;
import entities.UserEntity;
import dto.MessageUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class MessageBeanTest {

    @Mock
    private UserBean userBean;

    @Mock
    private MessageDao messageDao;

    @InjectMocks
    private MessageBean messageBean;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateMessage() {
        // Prepare test data
        MessageDto messageDto = new MessageDto();
        messageDto.setMessage("Hello");
UserEntity sender = new UserEntity();
        sender.setId(1);
        UserEntity receiver = new UserEntity();
        receiver.setId(2);
        messageDto.setSender(new MessageUserDto(sender));
        messageDto.setReceiver(new MessageUserDto(receiver));

        // Mock behavior
        when(userBean.findUserById(1)).thenReturn(sender);
        when(userBean.findUserById(2)).thenReturn(receiver);

        // Execute method under test
        MessageEntity createdMessageEntity = messageBean.createMessage(messageDto);

        // Verify interactions
        verify(userBean, times(1)).findUserById(1);
        verify(userBean, times(1)).findUserById(2);
        verify(messageDao, times(1)).createMessage(any(MessageEntity.class));

        // Verify returned entity
        assertNotNull(createdMessageEntity);
        assertEquals("Hello", createdMessageEntity.getMessage());
        assertEquals(sender, createdMessageEntity.getSender());
        assertEquals(receiver, createdMessageEntity.getReceiver());
    }

    @Test
    void testFindLastMessages() {
        // Prepare test data
        UserEntity user = new UserEntity();
        user.setId(1);
        when(userBean.findUserByToken(anyString())).thenReturn(user);

        List<MessageEntity> mockMessages = new ArrayList<>();
        MessageDto messageDto = new MessageDto();
        messageDto.setMessage("Hello");
        messageDto.setSender(new MessageUserDto(user));
        messageDto.setReceiver(new MessageUserDto(user));

        mockMessages.add(new MessageEntity(messageDto,user,user));

        // Mock behavior
        when(messageDao.findLastMessagesByUser(anyInt())).thenReturn(mockMessages);

        // Execute method under test
        List<LastMessageDto> lastMessageDtos = messageBean.findLastMessages("token");

        // Verify interactions
        verify(userBean, times(1)).findUserByToken("token");
        verify(messageDao, times(1)).findLastMessagesByUser(1);

        // Verify returned DTOs
        assertNotNull(lastMessageDtos);
        assertEquals(1, lastMessageDtos.size());
        assertEquals("Hello", lastMessageDtos.get(0).getMessage());
        assertEquals(1, lastMessageDtos.get(0).getSender().getId());
    }

    @Test
    void testMarkAsRead() {
        // Prepare test data
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setRead(false);

        // Execute method under test
        messageBean.markAsRead(messageEntity);

        // Verify interaction
        verify(messageDao, times(1)).merge(messageEntity);

        // Verify state change
        assertTrue(messageEntity.isRead());
    }
    @Test
    void testConvertToDto() {
        // Prepare test data
        UserEntity sender = new UserEntity();
        sender.setId(1);
        sender.setFirstName("John");
        sender.setLastName("Doe");
        sender.setUserPhoto("photo.jpg");

        UserEntity receiver = new UserEntity();
        receiver.setId(2);
        receiver.setFirstName("Jane");
        receiver.setLastName("Doe");
        receiver.setUserPhoto("photo.jpg");

        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setMessage("Hello");
        messageEntity.setSender(sender);
        messageEntity.setReceiver(receiver);

        // Execute method under test
        MessageDto messageDto = messageBean.convertToDto(messageEntity);

        // Verify returned DTO
        assertNotNull(messageDto);
        assertEquals("Hello", messageDto.getMessage());
        assertEquals(1, messageDto.getSender().getId());
        assertEquals("John", messageDto.getSender().getFirstName());
        assertEquals("Doe", messageDto.getSender().getLastName());
        assertEquals("photo.jpg", messageDto.getSender().getImage());
        assertEquals(2, messageDto.getReceiver().getId());
        assertEquals("Jane", messageDto.getReceiver().getFirstName());
        assertEquals("Doe", messageDto.getReceiver().getLastName());
        assertEquals("photo.jpg", messageDto.getReceiver().getImage());
    }
    @Test
    void testConvertToDtoWithNullValues() {
        // Prepare test data
        MessageEntity messageEntity = new MessageEntity();

        // Execute method under test
        MessageDto messageDto = messageBean.convertToDto(messageEntity);

        // Verify returned DTO
        assertNotNull(messageDto);
        assertNull(messageDto.getMessage());
        assertNull(messageDto.getSender());
        assertNull(messageDto.getReceiver());

    }
    @Test
    void testFindLastUserMessages() {
        // Mocking user and message data
        UserEntity user = new UserEntity();
        user.setId(1);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUserPhoto("user1.jpg");

        List<MessageEntity> mockMessages = new ArrayList<>();
        mockMessages.add(createMessageEntity(1, "Hello", user, user)); // Self-message

        // Mocking behavior of userBean.findUserByToken
        when(userBean.findUserByToken(anyString())).thenReturn(user);

        // Mocking behavior of messageDao.findLastMessagesByUser
        when(messageDao.findLastMessagesByUser(anyInt())).thenReturn(mockMessages);

        // Execute method under test
        List<LastMessageDto> lastMessageDtos = messageBean.findLastMessages("token");

        // Verify interactions with mocks
        verify(userBean, times(1)).findUserByToken("token");
        verify(messageDao, times(1)).findLastMessagesByUser(1);

        // Assertions on returned lastMessageDtos
        assertNotNull(lastMessageDtos);
        assertEquals(1, lastMessageDtos.size());

        LastMessageDto lastMessageDto = lastMessageDtos.get(0);
        assertNotNull(lastMessageDto);
        assertEquals("Hello", lastMessageDto.getMessage());
        assertEquals(user.getId(), lastMessageDto.getSender().getId());
        assertEquals(user.getFirstName(), lastMessageDto.getSender().getFirstName());
        assertEquals(user.getLastName(), lastMessageDto.getSender().getLastName());
        assertEquals(user.getUserPhoto(), lastMessageDto.getSender().getImage());
        assertTrue(lastMessageDto.isRead()); // Since it's a self-message, should be marked as read
    }

    // Helper method to create a MessageEntity
    private MessageEntity createMessageEntity(int id, String message, UserEntity sender, UserEntity receiver) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setId(id);
        messageEntity.setMessage(message);
        messageEntity.setSender(sender);
        messageEntity.setReceiver(receiver);
        messageEntity.setTime(LocalDateTime.now()); // Current time
        messageEntity.setRead(false); // Initially not read
        return messageEntity;
    }
}

    // Additional tests can be added for edge cases and error handling scenarios


