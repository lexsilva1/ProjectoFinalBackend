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

/**
 * The bean class for the notification.
 */
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
    @Inject
    private TaskBean taskBean;
    private static final org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(NotificationBean.class);

    public NotificationBean() {
    }
/**
     * Convert the entity to a DTO.
     *
     * @param entity the entity
     * @return the DTO
     */
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
        if(entity.getTask() != null) {

            dto.setTaskId(entity.getTask().getId());

            dto.setTaskName(entity.getTask().getTitle());

        }
        dto.setType(entity.getType().name());
        dto.setStatus(convertTypeTpStatus(entity.getType()));
        dto.setSeen(entity.isSeen());

        return dto;
    }

    /**
     * Create a notification.
     * @param dto
     * @return
     */
    public boolean createNotification(NotificationDto dto) {

        boolean created = false;
        NotificationEntity entity = new NotificationEntity();
        entity.setType(NotificationEntity.NotificationType.valueOf(dto.getType()));
        entity.setRead(dto.isRead());
        entity.setSeen(dto.isSeen());
        entity.setTime(LocalDateTime.now());
        entity.setUser(userDao.findUserById(dto.getUserId()));

        entity.setProject(projectDao.findProjectByName(dto.getProjectName()));
        if(entity.getUser() == null ) {

            return created;
        }
        if(dto.getOtherUserId() != 0) {

            entity.setOtherUser(userDao.findUserById(dto.getOtherUserId()));

        }
        if(dto.getTaskId() != 0) {

            entity.setTask(taskBean.findTaskById(dto.getTaskId()));

        }
        notificationDao.persist(entity);
        created = true;
        logger.info("Notification created successfully for user {} and project {}", entity.getUser().getEmail());
        return created;
    }

    /**
     * Find notifications.
     * @param projectName
     * @param token
     * @param isRead
     * @return
     */
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

    /**
     * Send a notification.
     * @param dto
     * @return
     */
    public boolean sendNotification (NotificationDto dto) {
        logger.info("Sending notification");
        boolean sent = false;

        if(createNotification(dto)) {
            logger.info("Notification created");
            NotificationEntity entity = notificationDao.findLastNotificationByUserAndType(userDao.findUserById(dto.getUserId()), NotificationEntity.NotificationType.valueOf(dto.getType()));
            dto.setNotificationId(entity.getId());
            notifications.sendNotification(dto);
            logger.info("Notification sent to user {} successfully", dto.getUserId());
            sent = true;
        }
        return sent;
    }

    /**
     * Find notification by id.
     * @param id
     * @return
     */
    public NotificationEntity findNotificationById(int id) {
        logger.info("Finding notification by id");
        NotificationEntity entity = notificationDao.findNotificationById(id);
        logger.info("Notification found successfully");
        return entity;
    }
    /**
     * Update notification message.
     * @param id
     * @param message
     * @return
     */
    public NotificationDto updateNotificationMessage( int id, String message) {
        NotificationEntity entity = notificationDao.findNotificationById(id);
        logger.info("Updating notification {} message to {}",id, message);
        entity.setType(NotificationEntity.NotificationType.valueOf(message));
        entity.setRead(true);
        entity.setSeen(true);
        notificationDao.merge(entity);
        logger.info("Notification message updated successfully");
        return convertToDto (entity);
    }
    public NotificationDto updateChatNotification(String ProjectName) {
        NotificationEntity entity = notificationDao.findNotificationByProjectAndType(projectDao.findProjectByName(ProjectName), NotificationEntity.NotificationType.CHAT);
        logger.info("Updating notification {} message to {}",entity.getId(), entity.getType());
        entity.setType(NotificationEntity.NotificationType.CHAT);
        entity.setRead(false);
        entity.setSeen(false);
        notificationDao.merge(entity);
        logger.info("Notification message updated successfully");
        return convertToDto (entity);
    }

    /**
     * Update notification seen.
     * @param id
     * @return
     */
    public NotificationDto updateNotificationSeen( int id) {
        NotificationEntity entity = notificationDao.findNotificationById(id);
        logger.info("Updating notification {} seen",id);
        entity.setSeen(true);
        notificationDao.merge(entity);
        logger.info("Notification seen updated successfully");
        return convertToDto (entity);
    }
    /**
     * convert notification type to status to string
     */
    public String convertTypeTpStatus(NotificationEntity.NotificationType type){
        logger.info("Converting notification type to status");
        String status = "";
        switch (type) {

            case TASK_DOING:
                         status ="IN_PROGRESS";
                        break;
            case TASK_COMPLETE:
                        status = "COMPLETE";
                        break;
            case PROJECT_COMPLETE:
                        status = "Finished";
                        break;
            case PROJECT_CANCEL:
                        status = "Cancelled";
                        break;
            case PROJECT_APPROVED:
                        status = "Approved";
                        break;
            case PROJECT_READY:
                        status = "Ready";
                        break;
            case PROJECT_DOING:
                        status = "In_Progress";
                        break;
    }
        logger.info("Notification type converted to status successfully");
    return status;
    }

    /**
     * Mark notification as read.
     * @param notificationId
     * @return
     */
    public boolean markAsRead(int notificationId) {
        logger.info("Marking notification {} as read", notificationId);
        NotificationEntity entity = notificationDao.findNotificationById(notificationId);
        entity.setRead(true);
        notificationDao.merge(entity);
        logger.info("Notification marked as read successfully");
        return true;
    }

    /**
     * Mark all notifications as seen.
     * @param token
     * @return
     */
    public boolean markAllAsSeen( String token) {
        logger.info("Marking all notifications as seen");
        UserEntity user = userDao.findUserByToken(token);
        List<NotificationEntity> entities = notificationDao.findNotificationByUser(user);
        for (NotificationEntity entity : entities) {
            if(!entity.isSeen()){
                NotificationDto dto =updateNotificationSeen(entity.getId());
                notifications.sendNotification(dto);

            }

        }
        logger.info("All notifications marked as seen successfully");
        return true;
    }
}
