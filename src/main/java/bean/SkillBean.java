package bean;

import dao.SkillDao;
import dto.SkillDto;
import entities.InterestEntity;
import entities.SkillEntity;
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
    public SkillEntity findSkillByName(String name){
        return skillDao.findSkillByName(name);
    }
    public void createSkill(SkillDto skillDto){
        SkillEntity skill = new SkillEntity();
        skill.setName(skillDto.getName());
        skill.setSkillType(SkillEntity.SkillType.valueOf(skillDto.getSkillType()));
        skillDao.persist(skill);
    }
    public Set<SkillEntity> convertStringToSkillEntities(Set<String> skills){
        return skillDao.findSkillsByName(skills);
    }
    public Set<String> convertSkillEntitiestoString(Set<SkillEntity> skills){
        Set<String> skillEntities = new HashSet<>();
        for(SkillEntity skill : skills){
            skillEntities.add(skill.getName());
        }
        return skillEntities;
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
    public boolean addSkilltoProject(String token, int projectId, String skillName){
        SkillEntity skill = skillDao.findSkillByName(skillName);
        if(skill == null){
            return false;
        }
        return projectBean.addSkillToProject(token,projectId, skill);
    }
    public boolean removeSkillFromProject(String token, int projectId, String skillName){
        SkillEntity skill = skillDao.findSkillByName(skillName);
        if(skill == null){
            return false;
        }
        return projectBean.removeSkillFromProject(token, projectId, skill);
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
