package dto;

public class ForcedLogoutDto {
    private String type;
    private String Message;

    public ForcedLogoutDto() {
        this.type = "FORCED_LOGOUT";
        this.Message = "You have been logged out due to inactivity";
    }
}
