package dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
/**
 * The DTO class for the project.
 */
public class ProjectDto {
    private String name;
    private String description;
    private String image;
    private String status;
    private String lab;
    private Set<SkillDto> skills;
    private Set<InterestDto> interests;
    private List<ProjectUserDto> teamMembers;
    private List<ResourceDto> billOfMaterials;
    private int maxTeamMembers;
    public LocalDateTime startDate;
    public LocalDateTime endDate;
    public LocalDateTime creationDate;




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

    public String getLab() {
        return lab;
    }

    public void setLab(String lab) {
        this.lab = lab;
    }

    public Set<SkillDto> getSkills() {
        return skills;
    }

    public void setSkills(Set<SkillDto> skills) {
        this.skills = skills;
    }

    public Set<InterestDto> getInterests() {
        return interests;
    }

    public void setInterests(Set<InterestDto> interests) {
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
