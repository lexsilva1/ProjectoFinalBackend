import bean.*;
import dao.*;
import dto.CreateProjectDto;
import dto.NotificationDto;
import dto.ProjectLogDto;
import entities.LabEntity;
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
import java.util.HashSet;
import java.util.List;

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

    @InjectMocks
    private ProjectBean projectBean;

    private CreateProjectDto projectDto;
    private UserEntity userEntity;
    private LabEntity labEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        projectDto = new CreateProjectDto();
        userEntity = new UserEntity();
        labEntity = new LabEntity();

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

        when(userBean.findUserByToken(anyString())).thenReturn(userEntity);
        when(labDao.findLabByLocation(any())).thenReturn(labEntity);
        when(taskBean.createLastTask(anyString(), any(), any(), any())).thenReturn(new TaskEntity());
        when(skillBean.listDtoToEntity(any())).thenReturn(new HashSet<>());
        when(interestBean.listDtoToEntity(any())).thenReturn(new HashSet<>());
        when(systemVariablesBean.getMaxUsers()).thenReturn(10);
    }

    @Test
    void testSuccessfulProjectCreation() {
        assertTrue(projectBean.createProject(projectDto, "token"));
        verify(projectDao, times(1)).persist(any(ProjectEntity.class));
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
        assertTrue(projectBean.createProject(projectDto, "token"));
    }

    @Test
    void testProjectCreationWithMaximumMembersExceeded() {
        projectDto.setSlots(11); // Assuming the max allowed is 10
        assertTrue(projectBean.createProject(projectDto, "token"));
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
}
