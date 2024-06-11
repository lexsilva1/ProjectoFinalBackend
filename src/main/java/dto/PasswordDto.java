package dto;

public class PasswordDto {
    private String password;
    private String confirmPassword;

    public PasswordDto() {
    }

    public PasswordDto(String password, String newPassword) {
        this.password = password;
        this.confirmPassword = newPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return confirmPassword;
    }

    public void setNewPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
