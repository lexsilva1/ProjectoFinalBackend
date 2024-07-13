package dto;
/**
 * The DTO class for the project user.
 */
public class ProjectUserDto {
    private int id;
    private String firstName;
    private String lastName;
    private String nickname;
    private String userPhoto;
    private boolean isProjectManager;
    private int userId;
    private String approvalStatus;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public boolean getIsProjectManager() {
        return isProjectManager;
    }

    public void setProjectManager(boolean projectManager) {
        isProjectManager = projectManager;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }
}
