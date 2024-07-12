import bean.NotificationBean;
import dao.NotificationDao;
import dao.ProjectDao;
import dao.TaskDao;
import dao.UserDao;
import dto.NotificationDto;
import entities.NotificationEntity;
import entities.NotificationEntity.NotificationType;
import entities.ProjectEntity;
import entities.TaskEntity;
import entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class NotificationBeanTest {

    @Mock
    private NotificationDao notificationDao;
    @Mock
    private ProjectDao projectDao;
    @Mock
    private TaskDao taskDao;
    @Mock
    private UserDao userDao;

    @InjectMocks
    private NotificationBean notificationBean;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private NotificationEntity createNotificationEntity() {
        NotificationEntity entity = new NotificationEntity();
        entity.setId(1);
        entity.setRead(true);
        entity.setType(NotificationType.PROJECT_APPROVED);
        entity.setTime(LocalDateTime.now());

        UserEntity user = new UserEntity();
        user.setId(2);
        entity.setUser(user);

        ProjectEntity project = new ProjectEntity();
        project.setName("ProjectName");
        entity.setProject(project);

        entity.setSeen(false);

        return entity;
    }

    @Test
    void testConvertToDto() {
        NotificationEntity entity = createNotificationEntity();

        NotificationDto dto = notificationBean.convertToDto(entity);

        assertEquals(entity.getId(), dto.getNotificationId());
        assertEquals(entity.isRead(), dto.isRead());
        assertEquals(entity.getType().name(), dto.getType());
        assertEquals(entity.getProject().getName(), dto.getProjectName());
        assertEquals(entity.getTime(), dto.getTime());
        assertEquals(entity.getUser().getId(), dto.getUserId());
        assertEquals(entity.isSeen(), dto.isSeen());
    }
    @Test
    void testCreateNotificationWithValidData() {
        NotificationDto dto = new NotificationDto();
        dto.setUserId(1);
        dto.setProjectName("ProjectName");
        dto.setType("PROJECT_APPROVED");
        dto.setRead(false);
        dto.setSeen(false);

        when(userDao.findUserById(1)).thenReturn(new UserEntity());
        when(projectDao.findProjectByName("ProjectName")).thenReturn(new ProjectEntity());

        assertTrue(notificationBean.createNotification(dto));
    }

    @Test
    void testCreateNotificationWithInvalidUser() {
        NotificationDto dto = new NotificationDto();
        dto.setUserId(99); // Assuming this user does not exist
        dto.setProjectName("ProjectName");
        dto.setType("PROJECT_APPROVED");

        when(userDao.findUserById(99)).thenReturn(null);

        assertFalse(notificationBean.createNotification(dto));
    }

    @Test
    void testFindNotificationsByProjectNameWithResults() {
        // Setup
        String projectName = "ProjectName";
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setName(projectName);
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1); // Example user ID

        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setId(1); // Example notification ID
        notificationEntity.setProject(projectEntity);
        notificationEntity.setUser(userEntity);
        notificationEntity.setRead(true);
        notificationEntity.setType(NotificationType.PROJECT_APPROVED); // Assuming NotificationType is an enum

        when(projectDao.findProjectByName(projectName)).thenReturn(projectEntity);
        when(userDao.findUserByToken(anyString())).thenReturn(userEntity);
        when(notificationDao.findNotifications(any(ProjectEntity.class), any(UserEntity.class), anyBoolean()))
                .thenReturn(List.of(notificationEntity));

        // Execute
        List<NotificationDto> results = notificationBean.findNotifications(projectName, "token", true);

        // Verify
        assertFalse(results.isEmpty());
        assertEquals(1, results.size()); // Verify the list contains exactly one element
        NotificationDto resultDto = results.get(0);
        assertEquals(notificationEntity.getId(), resultDto.getNotificationId());
        assertEquals(notificationEntity.getProject().getName(), resultDto.getProjectName());
        assertEquals(notificationEntity.getUser().getId(), resultDto.getUserId());
        assertEquals(notificationEntity.isRead(), resultDto.isRead());
        assertEquals(notificationEntity.getType().name(), resultDto.getType());
    }



    @Test
    void testFindNotificationById() {
        int notificationId = 1;
        when(notificationDao.findNotificationById(notificationId)).thenReturn(new NotificationEntity());

        assertNotNull(notificationBean.findNotificationById(notificationId));
    }



    @Test
    void testMarkAsRead() {
        int notificationId = 1;
        NotificationEntity entity = new NotificationEntity();
        entity.setId(notificationId);
        entity.setRead(false);

        when(notificationDao.findNotificationById(notificationId)).thenReturn(entity);

        assertTrue(notificationBean.markAsRead(notificationId));
    }

}
