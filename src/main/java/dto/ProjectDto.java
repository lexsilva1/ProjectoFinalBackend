package dto;

import java.util.ArrayList;
import java.util.List;

public class ProjectDto {
    private String name;
    private String description;
    private String image;
    private String status;
    private int lab;
    private String [] skills;
    private String [] interests;
    private List<ProjectUserDto> teamMembers;
    private List<ResourceDto> billOfMaterials;
    private int maxTeamMembers;



    public ProjectDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getLab() {
        return lab;
    }

    public void setLab(int lab) {
        this.lab = lab;
    }

    public String [] getSkills() {
        return skills;
    }

    public void setSkills(String [] skills) {
        this.skills = skills;
    }

    public String [] getInterests() {
        return interests;
    }

    public void setInterests(String [] interests) {
        this.interests = interests;
    }

    public List<ProjectUserDto> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<ProjectUserDto> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public List<ResourceDto> getBillOfMaterials() {
        return billOfMaterials;
    }

    public void setBillOfMaterials(List<ResourceDto> billOfMaterials) {
        this.billOfMaterials = billOfMaterials;
    }

    public int getMaxTeamMembers() {
        return maxTeamMembers;
    }

    public void setMaxTeamMembers(int maxTeamMembers) {
        this.maxTeamMembers = maxTeamMembers;
    }
}
