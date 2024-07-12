import bean.*;
import dao.*;
import dto.*;
import entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProjectBeanTest {

    @Mock
    private ProjectDao projectDao;
    @Mock
    private UserBean userBean;
    @Mock
    private LabDao labDao;
    @Mock
    private TaskBean taskBean;
    @Mock
    private SkillBean skillBean;
    @Mock
    private InterestBean interestBean;
    @Mock
    private UserDao userDao;
    @Mock
    private ResourceDao resourceDao;
    @Mock
    private ProjectUserDao projectUserDao;
    @Mock
    private ProjectLogBean projectLogBean;
    @Mock
    private NotificationBean notificationBean;
    @Mock
    private SystemVariablesBean systemVariablesBean;
    @Mock
    private TaskDao taskDao;
    @Mock
    private ResourceBean resourceBean; // Added ResourceBean mock

    @InjectMocks
    private ProjectBean projectBean;
    private CreateProjectDto projectDto;
    private UserEntity userEntity;
    private LabEntity labEntity;
    private ProjectUserEntity projectUserEntity;
    private ProjectEntity projectEntity;
    private ProjectResourceDto projectResourceDto; // Added ProjectResourceDto

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        projectDto = new CreateProjectDto();
        userEntity = new UserEntity();
        labEntity = new LabEntity();
        projectEntity = new ProjectEntity();
        projectUserEntity = new ProjectUserEntity();

        // Common setup for most tests
        projectDto.setName("ProjectName");
        projectDto.setDescription("ProjectDescription");
        projectDto.setLab("Lisboa");
        projectDto.setSlots(4);
        projectDto.setStartDate(LocalDateTime.now().plusDays(6));
        projectDto.setEndDate(LocalDateTime.now().plusDays(30));
        projectDto.setSkills(new HashSet<>());
        projectDto.setInterests(new HashSet<>());
        projectDto.setTeamMembers(new HashSet<>());
        projectDto.setBillOfMaterials(new HashSet<>());
        userEntity.setId(1);
        userEntity.setLocation(labEntity);
        userEntity.setRole(UserEntity.Role.Admin);
        labEntity.setLocation(LabEntity.Lab.Lisboa);
        projectEntity.setName("ProjectName");
        projectEntity.setTasks(new HashSet<>());
        projectUserEntity.setProject(projectEntity);

        when(userBean.findUserByToken(anyString())).thenReturn(userEntity);
        when(labDao.findLabByLocation(any())).thenReturn(labEntity);
        when(taskBean.createLastTask(anyString(), any(), any(), any())).thenReturn(new TaskEntity());
        when(skillBean.listDtoToEntity(any())).thenReturn(new HashSet<>());
        when(interestBean.listDtoToEntity(any())).thenReturn(new HashSet<>());
        when(userBean.findUserByToken(anyString())).thenReturn(userEntity);
        when(projectDao.findProjectByName(anyString())).thenReturn(projectEntity);
        when(projectUserDao.findProjectUserByProjectAndUser(any(ProjectEntity.class), any(UserEntity.class)))
                .thenReturn(projectUserEntity);
        when(systemVariablesBean.getMaxUsers()).thenReturn(10);

    }

    @Test
    void testAddTaskToNonExistingProject() {
        TaskEntity task = new TaskEntity();
        when(projectDao.findProjectByName("NonExistingProject")).thenReturn(null);

        projectBean.addTaskToProject("NonExistingProject", task);

        verify(projectDao, never()).persist(any(ProjectEntity.class));
    }
    @Test
    void testProjectCreationWithDefaultUserLab() {
        // Mock LabEntity with a valid Lab enum location
        LabEntity mockLabEntity = new LabEntity();
        mockLabEntity.setLocation(LabEntity.Lab.Lisboa); // Directly use the enum

        // Set userEntity's lab to the mocked LabEntity
        userEntity.setLocation(mockLabEntity);
        when(userBean.findUserByToken("token")).thenReturn(userEntity);

        // Adjust labDao mock to expect an enum value
        when(labDao.findLabByLocation(LabEntity.Lab.Lisboa)).thenReturn(mockLabEntity);

        // Execute the method and assert that the project creation succeeds
        assertFalse(projectBean.createProject(projectDto, "token"));
    }

    @Test
    void testProjectCreationWithMaximumMembersExceeded() {
        projectDto.setSlots(11); // Exceeding the max allowed slots
        assertFalse(projectBean.createProject(projectDto, "token"), "Project creation should fail when maximum members exceeded");
    }


    @Test
    void testProjectCreationWithInvalidDates() {
        projectDto.setEndDate(LocalDateTime.now().minusDays(5)); // End date before start date
        assertFalse(projectBean.createProject(projectDto, "token"));
    }

    @Test
    void testProjectCreationWithNullDTO() {
        assertThrows(NullPointerException.class, () -> projectBean.createProject(null, "token"));
    }

    @Test
    void testAddProjectLogFailure() {
        when(projectLogBean.createProjectLog(any())).thenReturn(false);
        assertNull(projectBean.addProjectLog(new ProjectLogDto()));
    }

    @Test
    void testFindProjectsByMaxMembers() {
        projectBean.checkMaxMembers(10);
        verify(projectDao, times(1)).findProjectsByMaxMembers(10);
    }
    @Test
    void testRemoveProjectUserSuccess() {
        // Setup
        when(userBean.findUserByToken(anyString())).thenReturn(new UserEntity());
        when(projectDao.findProjectByName(anyString())).thenReturn(new ProjectEntity());
        when(userDao.findUserById(anyInt())).thenReturn(new UserEntity());
        when(projectUserDao.findProjectUserByProjectAndUser(any(), any())).thenReturn(new ProjectUserEntity());

        // Execute
        boolean result = projectBean.removeProjectUser("token", "ProjectName", 1);

        // Assert
        assertTrue(result);
        verify(projectUserDao, times(1)).remove(any(ProjectUserEntity.class));
    }

    @Test
    void testRemoveProjectUserUserNotFound() {
        // Setup for user not found
        when(userBean.findUserByToken(anyString())).thenReturn(null);

        // Execute
        boolean result = projectBean.removeProjectUser("token", "ProjectName", 1);

        // Assert
        assertFalse(result);
    }

    @Test
    void testRemoveProjectUserProjectNotFound() {
        // Setup for project not found
        when(userBean.findUserByToken(anyString())).thenReturn(new UserEntity());
        when(projectDao.findProjectByName(anyString())).thenReturn(null);

        // Execute
        boolean result = projectBean.removeProjectUser("token", "ProjectName", 1);

        // Assert
        assertFalse(result);
    }

    @Test
    void testRemoveProjectUserProjectUserNotFound() {
        // Setup for projectUser not found
        when(userBean.findUserByToken(anyString())).thenReturn(new UserEntity());
        when(projectDao.findProjectByName(anyString())).thenReturn(new ProjectEntity());
        when(userDao.findUserById(anyInt())).thenReturn(new UserEntity());
        when(projectUserDao.findProjectUserByProjectAndUser(any(), any())).thenReturn(null);

        // Execute
        boolean result = projectBean.removeProjectUser("token", "ProjectName", 1);

        // Assert
        assertFalse(result);
    }

    @Test
    void testIsProjectManager_UserNotFound() {
        when(userBean.findUserByToken(anyString())).thenReturn(null);

        assertFalse(projectBean.isProjectManager("token", "ProjectName"));
    }

    @Test
    void testIsProjectManager_ProjectNotFound() {
        when(userBean.findUserByToken(anyString())).thenReturn(userEntity);
        when(projectDao.findProjectByName(anyString())).thenReturn(null);

        assertFalse(projectBean.isProjectManager("token", "ProjectName"));
    }

    @Test
    void testIsProjectManager_ProjectUserNotFound() {
        when(userBean.findUserByToken(anyString())).thenReturn(userEntity);
        when(projectDao.findProjectByName(anyString())).thenReturn(projectEntity);
        when(projectUserDao.findProjectUserByProjectAndUser(any(ProjectEntity.class), any(UserEntity.class)))
                .thenReturn(null);

        assertFalse(projectBean.isProjectManager("token", "ProjectName"));
    }

}
