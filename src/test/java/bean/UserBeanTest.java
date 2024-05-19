package bean;

import bean.UserBean;
import dao.UserDao;

import entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import utilities.EncryptHelper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserBeanTest {
    @Mock
    private UserDao userDaoMock;

    @Mock
    private EncryptHelper encryptHelperMock;
    @InjectMocks
    private UserBean userBean;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogout() {
        // Arrange
        UserEntity user = new UserEntity();
        user.setToken("token");
        when(userDaoMock.findUserByToken("token")).thenReturn(user);

        // Act
        userBean.logout("token");

        // Assert
        verify(userDaoMock, times(1)).findUserByToken("token");
        assertNull(user.getToken());
    }
    @Test
    public void testLogin() {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        String encryptedPassword = "encryptedPassword";
        String token = "token";

        UserEntity user = new UserEntity();
        user.setPwdHash(encryptedPassword);

        when(userDaoMock.findUserByEmail(email)).thenReturn(user);
        when(encryptHelperMock.encryptPassword(password)).thenReturn(encryptedPassword);

        // Act
        String result = userBean.login(email, password);

        // Assert
        assertNotNull(result);
        verify(userDaoMock, times(1)).findUserByEmail(email);
        verify(encryptHelperMock, times(1)).encryptPassword(password);
        verify(userDaoMock, times(1)).updateToken(user);
    }
    @Test
    public void testLoginInvalidPassword() {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        String wrongPassword = "wrongPassword";
        String encryptedPassword = "encryptedPassword";

        UserEntity user = new UserEntity();
        user.setPwdHash(encryptedPassword);

        when(userDaoMock.findUserByEmail(email)).thenReturn(user);
        when(encryptHelperMock.encryptPassword(wrongPassword)).thenReturn("wrongEncryptedPassword");

        // Act
        String result = userBean.login(email, wrongPassword);

        // Assert
        assertNull(result);
        verify(userDaoMock, times(1)).findUserByEmail(email);
        verify(encryptHelperMock, times(1)).encryptPassword(wrongPassword);
        verify(userDaoMock, times(0)).updateToken(user);
    }


}
