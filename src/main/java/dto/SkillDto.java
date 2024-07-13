package dto;

public class SkillDto {
    private int id;
    private String name;
    private String skillType;
    private String projectName;

    public SkillDto() {
    }
    public SkillDto(int id, String name, String skillType) {
        this.id = id;
        this.name = name;
        this.skillType = skillType;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkillType() {
        return skillType;
    }

    public void setSkillType(String skillType) {
        this.skillType = skillType;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
