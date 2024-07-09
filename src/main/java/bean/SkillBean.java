package bean;

import dao.SkillDao;
import dto.SkillDto;
import entities.InterestEntity;
import entities.SkillEntity;
import entities.UserEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBs;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Stateless
public class SkillBean {
    @Inject
    private SkillDao skillDao;
    @EJB
    private UserBean userBean;
    @EJB
    private ProjectBean projectBean;

    public void createDefaultSkills(){
        if(skillDao.findSkillByName("Java") == null){
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Java");
            defaultSkill.setSkillType(SkillEntity.SkillType.SOFTWARE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Leadership") == null){
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Leadership");
            defaultSkill.setSkillType(SkillEntity.SkillType.KNOWLEDGE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Communication") == null){
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Communication");
            defaultSkill.setSkillType(SkillEntity.SkillType.KNOWLEDGE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Negotiation") == null){
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Negotiation");
            defaultSkill.setSkillType(SkillEntity.SkillType.KNOWLEDGE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Problem Solving") == null){
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Problem Solving");
            defaultSkill.setSkillType(SkillEntity.SkillType.KNOWLEDGE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Teamwork") == null){
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Teamwork");
            defaultSkill.setSkillType(SkillEntity.SkillType.KNOWLEDGE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Blaster Combat") == null){
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Blaster Combat");
            defaultSkill.setSkillType(SkillEntity.SkillType.HARDWARE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Smuggling") == null){
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Smuggling");
            defaultSkill.setSkillType(SkillEntity.SkillType.KNOWLEDGE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Python") == null){
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Python");
            defaultSkill.setSkillType(SkillEntity.SkillType.SOFTWARE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("C++") == null){
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("C++");
            defaultSkill.setSkillType(SkillEntity.SkillType.SOFTWARE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("JavaScript") == null){
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("JavaScript");
            defaultSkill.setSkillType(SkillEntity.SkillType.SOFTWARE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("HTML") == null){
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("HTML");
            defaultSkill.setSkillType(SkillEntity.SkillType.SOFTWARE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("CSS") == null){
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("CSS");
            defaultSkill.setSkillType(SkillEntity.SkillType.SOFTWARE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("React") == null){
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("React");
            defaultSkill.setSkillType(SkillEntity.SkillType.SOFTWARE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Angular") == null){
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Angular");
            defaultSkill.setSkillType(SkillEntity.SkillType.SOFTWARE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Vue") == null){
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Vue");
            defaultSkill.setSkillType(SkillEntity.SkillType.SOFTWARE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Node.js") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Node.js");
            defaultSkill.setSkillType(SkillEntity.SkillType.SOFTWARE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Robotics") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Robotics");
            defaultSkill.setSkillType(SkillEntity.SkillType.HARDWARE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Arduino") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Arduino");
            defaultSkill.setSkillType(SkillEntity.SkillType.HARDWARE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Raspberry Pi") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Raspberry Pi");
            defaultSkill.setSkillType(SkillEntity.SkillType.HARDWARE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("3D Printing") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("3D Printing");
            defaultSkill.setSkillType(SkillEntity.SkillType.HARDWARE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Soldering") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Soldering");
            defaultSkill.setSkillType(SkillEntity.SkillType.HARDWARE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Circuit Design") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Circuit Design");
            defaultSkill.setSkillType(SkillEntity.SkillType.HARDWARE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Mechanical Design") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Mechanical Design");
            defaultSkill.setSkillType(SkillEntity.SkillType.HARDWARE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Power Tools") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Power Tools");
            defaultSkill.setSkillType(SkillEntity.SkillType.HARDWARE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Biotechnology") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Biotechnology");
            defaultSkill.setSkillType(SkillEntity.SkillType.KNOWLEDGE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Chemistry") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Chemistry");
            defaultSkill.setSkillType(SkillEntity.SkillType.KNOWLEDGE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Physics") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Physics");
            defaultSkill.setSkillType(SkillEntity.SkillType.KNOWLEDGE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Mathematics") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Mathematics");
            defaultSkill.setSkillType(SkillEntity.SkillType.KNOWLEDGE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Statistics") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Statistics");
            defaultSkill.setSkillType(SkillEntity.SkillType.KNOWLEDGE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Data Analysis") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Data Analysis");
            defaultSkill.setSkillType(SkillEntity.SkillType.KNOWLEDGE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Machine Learning") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Machine Learning");
            defaultSkill.setSkillType(SkillEntity.SkillType.KNOWLEDGE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Artificial Intelligence") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Artificial Intelligence");
            defaultSkill.setSkillType(SkillEntity.SkillType.TOOLS);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Git") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Git");
            defaultSkill.setSkillType(SkillEntity.SkillType.TOOLS);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Docker") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Docker");
            defaultSkill.setSkillType(SkillEntity.SkillType.TOOLS);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Kubernetes") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Kubernetes");
            defaultSkill.setSkillType(SkillEntity.SkillType.TOOLS);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Jenkins") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Jenkins");
            defaultSkill.setSkillType(SkillEntity.SkillType.TOOLS);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Jira") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Jira");
            defaultSkill.setSkillType(SkillEntity.SkillType.TOOLS);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Confluence") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Confluence");
            defaultSkill.setSkillType(SkillEntity.SkillType.TOOLS);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Lightsaber Combat") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Lightsaber Combat");
            defaultSkill.setSkillType(SkillEntity.SkillType.TOOLS);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("The Force") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("The Force");
            defaultSkill.setSkillType(SkillEntity.SkillType.KNOWLEDGE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Mind Trick") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Mind Trick");
            defaultSkill.setSkillType(SkillEntity.SkillType.KNOWLEDGE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Telekinesis") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Telekinesis");
            defaultSkill.setSkillType(SkillEntity.SkillType.KNOWLEDGE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Levitation") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Levitation");
            defaultSkill.setSkillType(SkillEntity.SkillType.KNOWLEDGE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Mind Reading") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Mind Reading");
            defaultSkill.setSkillType(SkillEntity.SkillType.KNOWLEDGE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Piloting") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Piloting");
            defaultSkill.setSkillType(SkillEntity.SkillType.HARDWARE);
            Set<UserEntity> users = new HashSet<>();
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Lightsaber Construction") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Lightsaber Construction");
            defaultSkill.setSkillType(SkillEntity.SkillType.HARDWARE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Droid Repair") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Droid Repair");
            defaultSkill.setSkillType(SkillEntity.SkillType.HARDWARE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Wookiee Speak") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Wookiee Speak");
            defaultSkill.setSkillType(SkillEntity.SkillType.KNOWLEDGE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Ewokese") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Ewokese");
            defaultSkill.setSkillType(SkillEntity.SkillType.KNOWLEDGE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Huttese") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Huttese");
            defaultSkill.setSkillType(SkillEntity.SkillType.KNOWLEDGE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Binary Loadlifter") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Binary Loadlifter");
            defaultSkill.setSkillType(SkillEntity.SkillType.HARDWARE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Moisture Farming") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Moisture Farming");
            defaultSkill.setSkillType(SkillEntity.SkillType.HARDWARE);
            skillDao.persist(defaultSkill);
        }
        if(skillDao.findSkillByName("Tatooine Survival") == null) {
            SkillEntity defaultSkill = new SkillEntity();
            defaultSkill.setName("Tatooine Survival");
            defaultSkill.setSkillType(SkillEntity.SkillType.KNOWLEDGE);
            skillDao.persist(defaultSkill);
        }
    }


    public List<SkillDto> findAllSkills(){
        List<SkillEntity> skills = skillDao.findAllSkills();
        List<SkillDto> skillDtos = new ArrayList<>();
        for(SkillEntity skill : skills){
            skillDtos.add(toSkillDtos(skill));
        }
        return skillDtos;
    }

    public SkillDto toSkillDtos(SkillEntity skill){
        SkillDto skillDto = new SkillDto();
        skillDto.setId(skill.getId());
        skillDto.setName(skill.getName());
        skillDto.setSkillType(skill.getSkillType().toString());
        return skillDto;
    }
    public Set<SkillEntity> listDtoToEntity(Set<SkillDto> skillDtos){
        Set<SkillEntity> skills = new HashSet<>();
        for(SkillDto skillDto : skillDtos){
            skills.add(toSkillEntity(skillDto));
        }
        return skills;
    }
    public SkillEntity toSkillEntity(SkillDto skillDto){
        SkillEntity skill = new SkillEntity();
        skill.setId(skillDto.getId());
        skill.setName(skillDto.getName());
        skill.setSkillType(SkillEntity.SkillType.valueOf(skillDto.getSkillType()));
        return skill;
    }
public Set<String> entityToString(Set<SkillEntity> skills){
        Set<String> skillNames = new HashSet<>();
        for(SkillEntity skill : skills){
            skillNames.add(skill.getName());
        }
        return skillNames;
    }
    public SkillEntity findSkillByName(String name){
        return skillDao.findSkillByName(name);
    }
    public boolean createSkill(SkillDto skillDto){
        SkillEntity skill = new SkillEntity();
        skill.setName(skillDto.getName());
        skill.setSkillType(SkillEntity.SkillType.valueOf(skillDto.getSkillType()));
        skillDao.persist(skill);
        return true;
    }
    public Set<SkillEntity> convertStringToSkillEntities(Set<String> skills){
        return skillDao.findSkillsByName(skills);
    }

    public boolean addSkillToUser(String token, String skillName){
        SkillEntity skill = skillDao.findSkillByName(skillName);
        if(skill == null){
            return false;
        }
        return userBean.addSkillToUser(token, skill);
    }
    public boolean removeSkillFromUser(String token, String skillName){
        SkillEntity skill = skillDao.findSkillByName(skillName);
        if(skill == null){
            return false;
        }
        return userBean.removeSkillFromUser(token, skill);
    }
    public boolean addSkilltoProject(String token, String projectName, String skillName){
        SkillEntity skill = skillDao.findSkillByName(skillName);
        if(skill == null){
            return false;
        }
        return projectBean.addSkillToProject(token,projectName, skill);
    }
    public boolean removeSkillFromProject(String token, String projectName, String skillName){
        SkillEntity skill = skillDao.findSkillByName(skillName);
        if(skill == null){
            return false;
        }
        return projectBean.removeSkillFromProject(token, projectName, skill);
    }
    public SkillEntity findSkillByName(String token, String skillName){
        return skillDao.findSkillByName(skillName);
    }
    public List<String> findAllSkilltypes(){
        List<String> skillTypes = new ArrayList<>();
        for(SkillEntity.SkillType skillType : SkillEntity.SkillType.values()){
            skillTypes.add(skillType.toString());
        }
        return skillTypes;
    }
    public Set<SkillEntity> findSkillsByName(List<String> names){
        Set<String> skillNames = new HashSet<>(names);
        return skillDao.findSkillsByName(skillNames);
    }
}
