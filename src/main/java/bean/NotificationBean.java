package bean;

import dao.NotificationDao;
import dao.ProjectDao;
import dao.UserDao;
import dto.NotificationDto;
import entities.NotificationEntity;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

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


    public NotificationBean() {
    }

    public NotificationDto convertToDto(NotificationEntity entity) {
        NotificationDto dto = new NotificationDto();
        dto.setRead(entity.isRead());
        dto.setMessage(entity.getMessage());
        dto.setProjectName(entity.getProject().getName());
        dto.setTime(entity.getTime());
        dto.setUserId(entity.getUser().getId());
        return dto;
    }
    public boolean createNotification(NotificationDto dto) {
        boolean created = false;
        NotificationEntity entity = new NotificationEntity();
        entity.setMessage(dto.getMessage());
        entity.setRead(dto.isRead());
        entity.setTime(dto.getTime());
        entity.setUser(userDao.findUserById(dto.getUserId()));
        entity.setProject(projectDao.findProjectByName(dto.getProjectName()));
        if(entity.getUser() == null || entity.getProject() == null) {
            return created;
        }
        notificationDao.persist(entity);
        created = true;
        return created;
    }
    public List<NotificationDto> findNotifications(String projectName,int userId, boolean isRead) {
        List<NotificationEntity> entities = notificationDao.findNotifications(projectName, userId,  isRead);
        List<NotificationDto> dtos = new ArrayList<>();
        for (NotificationEntity entity : entities) {
            dtos.add(convertToDto(entity));
        }
        return dtos;
    }
}
