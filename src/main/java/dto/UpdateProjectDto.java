package dto;
/**
 * The DTO class for the user confirmation.
 */
public class UpdateProjectDto {
    private String lab;
    private String description;

    public UpdateProjectDto() {
    }

    public String getLab() {
        return lab;
    }

    public void setLab(String lab) {
        this.lab = lab;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
