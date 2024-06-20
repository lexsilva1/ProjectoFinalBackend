package bean;

import dao.NotificationDao;
import dao.ProjectDao;
import dao.UserDao;
import dto.NotificationDto;
import entities.NotificationEntity;
import entities.ProjectEntity;
import entities.UserEntity;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import websocket.Notifications;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Stateless

public class NotificationBean {
    @Inject
    private NotificationDao notificationDao;
    @Inject
    private UserDao userDao;
    @Inject
    private ProjectDao projectDao;
    @Inject
    private Notifications notifications;


    public NotificationBean() {
    }

    public NotificationDto convertToDto(NotificationEntity entity) {
        NotificationDto dto = new NotificationDto();
        dto.setRead(entity.isRead());
        dto.setType(entity.getType().name());
        dto.setProjectName(entity.getProject().getName());
        dto.setTime(entity.getTime());
        dto.setUserId(entity.getUser().getId());
        dto.setNotificationId(entity.getId());
        if(entity.getOtherUser() != null) {
            dto.setOtherUserId(entity.getOtherUser().getId());
        }
        return dto;
    }
    public boolean createNotification(NotificationDto dto) {
        boolean created = false;
        NotificationEntity entity = new NotificationEntity();
        entity.setType(NotificationEntity.NotificationType.valueOf(dto.getType()));
        entity.setRead(dto.isRead());
        entity.setTime(LocalDateTime.now());
        entity.setUser(userDao.findUserById(dto.getUserId()));
        entity.setProject(projectDao.findProjectByName(dto.getProjectName()));
        if(entity.getUser() == null || entity.getProject() == null) {
            return created;
        }
        notificationDao.persist(entity);
        created = true;
        return created;
    }
    public List<NotificationDto> findNotifications(String projectName,String token, Boolean isRead) {
        ProjectEntity project = projectDao.findProjectByName(projectName);
        UserEntity user = userDao.findUserByToken(token);
        List<NotificationEntity> entities = notificationDao.findNotifications(project, user,  isRead);
        List<NotificationDto> dtos = new ArrayList<>();
        for (NotificationEntity entity : entities) {
            dtos.add(convertToDto(entity));
        }
        return dtos;
    }
    public boolean sendNotification (NotificationDto dto) {

        boolean sent = false;

        if(createNotification(dto)) {
            notifications.sendNotification(dto);
            sent = true;
        }
        return sent;
    }
    public NotificationEntity findNotificationById(int id) {
        NotificationEntity entity = notificationDao.findNotificationById(id);
        return entity;
    }
    public NotificationDto updateNotificationMessage( int id, String message) {
        NotificationEntity entity = notificationDao.findNotificationById(id);
        entity.setType(NotificationEntity.NotificationType.valueOf(message));
        entity.setRead(true);
        notificationDao.merge(entity);
        return convertToDto (entity);
    }
    public int findLastNotificationId() {
        return notificationDao.findLastNotificationId();
    }
}
