package dto;

import entities.ProjectResourceEntity;

public class ProjectResourceDto {

    private int projectId;
    private int resourceId;
    private int quantity;
    private String resourceName;

    public ProjectResourceDto() {
    }
    public ProjectResourceDto(ProjectResourceEntity projectResourceEntity) {
        this.projectId = projectResourceEntity.getProject().getId();
        this.resourceId = projectResourceEntity.getResource().getId();
        this.quantity = projectResourceEntity.getQuantity();
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}
