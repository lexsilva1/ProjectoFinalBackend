package bean;

import dao.InterestDao;
import dto.InterestDto;
import entities.InterestEntity;
import entities.UserEntity;
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
    private static final org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(InterestBean.class);

    public InterestBean() {
    }

    public void createDefaultInterests() {
        // Create default interests
        logger.info("Creating default interests");
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
            Set<UserEntity> users = new HashSet<>();
            users.add(userBean.findUserById(1));
            users.add(userBean.findUserById(4));
            interest5.setUsers(users);
            interestDao.persist(interest5);
        }
        if (interestDao.findInterestByName("Arts") == null) {
            InterestEntity interest6 = new InterestEntity();
            interest6.setName("Arts");
            interest6.setInterestType(InterestEntity.InterestType.KNOWLEDGE);
            Set<UserEntity> users = new HashSet<>();
            users.add(userBean.findUserById(1));
            users.add(userBean.findUserById(4));
            interest6.setUsers(users);
            interestDao.persist(interest6);
        }
        if (interestDao.findInterestByName("Sports") == null) {
            InterestEntity interest7 = new InterestEntity();
            interest7.setName("Sports");
            interest7.setInterestType(InterestEntity.InterestType.THEMES);
            Set<UserEntity> users = new HashSet<>();
            users.add(userBean.findUserById(2));
            users.add(userBean.findUserById(3));
            interest7.setUsers(users);
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
        if(interestDao.findInterestByName("Environment") == null){
            InterestEntity interest11 = new InterestEntity();
            interest11.setName("Environment");
            interest11.setInterestType(InterestEntity.InterestType.CAUSES);
            interestDao.persist(interest11);
        }
        if(interestDao.findInterestByName("Human Rights") == null){
            InterestEntity interest12 = new InterestEntity();
            interest12.setName("Human Rights");
            interest12.setInterestType(InterestEntity.InterestType.CAUSES);
            interestDao.persist(interest12);
        }
        if(interestDao.findInterestByName("Animal Rights") == null){
            InterestEntity interest13 = new InterestEntity();
            interest13.setName("Animal Rights");
            interest13.setInterestType(InterestEntity.InterestType.CAUSES);
            interestDao.persist(interest13);
        }
        if(interestDao.findInterestByName("Droid Rights") == null){
            InterestEntity interest14 = new InterestEntity();
            interest14.setName("Droid Rights");
            interest14.setInterestType(InterestEntity.InterestType.CAUSES);
            interestDao.persist(interest14);
        }
        if(interestDao.findInterestByName("Programming") == null){
            InterestEntity interest15 = new InterestEntity();
            interest15.setName("Programming");
            interest15.setInterestType(InterestEntity.InterestType.KNOWLEDGE);
            interestDao.persist(interest15);
        }
        if(interestDao.findInterestByName("Rebellion") == null){
            InterestEntity interest16 = new InterestEntity();
            interest16.setName("Rebellion");
            interest16.setInterestType(InterestEntity.InterestType.THEMES);
            interestDao.persist(interest16);
        }
        if(interestDao.findInterestByName("Revolution") == null){
            InterestEntity interest17 = new InterestEntity();
            interest17.setName("Revolution");
            interest17.setInterestType(InterestEntity.InterestType.THEMES);
            Set<UserEntity> users = new HashSet<>();
            users.add(userBean.findUserByEmail("princessleia@gmail.com"));
            interest17.setUsers(users);
            interestDao.persist(interest17);
        }
        if(interestDao.findInterestByName("Peace") == null){
            InterestEntity interest18 = new InterestEntity();
            interest18.setName("Peace");
            interest18.setInterestType(InterestEntity.InterestType.CAUSES);
            interestDao.persist(interest18);
        }
        if(interestDao.findInterestByName("War") == null){
            InterestEntity interest19 = new InterestEntity();
            interest19.setName("War");
            interest19.setInterestType(InterestEntity.InterestType.THEMES);
            interestDao.persist(interest19);
        }
        if(interestDao.findInterestByName("The Force") == null){
            InterestEntity interest20 = new InterestEntity();
            interest20.setName("The Force");
            interest20.setInterestType(InterestEntity.InterestType.THEMES);
            interestDao.persist(interest20);
        }
        if(interestDao.findInterestByName("The Dark Side") == null){
            InterestEntity interest21 = new InterestEntity();
            interest21.setName("The Dark Side");
            interest21.setInterestType(InterestEntity.InterestType.THEMES);
            interestDao.persist(interest21);
        }
        if(interestDao.findInterestByName("The Light Side") == null){
            InterestEntity interest22 = new InterestEntity();
            interest22.setName("The Light Side");
            interest22.setInterestType(InterestEntity.InterestType.THEMES);
            interestDao.persist(interest22);
        }
        if(interestDao.findInterestByName("The Empire") == null){
            InterestEntity interest23 = new InterestEntity();
            interest23.setName("The Empire");
            interest23.setInterestType(InterestEntity.InterestType.THEMES);
            interestDao.persist(interest23);
        }
        if(interestDao.findInterestByName("The Rebellion") == null){
            InterestEntity interest24 = new InterestEntity();
            interest24.setName("The Rebellion");
            interest24.setInterestType(InterestEntity.InterestType.THEMES);
            interestDao.persist(interest24);
        }
        if(interestDao.findInterestByName("The Republic") == null){
            InterestEntity interest25 = new InterestEntity();
            interest25.setName("The Republic");
            interest25.setInterestType(InterestEntity.InterestType.THEMES);
            interestDao.persist(interest25);
        }
        if(interestDao.findInterestByName("The Jedi") == null){
            InterestEntity interest26 = new InterestEntity();
            interest26.setName("The Jedi");
            interest26.setInterestType(InterestEntity.InterestType.THEMES);
            interestDao.persist(interest26);
        }
        if(interestDao.findInterestByName("Mandalore") == null){
            InterestEntity interest27 = new InterestEntity();
            interest27.setName("Mandalore");
            interest27.setInterestType(InterestEntity.InterestType.THEMES);
            interestDao.persist(interest27);

        }
        if(interestDao.findInterestByName("Bounty Hunting") == null){
            InterestEntity interest28 = new InterestEntity();
            interest28.setName("Bounty Hunting");
            interest28.setInterestType(InterestEntity.InterestType.THEMES);
            interestDao.persist(interest28);
        }

    }

    public List<InterestDto> findAllInterests() {
        logger.info("Fetching all interests");
        List<InterestEntity> interests = interestDao.findAllInterests();
        List<InterestDto> interestDtos = new ArrayList<>();
        for (InterestEntity interest : interests) {
            InterestDto interestDto = new InterestDto();
            interestDto.setId(interest.getId());
            interestDto.setName(interest.getName());
            interestDto.setInterestType(interest.getInterestType().toString());
            interestDtos.add(interestDto);
        }
        logger.info("All interests fetched successfully");
        return interestDtos;
    }
    public Set<InterestEntity> listDtoToEntity(Set<InterestDto> interests){
        logger.info("Converting list of interests from dto to entity");
        Set<InterestEntity> interestEntities = new HashSet<>();
        for(InterestDto interest : interests){
            interestEntities.add(toInterestEntity(interest));
        }
        logger.info("List of interests converted successfully");
        return interestEntities;
    }

    public InterestDto toInterestDto(InterestEntity interest) {
        logger.info("Converting interest to dto");
        InterestDto interestDto = new InterestDto();
        interestDto.setId(interest.getId());
        interestDto.setName(interest.getName());
        interestDto.setInterestType(interest.getInterestType().toString());
        logger.info("Interest converted to dto successfully");
        return interestDto;
    }
    public InterestEntity toInterestEntity(InterestDto interestDto){
        logger.info("Converting interest to entity");
        InterestEntity interest = new InterestEntity();
        interest.setId(interestDto.getId());
        interest.setName(interestDto.getName());
        interest.setInterestType(InterestEntity.InterestType.valueOf(interestDto.getInterestType()));
        logger.info("Interest converted to entity successfully");
        return interest;
    }

    public InterestEntity findInterestByName(String name) {
        logger.info("Finding interest by name");
        return interestDao.findInterestByName(name);
    }
    public boolean createInterest(InterestDto interestDto){
        logger.info("Creating interest");
        InterestEntity interest = new InterestEntity();
        interest.setName(interestDto.getName());
        interest.setInterestType(InterestEntity.InterestType.valueOf(interestDto.getInterestType()));
        interestDao.persist(interest);
        logger.info("Interest created successfully");
        return true;
    }

    public boolean addInterestToProject(String token, String projectName, String interestName){
        logger.info("Adding interest to project");
        InterestEntity interest = interestDao.findInterestByName(interestName);
        if(interest == null){
            logger.error("Interest not found");
            return false;
        }
        projectBean.addInterestToProject(token, projectName, interest);
        logger.info("Interest {} added to project {} successfully by user with token {}", interestName, projectName, token);
        return true;
    }
    public boolean removeInterestFromProject(String token ,String projectName, String interestName){
        logger.info("Removing interest from project");
        InterestEntity interest = interestDao.findInterestByName(interestName);
        if(interest == null){
            logger.error("Interest not found");
            return false;
        }
        projectBean.removeInterestFromProject(token, projectName, interest);
        logger.info("Interest {} removed from project {} successfully by user with token {}", interestName, projectName, token);
        return true;
    }
    public boolean addInterestToUser(String token, String interestName){
        logger.info("Adding interest to user");
        InterestEntity interest = interestDao.findInterestByName(interestName);
        if(interest == null){
            logger.error("Interest not found");
            return false;
        }
        userBean.addInterestToUser(token, interest);
        logger.info("Interest {} added to user with token {} successfully", interestName, token);
        return true;
    }
    public boolean removeInterestFromUser(String token, String interestName){
        logger.info("Removing interest from user");
        InterestEntity interest = interestDao.findInterestByName(interestName);
        if(interest == null){
            logger.error("Interest not found");
            return false;
        }
        userBean.removeInterestFromUser(token, interest);
        logger.info("Interest {} removed from user with token {} successfully", interestName, token);
        return true;
    }
    public List<String> findAllInterestTypes(){
        logger.info("Fetching all interest types");
        List<String> interestTypes = new ArrayList<>();
        for(InterestEntity.InterestType interestType : InterestEntity.InterestType.values()){
            interestTypes.add(interestType.toString());
        }
        logger.info("All interest types fetched successfully");
        return interestTypes;
    }
}

