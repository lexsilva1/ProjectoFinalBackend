import bean.UserBean;
import dto.ProjectUserDto;
import entities.ProjectUserEntity;
import entities.UserEntity;
import dto.MyDto;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserBeanTest {

    @Mock
    private UserEntity userEntity;
    @Mock
    private ProjectUserEntity projectUserEntity;

    private UserBean userBean;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userBean = new UserBean();

        // Setup mock userEntity
        when(userEntity.getFirstName()).thenReturn("John");
        when(userEntity.getLastName()).thenReturn("Doe");
        when(userEntity.getNickname()).thenReturn("JD");
        when(userEntity.getUserPhoto()).thenReturn("http://example.com/photo.jpg");
        when(userEntity.getId()).thenReturn(1);
        when(userEntity.getRole()).thenReturn(UserEntity.Role.User); // Assuming Role is an enum in UserEntity

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
}
