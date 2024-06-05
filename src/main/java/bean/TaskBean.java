package bean;

import dao.TaskDao;
import entities.ProjectEntity;
import entities.TaskEntity;
import entities.UserEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Stateless
public class TaskBean {
    @EJB
    TaskDao taskDao;
    @EJB
    UserBean userBean;
    @EJB
    ProjectBean projectBean;

    public TaskBean() {
    }
    public TaskEntity findTaskByName(String title) {
        return taskDao.findTaskByName(title);
    }
    public TaskEntity findTaskByProject(ProjectEntity project) {
        return taskDao.findTaskByProject(project);
    }
    public List<TaskEntity> findTasksByUser(UserEntity user) {
        return taskDao.findTasksByUser(user);
    }
    public boolean createTask(String token, String title, String description, ProjectEntity project, UserEntity user, LocalDate startDate, LocalDate endDate,List<Integer> dependencies, List<Integer> users) {
        TaskEntity task = new TaskEntity();
        task.setTitle(title);
        task.setDescription(description);
        task.setProject(project);
        task.setResponsibleUser(user);
        task.setStatus(TaskEntity.Status.NOT_STARTED);
        task.setStartDate(startDate);
        task.setEndDate(endDate);
        task.setCreationDate(LocalDate.now());
        Set dependenciesSet = new HashSet();
        for(Integer i : dependencies) {
            TaskEntity dep = taskDao.find(i);
            if(dep != null) {
                dependenciesSet.add(dep);
            }
        }
        task.setDependencies(dependenciesSet);
        Set usersSet = new HashSet();
        for(Integer i : users) {
            UserEntity u = userBean.findUserById(i);
            if(u != null) {
                usersSet.add(u);
            }
        }

        task.setCreatedBy(userBean.findUserByToken(token));

        taskDao.persist(task);
        return true;
    }
}
