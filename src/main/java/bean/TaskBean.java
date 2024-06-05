package bean;

import dao.TaskDao;
import dto.TaskDto;
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
    public boolean createTask(String token, String projectname, TaskDto taskDto) {
        TaskEntity task = new TaskEntity();
        task.setTitle(taskDto.getTitle());
        task.setExternalExecutors(taskDto.getExternalExecutors());
        task.setDescription(taskDto.getDescription());
        ProjectEntity project = projectBean.findProjectByName(projectname);
        if(project == null) {
            return false;
        }
        task.setProject(project);
        task.setResponsibleUser(userBean.findUserById(taskDto.getResponsibleId()));
        task.setStatus(TaskEntity.Status.NOT_STARTED);
        task.setStartDate(taskDto.getStartDate());
        task.setEndDate(taskDto.getEndDate());
        task.setCreationDate(taskDto.getCreationDate());
        Set dependencies = new HashSet();
        for(Integer i : taskDto.getDependencies()) {
            TaskEntity dep = taskDao.find(i);
            if(dep != null) {
                dependencies.add(dep);
            }
        }
        task.setDependencies(dependencies);
        Set users = new HashSet();
        for(Integer i : taskDto.getUsers()) {
            UserEntity u = userBean.findUserById(i);
            if(u != null) {
                users.add(u);
            }
        }
        task.setTaskUsers(users);
        task.setCreatedBy(userBean.findUserByToken(token));
        taskDao.persist(task);
        return true;

    }
    public TaskEntity createLastTask(String token,ProjectEntity project, UserEntity user, LocalDate startDate, LocalDate endDate,List<Integer> dependencies, List<Integer> users) {
        TaskEntity task = new TaskEntity();
        task.setTitle("Final Presentation");
        task.setDescription("Final presentation of the finalized project");
        task.setProject(project);
        task.setResponsibleUser(user);
        task.setStatus(TaskEntity.Status.NOT_STARTED);
        task.setStartDate(project.getEndDate().minusDays(1));
        task.setEndDate(project.getEndDate());
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
        return task;
    }
    public TaskDto tasktoDto(TaskEntity task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setProjectName(task.getProject().getName());
        taskDto.setResponsibleId(task.getResponsibleUser().getId());
        taskDto.setStartDate(task.getStartDate());
        taskDto.setEndDate(task.getEndDate());
        taskDto.setCreationDate(task.getCreationDate());
        taskDto.setStatus(task.getStatus().toString());
        Set<TaskEntity> dependencies = task.getDependencies();
        Set<Integer> dependenciesIds = new HashSet<>();
        for(TaskEntity t : dependencies) {
            dependenciesIds.add(t.getId());
        }
        taskDto.setDependencies(dependenciesIds);
        Set<UserEntity> users = task.getTaskUsers();
        Set<Integer> usersIds = new HashSet<>();
        for(UserEntity u : users) {
            usersIds.add(u.getId());
        }
        taskDto.setExternalExecutors(task.getExternalExecutors());
        taskDto.setUsers(usersIds);
        return taskDto;
    }
}