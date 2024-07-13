import bean.EmailBean;
import bean.TokenBean;
import bean.UserBean;
import dao.LabDao;
import dao.UserDao;
import dto.ProjectUserDto;
import dto.UserConfirmation;
import entities.LabEntity;
import entities.ProjectUserEntity;
import entities.UserEntity;
import dto.MyDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import org.mockito.MockitoAnnotations;
import utilities.EncryptHelper;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

class UserBeanTest {

    @Mock
    private UserEntity userEntity;
    @Mock
    private ProjectUserEntity projectUserEntity;
    @InjectMocks
    private UserBean userBean;
    @Mock
    private UserDao userDao;

    @Mock
    private EncryptHelper encryptHelper;

    @Mock
    private TokenBean tokenBean;
    @Mock
    private LabDao labDao;
    @Mock
    private EmailBean emailBean;
    private UserEntity mockUser;
    private final String testToken = "testToken";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUser = new UserEntity();
        when(tokenBean.findUserByToken(testToken)).thenReturn(mockUser);
        when(labDao.findLabByLocation(any(LabEntity.Lab.class))).thenReturn(new LabEntity());
        LabEntity lisboaLab = new LabEntity();
        lisboaLab.setLocation(LabEntity.Lab.Lisboa);
        LabEntity portoLab = new LabEntity();
        portoLab.setLocation(LabEntity.Lab.Porto);
        when(labDao.findLabByLocation(LabEntity.Lab.Lisboa)).thenReturn(lisboaLab);
        when(labDao.findLabByLocation(LabEntity.Lab.Porto)).thenReturn(portoLab);
        // Setup mock userEntity
        when(userEntity.getFirstName()).thenReturn("John");
        when(userEntity.getLastName()).thenReturn("Doe");
        when(userEntity.getNickname()).thenReturn("JD");
        when(userEntity.getUserPhoto()).thenReturn("http://example.com/photo.jpg");
        when(userEntity.getId()).thenReturn(1);
        when(userEntity.getRole()).thenReturn(UserEntity.Role.User); // Assuming Role is an enum in UserEntity
        when(userEntity.getPrivacy()).thenReturn(false);
        when(userEntity.getIsConfirmed()).thenReturn(LocalDate.now());
        when(userEntity.getLocation()).thenReturn(lisboaLab);
        when(projectUserEntity.getUser()).thenReturn(userEntity);
        when(projectUserEntity.isProjectManager()).thenReturn(false);
        when(projectUserEntity.getApprovalStatus()).thenReturn(ProjectUserEntity.ApprovalStatus.MEMBER);

    }

    @Test
    void testConvertToMyDto() {
        String token = "sampleToken123";
        MyDto resultDto = userBean.convertToMyDto(userEntity, token);

        assertEquals("John", resultDto.getFirstName());
        assertEquals("Doe", resultDto.getLastName());
        assertEquals("JD", resultDto.getNickname());
        assertEquals("http://example.com/photo.jpg", resultDto.getImage());
        assertEquals(token, resultDto.getToken());
        assertEquals(1, resultDto.getId());
        assertEquals(UserEntity.Role.User.getValue(), resultDto.getRole()); // Assuming Role enum has a getValue method
    }
    @Test
    void testPasswordValidityWithValidPassword() {
        assertTrue(userBean.isPasswordValid("Password1!"));
    }

    @Test
    void testPasswordValidityWithShortPassword() {
        assertFalse(userBean.isPasswordValid("V1!"));
    }

    @Test
    void testPasswordValidityWithoutLowercase() {
        assertFalse(userBean.isPasswordValid("INVALID1!"));
    }

    @Test
    void testPasswordValidityWithoutUppercase() {
        assertFalse(userBean.isPasswordValid("invalid1!"));
    }

    @Test
    void testPasswordValidityWithoutDigit() {
        assertFalse(userBean.isPasswordValid("Invalid!"));
    }

    @Test
    void testPasswordValidityWithoutSpecialCharacter() {
        assertFalse(userBean.isPasswordValid("Invalid1"));
    }

    @Test
    void testPasswordValidityWithOnlySpecialCharacter() {
        assertFalse(userBean.isPasswordValid("!@#$%^&*"));
    }

    @Test
    void testPasswordValidityWithOnlyDigits() {
        assertFalse(userBean.isPasswordValid("123456789"));
    }

    @Test
    void testPasswordValidityWithOnlyLetters() {
        assertFalse(userBean.isPasswordValid("InvalidPassword"));
    }
    @Test
    void testConvertToProjectUserDto() {
        ProjectUserDto projectUserDto = userBean.convertToProjectUserDto(projectUserEntity);

        assertEquals("John", projectUserDto.getFirstName());
        assertEquals("Doe", projectUserDto.getLastName());
        assertEquals("JD", projectUserDto.getNickname());
        assertEquals("http://example.com/photo.jpg", projectUserDto.getUserPhoto());
        assertFalse(projectUserDto.getIsProjectManager());
        assertEquals(1, projectUserDto.getUserId());
        assertEquals(ProjectUserEntity.ApprovalStatus.MEMBER.name(), projectUserDto.getApprovalStatus());
    }

    @Test
    void registerFailsWhenEmailOrPasswordIsEmpty() {
        assertFalse(userBean.register("", "password"));
        assertFalse(userBean.register("email@example.com", ""));
    }

    @Test
    void registerSucceedsAndPersistsUser() {
        when(encryptHelper.encryptPassword(anyString())).thenReturn("encryptedPassword");
        when(encryptHelper.generateToken()).thenReturn("token");
        when(emailBean.sendConfirmationEmail(any(UserEntity.class))).thenReturn(true);

        assertTrue(userBean.register("email@example.com", "password"));

        verify(userDao, times(1)).persist(any(UserEntity.class));
    }

    @Test
    void registerCreatesRegistrationToken() {
        when(encryptHelper.encryptPassword(anyString())).thenReturn("encryptedPassword");
        when(encryptHelper.generateToken()).thenReturn("token");
        when(emailBean.sendConfirmationEmail(any(UserEntity.class))).thenReturn(true);

        userBean.register("email@example.com", "password");

        verify(tokenBean, times(1)).createRegisterToken(anyString(), any(UserEntity.class));
    }

    @Test
    void registerSendsEmail() {
        when(encryptHelper.encryptPassword(anyString())).thenReturn("encryptedPassword");
        when(encryptHelper.generateToken()).thenReturn("token");
        when(emailBean.sendConfirmationEmail(any(UserEntity.class))).thenReturn(true);

        userBean.register("email@example.com", "password");

        verify(emailBean, times(1)).sendConfirmationEmail(any(UserEntity.class));
    }

    @Test
    void registerReturnsFalseWhenEmailSendingFails() {
        when(encryptHelper.encryptPassword(anyString())).thenReturn("encryptedPassword");
        when(encryptHelper.generateToken()).thenReturn("token");
        when(emailBean.sendConfirmationEmail(any(UserEntity.class))).thenReturn(false);

        assertFalse(userBean.register("email@example.com", "password"));
    }
    @Test
    void confirmUserSuccess() {
        UserConfirmation userConfirmation = new UserConfirmation();
        userConfirmation.setUserPhoto("testPhoto.jpg");
        userConfirmation.setFirstName("Test");
        userConfirmation.setLastName("User");
        userConfirmation.setNickname("TestUser");
        userConfirmation.setBio("Test Bio");
        userConfirmation.setLabLocation("Lisboa");

        UserEntity confirmedUser = userBean.confirmUser(testToken, userConfirmation);

        assertNotNull(confirmedUser);
        assertEquals("Test", confirmedUser.getFirstName());
        assertEquals("User", confirmedUser.getLastName());
        assertEquals("TestUser", confirmedUser.getNickname());
        assertEquals("Test Bio", confirmedUser.getBio());
        assertEquals("testPhoto.jpg", confirmedUser.getUserPhoto());
        assertNotNull(confirmedUser.getLocation());
        assertEquals(UserEntity.Role.User, confirmedUser.getRole());
        assertTrue(confirmedUser.getPrivacy());
        assertEquals(LocalDate.now(), confirmedUser.getIsConfirmed());
        verify(tokenBean, times(1)).removeToken(testToken);
    }

    @Test
    void confirmUserNotFound() {
        when(tokenBean.findUserByToken(anyString())).thenReturn(null);

        UserConfirmation userConfirmation = new UserConfirmation();
        UserEntity result = userBean.confirmUser("invalidToken", userConfirmation);

        assertNull(result);
    }
    @Test
    void testValidEmail() {
        assertTrue(userBean.isEmailValid("email@example.com"));
    }

    @Test
    void testEmailWithoutAtSymbol() {
        assertFalse(userBean.isEmailValid("email.example.com"));
    }

    @Test
    void testEmailWithoutDomain() {
        assertFalse(userBean.isEmailValid("email@.com"));
    }

    @Test
    void testEmailWithInvalidCharacters() {
        assertFalse(userBean.isEmailValid("email@exa*mple.com"));
    }

    @Test
    void testEmailWithoutTLD() {
        assertFalse(userBean.isEmailValid("email@example"));
    }
    @Test
    void confirmUserSuccessWithLisboaLab() {
        UserConfirmation userConfirmation = new UserConfirmation();
        userConfirmation.setUserPhoto("testPhoto.jpg");
        userConfirmation.setFirstName("Test");
        userConfirmation.setLastName("User");
        userConfirmation.setNickname("TestUser");
        userConfirmation.setBio("Test Bio");
        userConfirmation.setLabLocation("Lisboa");

        UserEntity confirmedUser = userBean.confirmUser(testToken, userConfirmation);

        assertNotNull(confirmedUser);
        assertEquals("Lisboa", confirmedUser.getLocation().getLocation().toString());
        verify(labDao, times(1)).findLabByLocation(LabEntity.Lab.valueOf("Lisboa"));
    }

    @Test
    void confirmUserSuccessWithPortoLab() {
        UserConfirmation userConfirmation = new UserConfirmation();
        userConfirmation.setUserPhoto("testPhoto.jpg");
        userConfirmation.setFirstName("Test");
        userConfirmation.setLastName("User");
        userConfirmation.setNickname("TestUser");
        userConfirmation.setBio("Test Bio");
        userConfirmation.setLabLocation("Porto");

        UserEntity confirmedUser = userBean.confirmUser(testToken, userConfirmation);

        assertNotNull(confirmedUser);
        assertEquals("Porto", confirmedUser.getLocation().getLocation().toString());
        verify(labDao, times(1)).findLabByLocation(LabEntity.Lab.valueOf("Porto"));
    }


}



