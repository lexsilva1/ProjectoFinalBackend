import bean.*;
import dao.TaskDao;
import dto.NotificationDto;
import dto.TaskDto;
import entities.ProjectEntity;
import entities.TaskEntity;
import entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class TaskBeanTest {

    @Mock
    private TaskDao taskDao;
    @Mock
    private UserBean userBean;
    @Mock
    private ProjectBean projectBean;
    @Mock
    private ProjectLogBean projectLogBean;
    @Mock
    private NotificationBean notificationBean;

    @InjectMocks
    private TaskBean taskBean;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTaskSuccess() {
        // Setup
        String token = "token";
        String projectName = "ProjectName";
        TaskDto taskDto = new TaskDto();
        taskDto.setTitle("Task Title");
        taskDto.setExternalExecutors("External Executors");
        taskDto.setDescription("Task Description");
        taskDto.setResponsibleId(1);
        taskDto.setStart(LocalDateTime.now());
        taskDto.setEnd(LocalDateTime.now().plusDays(1));
        taskDto.setDependencies(new HashSet<>(Arrays.asList(1)));
        taskDto.setUsers(new HashSet<>(Arrays.asList(1)));

        ProjectEntity project = new ProjectEntity();
        project.setName(projectName);
        UserEntity user = new UserEntity();
        TaskEntity dependency = new TaskEntity();

        when(projectBean.findProjectByName(projectName)).thenReturn(project);
        when(userBean.findUserById(anyInt())).thenReturn(user);
        when(userBean.findUserByToken(token)).thenReturn(user);
        when(taskDao.find(anyInt())).thenReturn(dependency);

        // Execute
        boolean result = taskBean.createTask(token, projectName, taskDto);

        // Assert
        assertTrue(result);
        verify(taskDao, times(1)).persist(any(TaskEntity.class));
        verify(notificationBean, atLeastOnce()).sendNotification(any(NotificationDto.class));
    }

    @Test
    void testCreateTaskProjectNotFound() {
        // Setup
        when(projectBean.findProjectByName(anyString())).thenReturn(null);

        // Execute
        boolean result = taskBean.createTask("token", "NonExistentProject", new TaskDto());

        // Assert
        assertFalse(result);
    }

    @Test
    void testCreateTaskWithDependencyNotFound() {
        // Setup
        TaskDto taskDto = new TaskDto();
        taskDto.setDependencies(new HashSet<>(Arrays.asList(1)));
        when(taskDao.find(anyInt())).thenReturn(null);

        // Execute
        boolean result = taskBean.createTask("token", "ProjectName", taskDto);

        // Assert
        assertFalse(result);
    }
    @Test
    void testCreateTaskWithNothing() {
        // Setup
        TaskDto taskDto = new TaskDto();
        taskDto.setTitle("Task Title");
        taskDto.setDescription("Task Description");
        taskDto.setStart(null);
        taskDto.setEnd(null);
        ProjectEntity project = new ProjectEntity();
        project.setEndDate(LocalDateTime.now().plusDays(10)); // Ensure the project has an end date
        when(projectBean.findProjectByName(anyString())).thenReturn(project);
        UserEntity user = new UserEntity();
        when(userBean.findUserByToken(anyString())).thenReturn(user);
        // Execute
        boolean result = taskBean.createTask("token","projectName" , taskDto);

        // Assert
        assertTrue(result);
    }
    @Test
    void testUpdateTaskSuccess() {
        // Setup
        String token = "token";
        String projectName = "ProjectName";
        TaskDto taskDto = new TaskDto();
        taskDto.setId(1); // Assuming an existing task ID
        taskDto.setTitle("Updated Task Title");
        taskDto.setDescription("Updated Task Description");
        taskDto.setResponsibleId(2); // Assuming a valid user ID
        taskDto.setStart(LocalDateTime.now());
        taskDto.setEnd(LocalDateTime.now().plusDays(1));
        taskDto.setStatus("IN_PROGRESS");
        taskDto.setDependencies(new HashSet<>(Arrays.asList(1)));
        taskDto.setUsers(new HashSet<>(Arrays.asList(2)));

        TaskEntity existingTask = new TaskEntity();
        ProjectEntity project = new ProjectEntity();
        UserEntity responsibleUser = new UserEntity();

        when(taskDao.find(taskDto.getId())).thenReturn(existingTask);
        when(userBean.findUserById(taskDto.getResponsibleId())).thenReturn(responsibleUser);
        when(projectBean.findProjectByName(projectName)).thenReturn(project);

        // Execute
        boolean result = taskBean.updateTask(token, projectName, taskDto);

        // Assert
        assertTrue(result);
        verify(taskDao, times(1)).merge(existingTask);
        assertEquals("Updated Task Title", existingTask.getTitle());
        assertEquals("Updated Task Description", existingTask.getDescription());
    }

    @Test
    void testUpdateTaskNotFound() {
        // Setup
        when(taskDao.find(anyInt())).thenReturn(null);

        // Execute
        boolean result = taskBean.updateTask("token", "ProjectName", new TaskDto());

        // Assert
        assertFalse(result);
    }

}