package dto;
/**
 * The DTO class for the system variables.
 */
public class SystemVariablesDto {
    private int timeout;
    private int maxUsers;

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(int maxUsers) {
        this.maxUsers = maxUsers;
    }
}
