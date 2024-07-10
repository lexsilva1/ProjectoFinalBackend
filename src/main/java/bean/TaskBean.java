package bean;

import dao.TaskDao;
import dto.*;
import entities.ProjectEntity;
import entities.TaskEntity;
import entities.UserEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

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
    @EJB
    ProjectLogBean projectLogBean;
    @EJB
    NotificationBean notificationBean;

    public TaskBean() {
    }
    public TaskEntity findTaskByName(String title) {
        return taskDao.findTaskByName(title);
    }

    public List<TaskEntity> findTasksByUser(UserEntity user) {
        return taskDao.findTasksByUser(user);
    }



    public boolean createTask(String token, String projectname, TaskDto taskDto) {
        TaskEntity task = new TaskEntity();
        if(taskDto.getTitle() == null) {
            return false;
        }
        task.setTitle(taskDto.getTitle());
        task.setExternalExecutors(taskDto.getExternalExecutors());
        if(taskDto.getDescription() == null) {
            return false;
        }
        task.setDescription(taskDto.getDescription());
        ProjectEntity project = projectBean.findProjectByName(projectname);
        if(project == null) {
            return false;
        }
        if(taskDto.getResponsibleId() <= 0) {
            task.setResponsibleUser(userBean.findUserByToken(token));
        }else {
            task.setResponsibleUser(userBean.findUserById(taskDto.getResponsibleId()));
        }
        task.setStatus(TaskEntity.Status.NOT_STARTED);
        if(taskDto.getStart() == null) {
            task.setStartDate(LocalDateTime.now());
        } else {
            task.setStartDate(taskDto.getStart());
        }
        if(taskDto.getEnd() == null) {
            task.setEndDate(project.getEndDate().minusDays(1));
        } else {
        task.setEndDate(taskDto.getEnd());
        }
        task.setCreationDate(LocalDateTime.now());
        if(taskDto.getDependencies() != null) {

            Set dependencies = new HashSet();
            for (Integer i : taskDto.getDependencies()) {
                TaskEntity dep = taskDao.find(i);
                if (dep != null) {
                    dependencies.add(dep);
                }
            }
            task.setDependencies(dependencies);
        }
        if(taskDto.getUsers() == null) {
            Set<UserEntity> users = new HashSet<>();
            users.add(userBean.findUserByToken(token));
            task.setTaskUsers(users);

        }else {
            Set users = new HashSet();
            for (Integer i : taskDto.getUsers()) {
                UserEntity u = userBean.findUserById(i);
                if (u != null) {
                    users.add(u);
                    notificationBean.sendNotification(new NotificationDto("TASK_EXECUTOR", i, projectname, false, LocalDateTime.now()));
                }
            }
            task.setTaskUsers(users);
        }
        task.setCreatedBy(userBean.findUserByToken(token));
        taskDao.persist(task);
        TaskEntity taskToAdd = taskDao.find(task.getId());
        projectBean.addTaskToProject(projectname, taskToAdd);
        ProjectLogDto projectLogDto = new ProjectLogDto(userBean.findUserByToken(token), project, "Task created: " + task.getTitle());
        projectLogDto.setType("CREATE_TASK");
        projectLogBean.createProjectLog(projectLogDto);
        notificationBean.sendNotification(new NotificationDto("TASK_ASSIGN", taskDto.getResponsibleId(),projectname,false,LocalDateTime.now()));
        return true;

    }
    public TaskEntity createLastTask(String token, CreateProjectDto project, UserEntity user, List<Integer> users) {
        TaskEntity task = new TaskEntity();
        task.setTitle("Final Presentation");
        task.setDescription("Final presentation of the finalized project");
        if(user == null) {
            return null;
        }else {
            task.setResponsibleUser(user);
        }
        task.setStatus(TaskEntity.Status.NOT_STARTED);
        task.setStartDate(project.getEndDate().minusDays(1));
        task.setEndDate(project.getEndDate());
        task.setCreationDate(LocalDateTime.now());
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
    public TaskDto toTasktoDto(TaskEntity task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setResponsibleId(task.getResponsibleUser().getId());
        taskDto.setStart(task.getStartDate());
        taskDto.setEnd(task.getEndDate());
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
    public TaskDto getTaskById(int id) {
        TaskEntity task = taskDao.find(id);
        if(task == null) {
            return null;
        }
        return toTasktoDto(task);
    }
    public boolean updateTask(String token, String projectName, TaskDto taskDto) {
        TaskEntity task = taskDao.find(taskDto.getId());
        if(task == null) {
            return false;
        }
        task.setTitle(taskDto.getTitle());
        task.setExternalExecutors(taskDto.getExternalExecutors());
        task.setDescription(taskDto.getDescription());
        if(taskDto.getResponsibleId() > 0) {
        task.setResponsibleUser(userBean.findUserById(taskDto.getResponsibleId()));
        }
        task.setStatus(TaskEntity.Status.valueOf(taskDto.getStatus()));
        if(taskDto.getStart() != null) {
            task.setStartDate(taskDto.getStart());
        }
        if(taskDto.getEnd() != null) {
        task.setEndDate(taskDto.getEnd());
        }
        if(taskDto.getStatus().equals("CANCELLED")) {
            Set<TaskEntity> dependencies = projectBean.findProjectByName(projectName).getTasks();
            System.out.println("dependencies: " + dependencies);
            if(dependencies != null) { // Null check before iteration
                for(TaskEntity t : dependencies) {
                    t.getDependencies().remove(task);
                    taskDao.merge(t);
                }
            }
            task.getDependencies().clear();
        } else {
            if (taskDto.getDependencies() != null) {
                Set dependencies = new HashSet();
                for (Integer i : taskDto.getDependencies()) {
                    TaskEntity dep = taskDao.find(i);
                    if (dep != null) {
                        dependencies.add(dep);
                    }
                }
                task.setDependencies(dependencies);
            }
        }
        Set users = new HashSet();
        for(Integer i : taskDto.getUsers()) {
            UserEntity u = userBean.findUserById(i);
            if(u != null) {
                users.add(u);
            }
        }
        task.setTaskUsers(users);
        taskDao.merge(task);
        return true;
    }

    public TaskEntity findTaskById(int id) {
        return taskDao.find(id);
    }
    public List<TaskEntity> findTasksByResponsibleUser(UserEntity user) {
        return taskDao.findTasksByResponsibleUser(user);
    }
}
