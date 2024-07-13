import bean.SystemVariablesBean;
import bean.UserBean;
import dao.SystemVariablesDao;
import entities.SystemVariablesEntity;
import dto.SystemVariablesDto;
import entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SystemVariablesBeanTest {

    @Mock
    private SystemVariablesDao systemVariablesDao;

    @Mock
    private UserBean userBean;

    @InjectMocks
    private SystemVariablesBean systemVariablesBean;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        List<UserEntity> mockUsers = new ArrayList<>();
        when(userBean.getAllUsers()).thenReturn(mockUsers);
        when(userBean.getAllUsers()).thenReturn(mockUsers);
    }

    @Test
    void testCreateDefaultSystemVariables_NewVariable() {
        // Setup mock behavior
        when(systemVariablesDao.findSystemVariableById(1)).thenReturn(null);

        // Call method under test
        systemVariablesBean.createDefaultSystemVariables();

        // Verify that systemVariablesDao.create was called once with specific parameters
        verify(systemVariablesDao, times(1)).create(30, 4);
    }

    @Test
    void testCreateDefaultSystemVariables_VariableExists() {
        // Setup mock behavior
        SystemVariablesEntity existingVariable = new SystemVariablesEntity();
        when(systemVariablesDao.findSystemVariableById(1)).thenReturn(existingVariable);

        // Call method under test
        systemVariablesBean.createDefaultSystemVariables();

        // Verify that systemVariablesDao.create was not called
        verify(systemVariablesDao, never()).create(anyInt(), anyInt());
    }

    @Test
    void testSetAndGetSessionTimeout() {
        // Setup mock behavior
        SystemVariablesEntity systemVariable = new SystemVariablesEntity();
        systemVariable.setTimeout(30);
        when(systemVariablesDao.findSystemVariableById(1)).thenReturn(systemVariable);

        // Call method under test
        systemVariablesBean.setSessionTimeout(45);

        // Verify that timeout was set correctly
        assertEquals(45, systemVariable.getTimeout());

        // Test get session timeout
        assertEquals(45, systemVariablesBean.getSessionTimeout());
    }


    @Test
    void testGetSystemVariables() {
        // Setup mock behavior
        SystemVariablesEntity systemVariable = new SystemVariablesEntity();
        systemVariable.setTimeout(30);
        systemVariable.setMaxUsers(10);
        when(systemVariablesDao.findSystemVariableById(1)).thenReturn(systemVariable);

        // Call method under test
        SystemVariablesDto systemVariablesDto = systemVariablesBean.getSystemVariables();

        // Verify DTO conversion
        assertNotNull(systemVariablesDto);
        assertEquals(30, systemVariablesDto.getTimeout());
        assertEquals(10, systemVariablesDto.getMaxUsers());
    }
}

