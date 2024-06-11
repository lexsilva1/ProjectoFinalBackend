package dto;

public class PasswordDto {
    private String password;
    private String confirmPassword;

    public PasswordDto() {
    }

    public PasswordDto(String password, String confirmPassword) {
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setNewPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
