import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.*;

import bean.UserBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import bean.ProjectBean;

import bean.SkillBean;
import dao.SkillDao;
import dto.SkillDto;
import entities.SkillEntity;

public class SkillBeanTest {

    @Mock
    private SkillDao skillDao;

    @Mock
    private UserBean userBean;

    @Mock
    private ProjectBean projectBean;

    @InjectMocks
    private SkillBean skillBean;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllSkills() {
        // Mock data
        List<SkillEntity> mockSkills = new ArrayList<>();
        mockSkills.add(createSkillEntity(1, "Java", SkillEntity.SkillType.SOFTWARE));
        mockSkills.add(createSkillEntity(2, "SQL", SkillEntity.SkillType.SOFTWARE));

        // Mock behavior
        when(skillDao.findAllSkills()).thenReturn(mockSkills);

        // Execute method under test
        List<SkillDto> skillDtos = skillBean.findAllSkills();

        // Verify interactions
        verify(skillDao, times(1)).findAllSkills();

        // Assertions
        assertNotNull(skillDtos);
        assertEquals(2, skillDtos.size());
        assertEquals("Java", skillDtos.get(0).getName());
        assertEquals("SQL", skillDtos.get(1).getName());
    }

    @Test
    void testToSkillEntity() {
        SkillDto skillDto = new SkillDto();
        skillDto.setId(1);
        skillDto.setName("Java");
        skillDto.setSkillType("SOFTWARE");

        SkillEntity skillEntity = skillBean.toSkillEntity(skillDto);

        assertNotNull(skillEntity);
        assertEquals(1, skillEntity.getId());
        assertEquals("Java", skillEntity.getName());
        assertEquals(SkillEntity.SkillType.SOFTWARE, skillEntity.getSkillType());
    }

    @Test
    void testListDtoToEntity() {
        Set<SkillDto> skillDtos = new HashSet<>();
        skillDtos.add(new SkillDto(1, "Java", "SOFTWARE"));
        skillDtos.add(new SkillDto(2, "SQL", "SOFTWARE"));

        Set<SkillEntity> skillEntities = skillBean.listDtoToEntity(skillDtos);

        assertNotNull(skillEntities);
        assertEquals(2, skillEntities.size());
        for (SkillEntity entity : skillEntities) {
            assertTrue(skillDtos.stream()
                    .anyMatch(dto -> dto.getId() == entity.getId() && dto.getName().equals(entity.getName())));
        }
    }

    @Test
    void testToSkillDtos() {
        SkillEntity skillEntity = createSkillEntity(1, "Java", SkillEntity.SkillType.SOFTWARE);

        SkillDto skillDto = skillBean.toSkillDtos(skillEntity);

        assertNotNull(skillDto);
        assertEquals(1, skillDto.getId());
        assertEquals("Java", skillDto.getName());
        assertEquals("SOFTWARE", skillDto.getSkillType());
    }

    @Test
    void testFindSkillByName() {
        String skillName = "Java";
        SkillEntity skillEntity = createSkillEntity(1, skillName, SkillEntity.SkillType.SOFTWARE);

        when(skillDao.findSkillByName(skillName)).thenReturn(skillEntity);

        SkillEntity foundSkill = skillBean.findSkillByName(skillName);

        assertNotNull(foundSkill);
        assertEquals(skillName, foundSkill.getName());
    }

    @Test
    void testCreateSkill() {
        SkillDto skillDto = new SkillDto();
        skillDto.setName("Tubernites");
        skillDto.setSkillType("SOFTWARE");

        boolean created = skillBean.createSkill(skillDto);

        assertTrue(created);
        verify(skillDao, times(1)).persist(any(SkillEntity.class));
    }

    @Test
    void testCreateSkill_NullSkillType() {
        SkillDto skillDto = new SkillDto();
        skillDto.setName("Java");
        skillDto.setSkillType(null);

        boolean created = skillBean.createSkill(skillDto);

        assertFalse(created);
        verify(skillDao, never()).persist(any(SkillEntity.class));
    }

    @Test
    void testCreateSkill_InvalidSkillType() {
        SkillDto skillDto = new SkillDto();
        skillDto.setName("Java");
        skillDto.setSkillType("INVALID");

        boolean created = skillBean.createSkill(skillDto);

        assertFalse(created);
        verify(skillDao, never()).persist(any(SkillEntity.class));
    }

    @Test
    void testCreateSkill_NullName() {
        SkillDto skillDto = new SkillDto();
        skillDto.setName(null);
        skillDto.setSkillType("SOFTWARE");

        boolean created = skillBean.createSkill(skillDto);

        assertFalse(created);
        verify(skillDao, never()).persist(any(SkillEntity.class));
    }

