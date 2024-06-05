package dao;
import entities.ProjectEntity;
import entities.TaskEntity;
import entities.UserEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class TaskDao extends AbstractDao<TaskEntity>{

    public TaskDao() {
        super(TaskEntity.class);
    }

    public TaskEntity findTaskByName(String title) {
        try {
            return (TaskEntity) em.createNamedQuery("TaskEntity.findTaskByTitle").setParameter("title", title)
                    .getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }
    public TaskEntity findTaskByProject(ProjectEntity project) {
        try {
            return (TaskEntity) em.createNamedQuery("TaskEntity.findTaskByProject").setParameter("project", project)
                    .getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }
    public List<TaskEntity> findTasksByUser(UserEntity user) {
        try {
            return em.createNamedQuery("TaskEntity.findTasksByUser").setParameter("user", user)
                    .getResultList();

        } catch (Exception e) {
            return null;
        }
    }
    public List<TaskEntity> findTasksByStatus(TaskEntity.Status status) {
        try {
            return em.createNamedQuery("TaskEntity.findTasksByStatus").setParameter("status", status)
                    .getResultList();

        } catch (Exception e) {
            return null;
        }
    }
    public List<TaskEntity> findTasks(String taskName, String projectName, int userId, TaskEntity.Status status, LocalDateTime startDate, LocalDateTime endDate) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TaskEntity> cq = cb.createQuery(TaskEntity.class);
        Root<TaskEntity> task = cq.from(TaskEntity.class);
        cq.select(task);
        if (taskName != null) {
            cq.where(cb.equal(task.get("title"), taskName));
        }
        if (projectName != null) {
            Join<ProjectEntity,TaskEntity> projectJoin = task.join("project");
            cq.where(cb.equal(projectJoin.get("name"), projectName));
        }
        if (userId != 0) {
            Join<UserEntity,TaskEntity> userJoin = task.join("projectUsers");
            cq.where(cb.equal(userJoin.get("id"), userId));
            Join<UserEntity,TaskEntity> responsibleUserJoin = task.join("responsibleUser");
            cq.where(cb.equal(responsibleUserJoin.get("id"), userId));
        }
        if (status != null) {
            cq.where(cb.equal(task.get("status"), status));
        }
        if (startDate != null) {
            cq.where(cb.equal(task.get("startDate"), startDate));
        }
        if (endDate != null) {
            cq.where(cb.equal(task.get("endDate"), endDate));
        }
        return em.createQuery(cq).getResultList();
    }




}
