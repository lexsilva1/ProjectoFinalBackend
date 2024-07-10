import bean.UserBean;
import entities.UserEntity;
import dto.MyDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserBeanTest {

    @Mock
    private UserEntity userEntity;

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
}
