package dto;

import java.time.LocalDateTime;
import java.util.Set;

public class CreateProjectDto {
    private String name;
    private String description;
    private String image;
    private String lab;
    private int slots;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
    private Set<SkillDto> skills;
    private Set<InterestDto> interests;
    private Set<ProjectUserDto> teamMembers;
    private Set<ResourceDto> billOfMaterials;

    public CreateProjectDto() {
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

    public String getLab() {
        return lab;
    }

    public void setLab(String lab) {
        this.lab = lab;
    }

    public int getSlots() {
        return slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Set<ProjectUserDto> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(Set<ProjectUserDto> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public Set<ResourceDto> getBillOfMaterials() {
        return billOfMaterials;
    }

    public void setBillOfMaterials(Set<ResourceDto> billOfMaterials) {
        this.billOfMaterials = billOfMaterials;
    }
}