    @Test
    void testAddSkillToUser() {
        String token = "token";
        String skillName = "Java";
        SkillEntity skillEntity = createSkillEntity(1, skillName, SkillEntity.SkillType.SOFTWARE);

        when(skillDao.findSkillByName(skillName)).thenReturn(skillEntity);
        when(userBean.addSkillToUser(token, skillEntity)).thenReturn(true);

        boolean added = skillBean.addSkillToUser(token, skillName);

        assertTrue(added);
        verify(userBean, times(1)).addSkillToUser(token, skillEntity);
    }

    @Test
    void testAddSkillToUser_SkillNotFound() {
        String token = "token";
        String skillName = "Java";

        when(skillDao.findSkillByName(skillName)).thenReturn(null);

        boolean added = skillBean.addSkillToUser(token, skillName);

        assertFalse(added);
        verify(userBean, never()).addSkillToUser(anyString(), any(SkillEntity.class));
    }

    @Test
    void testAddSkillToProject() {
        String token = "token";
        String projectName = "Project A";
        String skillName = "Java";
        SkillEntity skillEntity = createSkillEntity(1, skillName, SkillEntity.SkillType.SOFTWARE);

        when(skillDao.findSkillByName(skillName)).thenReturn(skillEntity);
        when(projectBean.addSkillToProject(token, projectName, skillEntity)).thenReturn(true);

        boolean added = skillBean.addSkilltoProject(token, projectName, skillName);

        assertTrue(added);
        verify(projectBean, times(1)).addSkillToProject(token, projectName, skillEntity);
    }

    @Test
    void testAddSkillToProject_SkillNotFound() {
        String token = "token";
        String projectName = "Project A";
        String skillName = "Java";

        when(skillDao.findSkillByName(skillName)).thenReturn(null);

        boolean added = skillBean.addSkilltoProject(token, projectName, skillName);

        assertFalse(added);
        verify(projectBean, never()).addSkillToProject(anyString(), anyString(), any(SkillEntity.class));
    }

    @Test
    void testRemoveSkillFromProject() {
        String token = "token";
        String projectName = "Project A";
        String skillName = "Java";
        SkillEntity skillEntity = createSkillEntity(1, skillName, SkillEntity.SkillType.SOFTWARE);

        when(skillDao.findSkillByName(skillName)).thenReturn(skillEntity);
        when(projectBean.removeSkillFromProject(token, projectName, skillEntity)).thenReturn(true);

        boolean removed = skillBean.removeSkillFromProject(token, projectName, skillName);

        assertTrue(removed);
        verify(projectBean, times(1)).removeSkillFromProject(token, projectName, skillEntity);
    }

    @Test
    void testRemoveSkillFromProject_SkillNotFound() {
        String token = "token";
        String projectName = "Project A";
        String skillName = "Java";

        when(skillDao.findSkillByName(skillName)).thenReturn(null);

        boolean removed = skillBean.removeSkillFromProject(token, projectName, skillName);

        assertFalse(removed);
        verify(projectBean, never()).removeSkillFromProject(anyString(), anyString(), any(SkillEntity.class));
    }

    @Test
    void testFindAllSkilltypes() {
        List<String> skillTypes = skillBean.findAllSkilltypes();

        assertNotNull(skillTypes);
        assertEquals(4, skillTypes.size()); // Assuming there are 3 skill types defined in SkillEntity.SkillType
        assertTrue(skillTypes.contains("HARDWARE"));
        assertTrue(skillTypes.contains("KNOWLEDGE"));
        assertTrue(skillTypes.contains("SOFTWARE"));
        assertTrue(skillTypes.contains("TOOLS"));
    }

    @Test
    void testFindSkillsByName() {
        List<String> skillNames = Arrays.asList("Java", "SQL", "Python");
        Set<SkillEntity> mockSkills = new HashSet<>();
        mockSkills.add(createSkillEntity(1, "Java", SkillEntity.SkillType.SOFTWARE));
        mockSkills.add(createSkillEntity(2, "SQL", SkillEntity.SkillType.SOFTWARE));

        Set<String> skillNameSet = new HashSet<>(skillNames);
        when(skillDao.findSkillsByName(skillNameSet)).thenReturn(mockSkills);

        Set<SkillEntity> foundSkills = skillBean.findSkillsByName(skillNames);

        assertNotNull(foundSkills);
        assertEquals(2, foundSkills.size());
        for (SkillEntity entity : foundSkills) {
            assertTrue(skillNames.contains(entity.getName()));
        }
    }

    // Additional helper methods

    private SkillEntity createSkillEntity(int id, String name, SkillEntity.SkillType skillType) {
        SkillEntity skillEntity = new SkillEntity();
        skillEntity.setId(id);
        skillEntity.setName(name);
        skillEntity.setSkillType(skillType);
        return skillEntity;
    }
}


