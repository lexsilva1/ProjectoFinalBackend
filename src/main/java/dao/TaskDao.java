package dao;

import dto.Category;
import dto.TaskStatisticsDto;
import entities.CategoryEntity;
import entities.TaskEntity;
import entities.UserEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class TaskDao extends AbstractDao<TaskEntity>{
    @PersistenceContext
    private EntityManager em;
    public TaskDao() {
        super(TaskEntity.class);
    }
    private static final long serialVersionUID = 1L;

    public TaskEntity findTaskById(int id) {
        try {
            return (TaskEntity) em.createNamedQuery("Task.findTaskById").setParameter("id", id)
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }

    }

    public ArrayList<TaskEntity> findTaskByUser(UserEntity userEntity) {
        try {
            ArrayList<TaskEntity> taskEntityEntities = (ArrayList<TaskEntity>) em.createNamedQuery("Task.findTaskByUser").setParameter("user", userEntity).getResultList();
            return taskEntityEntities;
        } catch (Exception e) {
            return null;
        }
    }
    public TaskEntity createTask(TaskEntity taskEntity) {
        em.persist(taskEntity);
        return taskEntity;
    }
    public String findCreatorByName(String name){
        try {
            return (String) em.createNamedQuery("Category.findCreatorByName").setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    public List<TaskEntity> findTasksByUser2(UserEntity userEntity, boolean active) {
        try {
            List<TaskEntity> taskEntityEntities = (List<TaskEntity>) em.createNamedQuery("Task.findTaskByUser2").setParameter("user", userEntity).setParameter("active", active).getResultList();
            return taskEntityEntities;
        } catch (Exception e) {
            return null;
        }
    }
    public List<TaskEntity> findTasksByUser(UserEntity userEntity) {
        try {
            List<TaskEntity> taskEntityEntities = (List<TaskEntity>) em.createNamedQuery("Task.findTaskByUser").setParameter("user", userEntity).getResultList();
            return taskEntityEntities;
        } catch (Exception e) {
            return null;
        }
    }
    public CategoryEntity findCategoryByName(String name){
        System.out.println("nome da categoria: " + name);
        try {
            return (CategoryEntity) em.createNamedQuery("Category.findCategoryByName").setParameter("name", name).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    public void removeCategory(CategoryEntity categoryEntity) {
        em.remove(categoryEntity);
    }
    public void createCategory(CategoryEntity categoryEntity) {
        em.persist(categoryEntity);
    }
    public void updateCategory(CategoryEntity categoryEntity) {
        em.merge(categoryEntity);
    }
    public CategoryEntity findCategoryById(int id) {
        try {
            return (CategoryEntity) em.createNamedQuery("Category.findCategoryById").setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    public void updateTask(TaskEntity taskEntity) {
        em.merge(taskEntity);
    }
    public TaskEntity findTaskById(String id) {
        try {
            return (TaskEntity) em.createNamedQuery("Task.findTaskById").setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    public List<TaskEntity> findTasksByCategory2(CategoryEntity category, boolean active) {
        try {
           return  (List<TaskEntity>) em.createNamedQuery("Task.findTaskByCategory2").setParameter("category", category).setParameter("active", active).getResultList();

        } catch (Exception e) {
            return null;
        }
    }
    public List<TaskEntity> findTasksByCategory(String category) {
        try {
            return  (List<TaskEntity>) em.createNamedQuery("Task.findTaskByCategory").setParameter("category", category).getResultList();

        } catch (Exception e) {
            return null;
        }
    }
    public List<TaskEntity> findBlockedTasks() {
        try {
            List<TaskEntity> taskEntityEntities = (List<TaskEntity>) em.createNamedQuery("Task.findBlockedTasks").getResultList();
            return taskEntityEntities;
        } catch (Exception e) {
            return null;
        }
    }
    public List<CategoryEntity> findAllCategories() {
        try {
            List<CategoryEntity> categoryEntities = (List<CategoryEntity>) em.createNamedQuery("Category.findAll").getResultList();
            return categoryEntities;
        } catch (Exception e) {
            return null;
        }
    }
    public List<TaskEntity> findAllActiveTasks() {
        try {
            List<TaskEntity> taskEntityEntities = (List<TaskEntity>) em.createNamedQuery("Task.findAllActiveTasks").getResultList();
            return taskEntityEntities;
        } catch (Exception e) {
            return null;
        }
    }
    public List<TaskEntity> findDeletedTasks() {
        try {
            List<TaskEntity> taskEntityEntities = (List<TaskEntity>) em.createNamedQuery("Task.findDeletedTasks").getResultList();
            return taskEntityEntities;
        } catch (Exception e) {
            return null;
        }
    }
    public List<TaskEntity> findTasksByUserAndStatus(UserEntity userEntity, int status) {
        try {
            List<TaskEntity> taskEntityEntities = (List<TaskEntity>) em.createNamedQuery("Task.findTasksByUserAndStatus").setParameter("user", userEntity).setParameter("status", status).getResultList();
            return taskEntityEntities;
        } catch (Exception e) {
            return null;
        }
    }
    public List<TaskEntity> findTasksByStatus(int status) {
        try {
            List<TaskEntity> taskEntityEntities = (List<TaskEntity>) em.createNamedQuery("Task.findTaskByStatus").setParameter("status", status).getResultList();
            return taskEntityEntities;
        } catch (Exception e) {
            return null;
        }
    }
    public HashMap<String, Long> getTaskCountPerCategory() {
        // Execute the named query
        List<Object[]> results = em.createNamedQuery("Task.countTasksPerCategory").getResultList();

        // Process the results into a Map
        HashMap<String, Long> taskCountPerCategory = new HashMap<>();
        for (Object[] result : results) {
            String categoryName = ((CategoryEntity) result[0]).getName();
            Long count = (Long) result[1];
            taskCountPerCategory.put(categoryName, count);
        }

        return taskCountPerCategory;
    }
    public HashMap<LocalDate,Long> getTasksCompletedByDate() {
        // Execute the named query
        List<Object[]> results = em.createNamedQuery("Task.countTasksCompletedByDate").getResultList();

        // Process the results into a Map
        HashMap<LocalDate, Long> tasksCompletedByDate = new HashMap<>();
        for (Object[] result : results) {
            LocalDate date = (LocalDate) result[0];
            Long count = (Long) result[1];
            tasksCompletedByDate.put(date, count);
        }
        System.out.println("tasksCompletedByDate: " + tasksCompletedByDate);
        return tasksCompletedByDate;
    }




}
