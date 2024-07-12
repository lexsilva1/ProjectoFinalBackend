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
    private static final org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(TaskBean.class);
    public TaskBean() {
    }


    public boolean createTask(String token, String projectname, TaskDto taskDto) {
        logger.info("Creating task {} for project {}", taskDto.getTitle(), projectname);
        TaskEntity task = new TaskEntity();
        if(taskDto.getTitle() == null) {
            logger.error("Title is null");
            return false;
        }
        task.setTitle(taskDto.getTitle());
        task.setExternalExecutors(taskDto.getExternalExecutors());
        if(taskDto.getDescription() == null) {
            logger.error("Description is null");
            return false;
        }
        task.setDescription(taskDto.getDescription());
        ProjectEntity project = projectBean.findProjectByName(projectname);
        if(project == null) {
            logger.error("Project not found");
            return false;
        }
        if(taskDto.getResponsibleId() <= 0) {
            task.setResponsibleUser(userBean.findUserByToken(token));
            logger.info("Responsible user set to creator");
        }else {
            task.setResponsibleUser(userBean.findUserById(taskDto.getResponsibleId()));
        }
        task.setStatus(TaskEntity.Status.NOT_STARTED);
        if(taskDto.getStart() == null) {
            logger.info("Start date is null, setting to current date");
            task.setStartDate(LocalDateTime.now());
        } else {
            task.setStartDate(taskDto.getStart());
        }
        if(taskDto.getEnd() == null) {
            logger.info("End date is null, setting to project end date");
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
            logger.info("Users not found, setting to creator");
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
        logger.info("Task created successfully");
        return true;

    }
    public TaskEntity createLastTask(String token, CreateProjectDto project, UserEntity user, List<Integer> users) {
        logger.info("Creating final task for project {}", project.getName());
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
        logger.info("Final task created successfully");
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
        logger.info("User {} updating task {}", token,taskDto.getTitle());
        TaskEntity task = taskDao.find(taskDto.getId());
        if(task == null) {
            logger.error("Task not found");
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
            logger.info("Start date set to {}", taskDto.getStart());
            task.setStartDate(taskDto.getStart());
        }
        if(taskDto.getEnd() != null) {
            logger.info("End date set to {}", taskDto.getEnd());
        task.setEndDate(taskDto.getEnd());
        }
        if(taskDto.getStatus().equals("CANCELLED")) {
            logger.info("Task cancelled, removing dependencies");
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
                logger.info("Setting dependencies");
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
            logger.info("Setting users");
            UserEntity u = userBean.findUserById(i);
            if(u != null) {
                users.add(u);
            }
        }
        task.setTaskUsers(users);
        taskDao.merge(task);
        logger.info("Task updated successfully");
        return true;
    }

    public TaskEntity findTaskById(int id) {

        return taskDao.find(id);
    }

}
