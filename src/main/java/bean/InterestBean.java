package bean;

import dao.InterestDao;
import dto.InterestDto;
import entities.InterestEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Stateless
public class InterestBean {
    @Inject
    private InterestDao interestDao;
    @EJB
    private UserBean userBean;
    @EJB
    private ProjectBean projectBean;

    public InterestBean() {
    }

    public void createDefaultInterests() {
        // Create default interests
        if (interestDao.findInterestByName("Sustainability") == null) {
            InterestEntity interest1 = new InterestEntity();
            interest1.setName("Sustainability");
            interest1.setInterestType(InterestEntity.InterestType.CAUSES);
            interestDao.persist(interest1);
        }
        if (interestDao.findInterestByName("Education") == null) {
            InterestEntity interest2 = new InterestEntity();
            interest2.setName("Education");
            interest2.setInterestType(InterestEntity.InterestType.CAUSES);
            interestDao.persist(interest2);
        }
        if (interestDao.findInterestByName("Health") == null) {
            InterestEntity interest3 = new InterestEntity();
            interest3.setName("Health");
            interest3.setInterestType(InterestEntity.InterestType.CAUSES);
            interestDao.persist(interest3);
        }
        if (interestDao.findInterestByName("Technology") == null) {
            InterestEntity interest4 = new InterestEntity();
            interest4.setName("Technology");
            interest4.setInterestType(InterestEntity.InterestType.KNOWLEDGE);
            interestDao.persist(interest4);
        }
        if (interestDao.findInterestByName("Science") == null) {
            InterestEntity interest5 = new InterestEntity();
            interest5.setName("Science");
            interest5.setInterestType(InterestEntity.InterestType.KNOWLEDGE);
            interestDao.persist(interest5);
        }
        if (interestDao.findInterestByName("Arts") == null) {
            InterestEntity interest6 = new InterestEntity();
            interest6.setName("Arts");
            interest6.setInterestType(InterestEntity.InterestType.KNOWLEDGE);
            interestDao.persist(interest6);
        }
        if (interestDao.findInterestByName("Sports") == null) {
            InterestEntity interest7 = new InterestEntity();
            interest7.setName("Sports");
            interest7.setInterestType(InterestEntity.InterestType.THEMES);
            interestDao.persist(interest7);
        }
        if (interestDao.findInterestByName("Music") == null) {
            InterestEntity interest8 = new InterestEntity();
            interest8.setName("Music");
            interest8.setInterestType(InterestEntity.InterestType.THEMES);
            interestDao.persist(interest8);
        }
        if (interestDao.findInterestByName("Cinema") == null) {
            InterestEntity interest9 = new InterestEntity();
            interest9.setName("Cinema");
            interest9.setInterestType(InterestEntity.InterestType.THEMES);
            interestDao.persist(interest9);
        }
        if (interestDao.findInterestByName("Other") == null) {
            InterestEntity interest10 = new InterestEntity();
            interest10.setName("Other");
            interest10.setInterestType(InterestEntity.InterestType.OTHER);
            interestDao.persist(interest10);
        }
    }

    public List<InterestDto> findAllInterests() {
        List<InterestEntity> interests = interestDao.findAllInterests();
        List<InterestDto> interestDtos = new ArrayList<>();
        for (InterestEntity interest : interests) {
            InterestDto interestDto = new InterestDto();
            interestDto.setId(interest.getId());
            interestDto.setName(interest.getName());
            interestDto.setInterestType(interest.getInterestType().toString());
            interestDtos.add(interestDto);
        }
        return interestDtos;
    }
    public Set<String> entityToName(Set<InterestEntity> interests){
        Set<String> interestNames = new HashSet<>();
        for(InterestEntity interest : interests){
            interestNames.add(interest.getName());
        }
        return interestNames;
    }
    public Set<InterestEntity> listDtoToEntity(Set<InterestDto> interests){
        Set<InterestEntity> interestEntities = new HashSet<>();
        for(InterestDto interest : interests){
            interestEntities.add(toInterestEntity(interest));
        }
        return interestEntities;
    }

    public InterestDto toInterestDto(InterestEntity interest) {
        InterestDto interestDto = new InterestDto();
        interestDto.setId(interest.getId());
        interestDto.setName(interest.getName());
        interestDto.setInterestType(interest.getInterestType().toString());
        return interestDto;
    }
    public InterestEntity toInterestEntity(InterestDto interestDto){
        InterestEntity interest = new InterestEntity();
        interest.setId(interestDto.getId());
        interest.setName(interestDto.getName());
        interest.setInterestType(InterestEntity.InterestType.valueOf(interestDto.getInterestType()));
        return interest;
    }

    public InterestEntity findInterestByName(String name) {
        return interestDao.findInterestByName(name);
    }
    public boolean createInterest(InterestDto interestDto){
        InterestEntity interest = new InterestEntity();
        interest.setName(interestDto.getName());
        interest.setInterestType(InterestEntity.InterestType.valueOf(interestDto.getInterestType()));
        interestDao.persist(interest);
        return true;
    }
    public Set<InterestEntity> convertStringToInterestEntities(Set<String> interests){
        return interestDao.findInterestsByName(interests);
    }
    public Set<String> convertInterestEntitiesToString(Set<InterestEntity> interests){
        Set <String> interestNames = new HashSet<>();
        for(InterestEntity interest : interests){
            interestNames.add(interest.getName());
        }
        return interestNames;
    }
    public boolean addInterestToProject(String token, String projectName, String interestName){
        InterestEntity interest = interestDao.findInterestByName(interestName);
        if(interest == null){
            return false;
        }
        projectBean.addInterestToProject(token, projectName, interest);
        return true;
    }
    public boolean removeInterestFromProject(String token ,String projectName, String interestName){
        InterestEntity interest = interestDao.findInterestByName(interestName);
        if(interest == null){
            return false;
        }
        projectBean.removeInterestFromProject(token, projectName, interest);
        return true;
    }
    public boolean addInterestToUser(String token, String interestName){
        InterestEntity interest = interestDao.findInterestByName(interestName);
        if(interest == null){
            return false;
        }
        userBean.addInterestToUser(token, interest);
        return true;
    }
    public boolean removeInterestFromUser(String token, String interestName){
        InterestEntity interest = interestDao.findInterestByName(interestName);
        if(interest == null){
            return false;
        }
        userBean.removeInterestFromUser(token, interest);
        return true;
    }
    public List<String> findAllInterestTypes(){
        List<String> interestTypes = new ArrayList<>();
        for(InterestEntity.InterestType interestType : InterestEntity.InterestType.values()){
            interestTypes.add(interestType.toString());
        }
        return interestTypes;
    }
}

