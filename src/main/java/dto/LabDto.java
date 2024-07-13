package dto;

import entities.LabEntity;
/**
 * The DTO class for the lab.
 */
public class LabDto {
    private String location;

    public LabDto() {
    }

    public LabDto(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
