package dto;

public class SkillDto {
    private int id;
    private String name;
    private String skillType;
    private int projetcId;

    public SkillDto() {
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


    public int getProjetcId() {
        return projetcId;
    }

    public void setProjetcId(int projetcId) {
        this.projetcId = projetcId;
    }
}
