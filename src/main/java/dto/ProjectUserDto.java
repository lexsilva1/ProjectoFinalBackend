package dto;

public class ProjectUserDto {
    private int id;
    private String firstName;
    private String lastName;
    private String nickname;
    private String projectPhoto;
    private boolean isProjectManager;
    private int userId;
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
        return projectPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.projectPhoto = userPhoto;
    }

    public boolean isProjectManager() {
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
}
