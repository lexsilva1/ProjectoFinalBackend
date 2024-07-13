import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import bean.ProjectBean;
import bean.UserBean;
import bean.InterestBean;
import dao.InterestDao;
import dto.InterestDto;
import entities.InterestEntity;

public class InterestBeanTest {

    @Mock
    private InterestDao interestDao;

    @Mock
    private UserBean userBean;

    @Mock
    private ProjectBean projectBean;

    @InjectMocks
    private InterestBean interestBean;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllInterests() {
        // Mock data
        List<InterestEntity> mockInterests = new ArrayList<>();
        mockInterests.add(createInterestEntity(1, "Music", InterestEntity.InterestType.THEMES));
        mockInterests.add(createInterestEntity(2, "Sports", InterestEntity.InterestType.THEMES));

        // Mock behavior
        when(interestDao.findAllInterests()).thenReturn(mockInterests);

        // Execute method under test
        List<InterestDto> interestDtos = interestBean.findAllInterests();

        // Verify interactions
        verify(interestDao, times(1)).findAllInterests();

        // Assertions
        assertNotNull(interestDtos);
        assertEquals(2, interestDtos.size());
        assertEquals("Music", interestDtos.get(0).getName());
        assertEquals("Sports", interestDtos.get(1).getName());
    }

    @Test
    void testToInterestEntity() {
        InterestDto interestDto = new InterestDto();
        interestDto.setId(1);
        interestDto.setName("Music");
        interestDto.setInterestType("THEMES");

        InterestEntity interestEntity = interestBean.toInterestEntity(interestDto);

        assertNotNull(interestEntity);
        assertEquals(1, interestEntity.getId());
        assertEquals("Music", interestEntity.getName());
        assertEquals(InterestEntity.InterestType.THEMES, interestEntity.getInterestType());
    }

    @Test
    void testListDtoToEntity() {
        Set<InterestDto> interestDtos = new HashSet<>();
        interestDtos.add(createInterestDto(1, "Music", "THEMES"));
        interestDtos.add(createInterestDto(2, "Sports", "THEMES"));

        Set<InterestEntity> interestEntities = interestBean.listDtoToEntity(interestDtos);

        assertNotNull(interestEntities);
        assertEquals(2, interestEntities.size());
        for (InterestEntity entity : interestEntities) {
            assertTrue(interestDtos.stream()
                    .anyMatch(dto -> dto.getId() == entity.getId() && dto.getName().equals(entity.getName())));
        }
    }

    @Test
    void testToInterestDto() {
        InterestEntity interestEntity = createInterestEntity(1, "Music", InterestEntity.InterestType.THEMES);

        InterestDto interestDto = interestBean.toInterestDto(interestEntity);

        assertNotNull(interestDto);
        assertEquals(1, interestDto.getId());
        assertEquals("Music", interestDto.getName());
        assertEquals("THEMES", interestDto.getInterestType());
    }

    @Test
    void testFindInterestByName() {
        String interestName = "Music";
        InterestEntity interestEntity = createInterestEntity(1, interestName, InterestEntity.InterestType.THEMES);

        when(interestDao.findInterestByName(interestName)).thenReturn(interestEntity);

        InterestEntity foundInterest = interestBean.findInterestByName(interestName);

        assertNotNull(foundInterest);
        assertEquals(interestName, foundInterest.getName());
    }

    @Test
    void testCreateInterest() {
        InterestDto interestDto = new InterestDto();
        interestDto.setName("Music");
        interestDto.setInterestType("THEMES");

        boolean created = interestBean.createInterest(interestDto);

        assertTrue(created);
        verify(interestDao, times(1)).persist(any(InterestEntity.class));
    }

    @Test
    void testCreateInterest_NullInterestType() {
        InterestDto interestDto = new InterestDto();
        interestDto.setName("Music");
        interestDto.setInterestType(null);

        boolean created = interestBean.createInterest(interestDto);

        assertFalse(created);
    }

    @Test
    void testCreateInterest_InvalidInterestType() {
        InterestDto interestDto = new InterestDto();
        interestDto.setName("Music");
        interestDto.setInterestType("INVALID");

        boolean created = interestBean.createInterest(interestDto);

        assertFalse(created);
        verify(interestDao, never()).persist(any(InterestEntity.class));
    }

    @Test
    void testCreateInterest_NullName() {
        InterestDto interestDto = new InterestDto();
        interestDto.setName(null);
        interestDto.setInterestType("HOBBY");

        boolean created = interestBean.createInterest(interestDto);

        assertFalse(created);
        verify(interestDao, never()).persist(any(InterestEntity.class));
    }

    @Test
    void testAddInterestToUser() {
        String token = "token";
        String interestName = "Music";
        InterestEntity interestEntity = createInterestEntity(1, interestName, InterestEntity.InterestType.THEMES);

        when(interestDao.findInterestByName(interestName)).thenReturn(interestEntity);
        when(userBean.addInterestToUser(token, interestEntity)).thenReturn(true);

        boolean added = interestBean.addInterestToUser(token, interestName);

        assertTrue(added);

        // Verify that userBean.addInterestToUser was called once with the expected arguments
        verify(userBean, times(1)).addInterestToUser(token, interestEntity);
    }


    @Test
    void testRemoveInterestFromUser() {
        String token = "token";
        String interestName = "Music";
        InterestEntity interestEntity = createInterestEntity(1, interestName, InterestEntity.InterestType.THEMES);

        // Mocking the behavior of interestDao.findInterestByName()
        when(interestDao.findInterestByName(interestName)).thenReturn(interestEntity);

        // Call the actual method under test
        boolean removed = interestBean.removeInterestFromUser(token, interestName);

        // Assert the result
        assertTrue(removed);

        // Verify interactions with dependencies
        verify(interestDao, times(1)).findInterestByName(interestName);
        verify(userBean, times(1)).removeInterestFromUser(token, interestEntity);
    }


    @Test
    void testFindAllInterestTypes() {
        List<String> interestTypes = interestBean.findAllInterestTypes();

        assertNotNull(interestTypes);
        assertEquals(4, interestTypes.size()); // Assuming there are 3 interest types defined in InterestEntity.InterestType
        assertTrue(interestTypes.contains("THEMES"));
        assertTrue(interestTypes.contains("CAUSES"));
        assertTrue(interestTypes.contains("OTHER"));
        assertTrue(interestTypes.contains("KNOWLEDGE"));
    }

    // Additional helper methods

    private InterestEntity createInterestEntity(int id, String name, InterestEntity.InterestType interestType) {
        InterestEntity interestEntity = new InterestEntity();
        interestEntity.setId(id);
        interestEntity.setName(name);
        interestEntity.setInterestType(interestType);
        return interestEntity;
    }

    private InterestDto createInterestDto(int id, String name, String interestType) {
        InterestDto interestDto = new InterestDto();
        interestDto.setId(id);
        interestDto.setName(name);
        interestDto.setInterestType(interestType);
        return interestDto;
    }
}

