package dto;

import entities.LabEntity;

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
