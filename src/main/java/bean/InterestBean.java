package bean;

import dao.InterestDao;
import entities.InterestEntity;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class InterestBean {
    @Inject
    private InterestDao interestDao;

    public InterestBean() {
    }

    public void createDefaultInterests(){
        // Create default interests
        if(interestDao.findInterestByName("Sustainability") == null){
            InterestEntity interest1 = new InterestEntity();
            interest1.setName("Sustainability");
            interest1.setInterestType(InterestEntity.InterestType.CAUSES);
            interestDao.persist(interest1);
        }
        if(interestDao.findInterestByName("Education") == null){
            InterestEntity interest2 = new InterestEntity();
            interest2.setName("Education");
            interest2.setInterestType(InterestEntity.InterestType.CAUSES);
            interestDao.persist(interest2);
        }
        if(interestDao.findInterestByName("Health") == null){
            InterestEntity interest3 = new InterestEntity();
            interest3.setName("Health");
            interest3.setInterestType(InterestEntity.InterestType.CAUSES);
            interestDao.persist(interest3);
        }
        if(interestDao.findInterestByName("Technology") == null){
            InterestEntity interest4 = new InterestEntity();
            interest4.setName("Technology");
            interest4.setInterestType(InterestEntity.InterestType.KNOWLEDGE);
            interestDao.persist(interest4);
        }
        if(interestDao.findInterestByName("Science") == null){
            InterestEntity interest5 = new InterestEntity();
            interest5.setName("Science");
            interest5.setInterestType(InterestEntity.InterestType.KNOWLEDGE);
            interestDao.persist(interest5);
        }
        if(interestDao.findInterestByName("Arts") == null){
            InterestEntity interest6 = new InterestEntity();
            interest6.setName("Arts");
            interest6.setInterestType(InterestEntity.InterestType.KNOWLEDGE);
            interestDao.persist(interest6);
        }
        if(interestDao.findInterestByName("Sports") == null){
            InterestEntity interest7 = new InterestEntity();
            interest7.setName("Sports");
            interest7.setInterestType(InterestEntity.InterestType.THEMES);
            interestDao.persist(interest7);
        }
        if(interestDao.findInterestByName("Music") == null){
            InterestEntity interest8 = new InterestEntity();
            interest8.setName("Music");
            interest8.setInterestType(InterestEntity.InterestType.THEMES);
            interestDao.persist(interest8);
        }
        if(interestDao.findInterestByName("Cinema") == null){
            InterestEntity interest9 = new InterestEntity();
            interest9.setName("Cinema");
            interest9.setInterestType(InterestEntity.InterestType.THEMES);
            interestDao.persist(interest9);
        }
        if(interestDao.findInterestByName("Other") == null){
            InterestEntity interest10 = new InterestEntity();
            interest10.setName("Other");
            interest10.setInterestType(InterestEntity.InterestType.OTHER);
            interestDao.persist(interest10);
        }
    }
}
