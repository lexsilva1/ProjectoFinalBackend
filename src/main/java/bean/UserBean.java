package bean;

import dao.*;
import dto.*;
import entities.*;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import utilities.EncryptHelper;
import websocket.Notifications;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Stateless
public class UserBean {
    @Inject
    private UserDao userDao;
    @Inject
    private EncryptHelper encryptHelper;
    @Inject
    private LabDao labDao;
    @Inject
    private EmailBean emailBean;
    @Inject
    private ProjectUserDao projectUserDao;
    @EJB
    private SkillBean skillBean;
    @EJB
    private InterestBean interestBean;
    @Inject
    private SkillDao skillDao;
    @Inject
    InterestDao intererestDao;
    @Inject
    TokenBean tokenBean;
    @EJB
    NotificationBean notificationBean;
    private static final org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(UserBean.class);
    public UserBean() {
    }

    /**
     * Creates a default admin user if it doesn't exist
     */
    public void createDefaultUsers() {
        logger.info("Creating default users");
        if (userDao.findUserByEmail("admin@admin.com") == null) {
            UserEntity admin = new UserEntity();
            admin.setFirstName("admin");
            admin.setLastName("admin");
            admin.setEmail("admin@admin.com");
            admin.setPwdHash(encryptHelper.encryptPassword("admin"));
            admin.setLocation(labDao.findLabByLocation(LabEntity.Lab.Coimbra));
            admin.setCreationDate(LocalDateTime.now());
            admin.setIsConfirmed(LocalDate.now());
            admin.setActive(true);
            admin.setRole(UserEntity.Role.Admin);
            List<SkillEntity> skills = skillDao.findAll();
            Collections.shuffle(skills);
            Set<SkillEntity> userSkills = new HashSet<>();
            for(int i = 0; i < Math.min(skills.size(), 5); i++) {
                userSkills.add(skills.get(i));
            }
            admin.setSkills(userSkills);
            List<InterestEntity> interests = intererestDao.findAll();
            Collections.shuffle(interests);
            Set<InterestEntity> userInterests = new HashSet<>();
            for(int i = 0; i < Math.min(interests.size(), 5); i++) {
                userInterests.add(interests.get(i));
            }
            admin.setInterests(userInterests);
            userDao.persist(admin);
        }
        if (userDao.findUserByEmail("tozemarreco@gmail.com") == null) {
            UserEntity toze = new UserEntity();
            toze.setFirstName("António");
            toze.setLastName("Marreco");
            toze.setEmail("tozemarreco@gmail.com");
            toze.setPwdHash(encryptHelper.encryptPassword("123Oliveira#4"));
            toze.setLocation(labDao.findLabByLocation(LabEntity.Lab.Porto));
            toze.setCreationDate(LocalDateTime.now().minusDays(5));
            toze.setIsConfirmed(LocalDate.now().minusDays(3));
            toze.setActive(true);
            toze.setRole(UserEntity.Role.User);
            toze.setPrivacy(true);
            toze.setUserPhoto("http://localhost:8080/ProjectoFinalImages/2.jpg?t=" + System.currentTimeMillis());
            List<SkillEntity> skills = skillDao.findAll();
            Collections.shuffle(skills);
            Set<SkillEntity> userSkills = new HashSet<>();
            for(int i = 0; i < Math.min(skills.size(), 5); i++) {
                userSkills.add(skills.get(i));
            }
            toze.setSkills(userSkills);
            List<InterestEntity> interests = intererestDao.findAll();
            Collections.shuffle(interests);
            Set<InterestEntity> userInterests = new HashSet<>();
            for(int i = 0; i < Math.min(interests.size(), 5); i++) {
                userInterests.add(interests.get(i));
            }
            toze.setInterests(userInterests);
            userDao.persist(toze);

        }
        if (userDao.findUserByEmail("mariamacaca@gmail.com") == null) {
            UserEntity maria = new UserEntity();
            maria.setEmail("mariamacaca@gmail.com");
            maria.setFirstName("Maria");
            maria.setLastName("Simia");
            maria.setNickname("MariaMacaca");
            maria.setCreationDate(LocalDateTime.now().minusDays(3));
            maria.setIsConfirmed(LocalDate.now().minusDays(1));
            maria.setActive(true);
            maria.setPwdHash(encryptHelper.encryptPassword("Password1!"));
            maria.setLocation(labDao.findLabByLocation(LabEntity.Lab.Lisboa));
            maria.setRole(UserEntity.Role.User);
            maria.setUserPhoto("http://localhost:8080/ProjectoFinalImages/3.jpg?t=" + System.currentTimeMillis());
            List<SkillEntity> skills = skillDao.findAll();
            Collections.shuffle(skills);
            Set<SkillEntity> userSkills = new HashSet<>();
            for(int i = 0; i < Math.min(skills.size(), 5); i++) {
                userSkills.add(skills.get(i));
            }
            maria.setSkills(userSkills);
            List<InterestEntity> interests = intererestDao.findAll();
            Collections.shuffle(interests);
            Set<InterestEntity> userInterests = new HashSet<>();
            for(int i = 0; i < Math.min(interests.size(), 5); i++) {
                userInterests.add(interests.get(i));
            }
            maria.setInterests(userInterests);
            userDao.persist(maria);
        }
        if (userDao.findUserByEmail("zetamplario@gmail.com") == null) {
            UserEntity ze = new UserEntity();
            ze.setEmail("zetamplario@gmail.com");
            ze.setFirstName("Jose");
            ze.setLastName("Teutao");
            ze.setNickname("KnightTemplar");
            ze.setCreationDate(LocalDateTime.now().minusDays(2));
            ze.setIsConfirmed(LocalDate.now().minusDays(1));
            ze.setActive(true);
            ze.setPwdHash(encryptHelper.encryptPassword("Password1!"));
            ze.setLocation(labDao.findLabByLocation(LabEntity.Lab.Tomar));
            ze.setRole(UserEntity.Role.User);
            ze.setUserPhoto("http://localhost:8080/ProjectoFinalImages/4.jpg?t=" + System.currentTimeMillis());
            List<SkillEntity> skills = skillDao.findAll();
            Collections.shuffle(skills);
            Set<SkillEntity> userSkills = new HashSet<>();
            for(int i = 0; i < Math.min(skills.size(), 5); i++) {
                userSkills.add(skills.get(i));
            }
            ze.setSkills(userSkills);
            List<InterestEntity> interests = intererestDao.findAll();
            Collections.shuffle(interests);
            Set<InterestEntity> userInterests = new HashSet<>();
            for(int i = 0; i < Math.min(interests.size(), 5); i++) {
                userInterests.add(interests.get(i));
            }
            ze.setInterests(userInterests);
            userDao.persist(ze);
        }
        if (userDao.findUserByEmail("darthvader@gmail.com") == null) {
            UserEntity vader = new UserEntity();
            vader.setEmail("darthvader@gmail.com");
            vader.setFirstName("Anakin");
            vader.setLastName("Skywalker");
            vader.setNickname("DarthVader");
            vader.setCreationDate(LocalDateTime.now().minusDays(1));
            vader.setIsConfirmed(LocalDate.now());
            vader.setActive(true);
            vader.setPwdHash(encryptHelper.encryptPassword("Password1!"));
            vader.setLocation(labDao.findLabByLocation(LabEntity.Lab.Lisboa));
            vader.setRole(UserEntity.Role.User);
            vader.setUserPhoto("http://localhost:8080/ProjectoFinalImages/5.jpg?t=" + System.currentTimeMillis());
            Set<SkillEntity> skills = new HashSet<>();
            skills.add(skillDao.findSkillByName("Lightsaber Combat"));
            skills.add(skillDao.findSkillByName("Telekinesis"));
            skills.add(skillDao.findSkillByName("Piloting"));
            skills.add(skillDao.findSkillByName("Droid Repair"));
            skills.add(skillDao.findSkillByName("Tatooine Survival"));
            vader.setSkills(skills);
            Set<InterestEntity> interests = new HashSet<>();
            interests.add(intererestDao.findInterestByName("The Dark Side"));
            interests.add(intererestDao.findInterestByName("The Empire"));
            interests.add(intererestDao.findInterestByName("War"));
            interests.add(intererestDao.findInterestByName("Droid Rights"));
            interests.add(intererestDao.findInterestByName("Health"));
            vader.setInterests(interests);
            userDao.persist(vader);
        }
        if (userDao.findUserByEmail("luke@gmail.com") == null) {
            UserEntity luke = new UserEntity();
            luke.setEmail("luke@gmail.com");
            luke.setFirstName("Luke");
            luke.setLastName("Skywalker");
            luke.setNickname("JediKnight");
            luke.setCreationDate(LocalDateTime.now());
            luke.setIsConfirmed(LocalDate.now());
            luke.setActive(true);
            luke.setPwdHash(encryptHelper.encryptPassword("Password1!"));
            luke.setLocation(labDao.findLabByLocation(LabEntity.Lab.Vila_Real));
            luke.setRole(UserEntity.Role.User);
            luke.setUserPhoto("http://localhost:8080/ProjectoFinalImages/6.jpg?t=" + System.currentTimeMillis());
            Set<SkillEntity> skills = new HashSet<>();
            skills.add(skillDao.findSkillByName("Lightsaber Combat"));
            skills.add(skillDao.findSkillByName("Telekinesis"));
            skills.add(skillDao.findSkillByName("Piloting"));
            skills.add(skillDao.findSkillByName("Droid Repair"));
            skills.add(skillDao.findSkillByName("Tatooine Survival"));
            skills.add(skillDao.findSkillByName("Moisture Farming"));
            luke.setSkills(skills);
            Set<InterestEntity> interests = new HashSet<>();
            interests.add(intererestDao.findInterestByName("The Light Side"));
            interests.add(intererestDao.findInterestByName("Mandalore"));
            interests.add(intererestDao.findInterestByName("Peace"));
            interests.add(intererestDao.findInterestByName("Programming"));
            luke.setInterests(interests);
            userDao.persist(luke);
        }
        if(userDao.findUserByEmail("princessleia@gmail.com") == null) {
            UserEntity leia = new UserEntity();
            leia.setEmail("princessleia@gmail.com");
            leia.setFirstName("Leia");
            leia.setLastName("Organa");
            leia.setNickname("PrincessLeia");
            leia.setCreationDate(LocalDateTime.now());
            leia.setIsConfirmed(LocalDate.now());
            leia.setActive(true);
            leia.setPwdHash(encryptHelper.encryptPassword("Password1!"));
            leia.setLocation(labDao.findLabByLocation(LabEntity.Lab.Vila_Real));
            leia.setRole(UserEntity.Role.User);
            leia.setUserPhoto("http://localhost:8080/ProjectoFinalImages/7.jpg?t=" + System.currentTimeMillis());
            Set<SkillEntity> skills = new HashSet<>();
            skills.add(skillDao.findSkillByName("Power Tools"));
            skills.add(skillDao.findSkillByName("Robotics"));
            skills.add(skillDao.findSkillByName("Leadership"));
            skills.add(skillDao.findSkillByName("Negotiation"));
            leia.setSkills(skills);
            Set<InterestEntity> interests = new HashSet<>();
            interests.add(intererestDao.findInterestByName("The Rebellion"));
            interests.add(intererestDao.findInterestByName("Peace"));
            interests.add(intererestDao.findInterestByName("Human Rights"));
            interests.add(intererestDao.findInterestByName("Droid Rights"));
            leia.setInterests(interests);
            userDao.persist(leia);
        }
        if(userDao.findUserByEmail("hansolo@gmail.com") == null) {
            UserEntity han = new UserEntity();
            han.setEmail("hansolo@gmail.com");
            han.setFirstName("Han");
            han.setLastName("Solo");
            han.setNickname("Smuggler");
            han.setCreationDate(LocalDateTime.now());
            han.setIsConfirmed(LocalDate.now());
            han.setActive(true);
            han.setPwdHash(encryptHelper.encryptPassword("Password1!"));
            han.setLocation(labDao.findLabByLocation(LabEntity.Lab.Tomar));
            han.setRole(UserEntity.Role.User);
            han.setUserPhoto("http://localhost:8080/ProjectoFinalImages/8.jpg?t=" + System.currentTimeMillis());
            Set<SkillEntity> skills = new HashSet<>();
            skills.add(skillDao.findSkillByName("Piloting"));
            skills.add(skillDao.findSkillByName("Negotiation"));
            skills.add(skillDao.findSkillByName("Blaster Combat"));
            skills.add(skillDao.findSkillByName("Smuggling"));
            skills.add(skillDao.findSkillByName("Leadership"));
            skills.add(skillDao.findSkillByName("Wookiee Speak"));
            han.setSkills(skills);
            Set<InterestEntity> interests = new HashSet<>();
            interests.add(intererestDao.findInterestByName("Bounty Hunting"));
            interests.add(intererestDao.findInterestByName("The Empire"));
            interests.add(intererestDao.findInterestByName("Peace"));
            interests.add(intererestDao.findInterestByName("Droid Rights"));
            han.setInterests(interests);
            userDao.persist(han);
        }
        if(userDao.findUserByEmail("chewbacca@gmail.com") == null) {
            UserEntity chewie = new UserEntity();
            chewie.setEmail("chewbacca@gmail.com");
            chewie.setFirstName("Chewbacca");
            chewie.setLastName("daSilva");
            chewie.setNickname("Chewie");
            chewie.setCreationDate(LocalDateTime.now());
            chewie.setIsConfirmed(LocalDate.now());
            chewie.setActive(true);
            chewie.setPwdHash(encryptHelper.encryptPassword("Password1!"));
            chewie.setLocation(labDao.findLabByLocation(LabEntity.Lab.Coimbra));
            chewie.setRole(UserEntity.Role.User);
            chewie.setUserPhoto("http://localhost:8080/ProjectoFinalImages/9.jpg?t=" + System.currentTimeMillis());
            Set<SkillEntity> skills = new HashSet<>();
            skills.add(skillDao.findSkillByName("Smuggling"));
            skills.add(skillDao.findSkillByName("Blaster Combat"));
            skills.add(skillDao.findSkillByName("Piloting"));
            skills.add(skillDao.findSkillByName("Robotics"));
            skills.add(skillDao.findSkillByName("Soldering"));
            skills.add(skillDao.findSkillByName("Wookiee Speak"));
            Set<InterestEntity> interests = new HashSet<>();
            interests.add(intererestDao.findInterestByName("The Light Side"));
            interests.add(intererestDao.findInterestByName("Peace"));
            interests.add(intererestDao.findInterestByName("Rebellion"));
            interests.add(intererestDao.findInterestByName("Programming"));
            chewie.setInterests(interests);
            userDao.persist(chewie);
        }
        if(userDao.findUserByEmail("obiwan@gmail.com") == null) {
            UserEntity obiwan = new UserEntity();
            obiwan.setEmail("obiwan@gmail.com");
            obiwan.setFirstName("ObiWan");
            obiwan.setLastName("Kenobi");
            obiwan.setNickname("JediMaster");
            obiwan.setCreationDate(LocalDateTime.now());
            obiwan.setIsConfirmed(LocalDate.now());
            obiwan.setActive(true);
            obiwan.setPwdHash(encryptHelper.encryptPassword("Password1!"));
            obiwan.setLocation(labDao.findLabByLocation(LabEntity.Lab.Porto));
            obiwan.setRole(UserEntity.Role.User);
            obiwan.setUserPhoto("http://localhost:8080/ProjectoFinalImages/10.jpg?t=" + System.currentTimeMillis());
            Set<SkillEntity> skills = new HashSet<>();
            skills.add(skillDao.findSkillByName("Lightsaber Combat"));
            skills.add(skillDao.findSkillByName("Telekinesis"));
            skills.add(skillDao.findSkillByName("Piloting"));
            skills.add(skillDao.findSkillByName("Droid Repair"));
            skills.add(skillDao.findSkillByName("Tatooine Survival"));
            skills.add(skillDao.findSkillByName("Negotiation"));
            skills.add(skillDao.findSkillByName("The Force"));
            obiwan.setSkills(skills);
            Set<InterestEntity> interests = new HashSet<>();
            interests.add(intererestDao.findInterestByName("The Light Side"));
            interests.add(intererestDao.findInterestByName("Mandalore"));
            interests.add(intererestDao.findInterestByName("Peace"));
            obiwan.setInterests(interests);
            userDao.persist(obiwan);
        }
        if(userDao.findUserByEmail("palpatine@gmail.com") == null) {
            UserEntity palpatine = new UserEntity();
            palpatine.setEmail("palpatine@gmail.com");
            palpatine.setFirstName("Sheev");
            palpatine.setLastName("Palpatine");
            palpatine.setNickname("Emperor");
            palpatine.setCreationDate(LocalDateTime.now());
            palpatine.setIsConfirmed(LocalDate.now());
            palpatine.setActive(true);
            palpatine.setPwdHash(encryptHelper.encryptPassword("Password1!"));
            palpatine.setLocation(labDao.findLabByLocation(LabEntity.Lab.Lisboa));
            palpatine.setRole(UserEntity.Role.User);
            palpatine.setUserPhoto("http://localhost:8080/ProjectoFinalImages/11.jpg?t=" + System.currentTimeMillis());
            Set<SkillEntity> skills = new HashSet<>();
            skills.add(skillDao.findSkillByName("Lightsaber Combat"));
            skills.add(skillDao.findSkillByName("Telekinesis"));
            skills.add(skillDao.findSkillByName("Lightsaber Construction"));
            skills.add(skillDao.findSkillByName("Mind Reading"));
            skills.add(skillDao.findSkillByName("Tatooine Survival"));
            skills.add(skillDao.findSkillByName("Negotiation"));
            skills.add(skillDao.findSkillByName("Levitation"));
            palpatine.setSkills(skills);
            Set<InterestEntity> interests = new HashSet<>();
            interests.add(intererestDao.findInterestByName("War"));
            interests.add(intererestDao.findInterestByName("The Dark Side"));
            interests.add(intererestDao.findInterestByName("The Empire"));
            palpatine.setInterests(interests);
            userDao.persist(palpatine);
        }
        if(userDao.findUserByEmail("lando@gamil.com") == null) {
            UserEntity lando = new UserEntity();
            lando.setEmail("lando@gmail.com");
            lando.setFirstName("Lando");
            lando.setLastName("Calrissian");
            lando.setNickname("Administrator");
            lando.setCreationDate(LocalDateTime.now());
            lando.setIsConfirmed(LocalDate.now());
            lando.setActive(true);
            lando.setPwdHash(encryptHelper.encryptPassword("Password1!"));
            lando.setLocation(labDao.findLabByLocation(LabEntity.Lab.Coimbra));
            lando.setRole(UserEntity.Role.User);
            lando.setUserPhoto("http://localhost:8080/ProjectoFinalImages/12.jpg?t=" + System.currentTimeMillis());
            Set<SkillEntity> skills = new HashSet<>();
            skills.add(skillDao.findSkillByName("Piloting"));
            skills.add(skillDao.findSkillByName("Negotiation"));
            skills.add(skillDao.findSkillByName("Blaster Combat"));
            skills.add(skillDao.findSkillByName("Leadership"));
            skills.add(skillDao.findSkillByName("Smuggling"));
            skills.add(skillDao.findSkillByName("Wookiee Speak"));
            lando.setSkills(skills);
            Set<InterestEntity> interests = new HashSet<>();
            interests.add(intererestDao.findInterestByName("Bounty Hunting"));
            interests.add(intererestDao.findInterestByName("Human Rights"));
            interests.add(intererestDao.findInterestByName("Peace"));
            interests.add(intererestDao.findInterestByName("Droid Rights"));
            lando.setInterests(interests);
            userDao.persist(lando);

        }
        if(userDao.findUserByEmail("mando@gmail.com") == null) {
            UserEntity mando = new UserEntity();
            mando.setEmail("mando@gmail.com");
            mando.setFirstName("Din");
            mando.setLastName("Djarin");
            mando.setNickname("Mando");
            mando.setCreationDate(LocalDateTime.now());
            mando.setIsConfirmed(LocalDate.now());
            mando.setActive(true);
            mando.setPwdHash(encryptHelper.encryptPassword("Password1!"));
            mando.setLocation(labDao.findLabByLocation(LabEntity.Lab.Coimbra));
            mando.setRole(UserEntity.Role.User);
            mando.setUserPhoto("http://localhost:8080/ProjectoFinalImages/13.jpg?t=" + System.currentTimeMillis());
            Set<SkillEntity> skills = new HashSet<>();
            skills.add(skillDao.findSkillByName("Piloting"));
            skills.add(skillDao.findSkillByName("Negotiation"));
            skills.add(skillDao.findSkillByName("Blaster Combat"));
            skills.add(skillDao.findSkillByName("Leadership"));
            skills.add(skillDao.findSkillByName("Smuggling"));
            skills.add(skillDao.findSkillByName("Wookiee Speak"));
            mando.setSkills(skills);
            Set<InterestEntity> interests = new HashSet<>();
            interests.add(intererestDao.findInterestByName("Bounty Hunting"));
            interests.add(intererestDao.findInterestByName("Mandalore"));
            interests.add(intererestDao.findInterestByName("War"));
            mando.setInterests(interests);
            userDao.persist(mando);
        }
    }


    public void removeUser(String email) {
        logger.info("Removing user with email {}", email);
        UserEntity user = userDao.findUserByEmail(email);
        if (user != null) {
            logger.info("User found, removing user");
            userDao.remove(user);
        }
    }

    public List<UserDto> findAllUsers() {

        userDao.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (UserEntity user : userDao.findAll()) {
            if(user.getId() != 1) {
                userDtos.add(convertToDto(user));
            }
        }
        return userDtos;
    }
public List<UserEntity> getAllUsers() {

    return userDao.findAll();
}

    public MyDto convertToMyDto(UserEntity user, String token) {

        MyDto myDto = new MyDto();
        myDto.setFirstName(user.getFirstName());
        myDto.setLastName(user.getLastName());
        myDto.setNickname(user.getNickname());
        myDto.setImage(user.getUserPhoto());
        myDto.setToken(token);
        myDto.setId(user.getId());
        myDto.setRole(user.getRole().getValue());

        return myDto;
    }

    /**
     * Logs in the user
     *
     * @param email
     * @param password
     * @return
     */

    public MyDto login(String email, String password) {
        logger.info("Logging in user with email {}", email);
        UserEntity user = userDao.findUserByEmail(email);
        if (user != null && user.isActive() && encryptHelper.checkPassword(password, user.getPwdHash())) {
            logger.info("User found, creating token");
            String token = generateToken();
            tokenBean.createLoginToken(token,user);

            MyDto userDto = convertToMyDto(user,token);
            logger.info("User logged in successfully");
            return userDto;
        }
        logger.error("User not found or password incorrect");
        return null;
    }

    /**
     * Logs in the user and generates a token
     *
     * @param user
     * @return
     */
    public MyDto firstLogin(UserEntity user) {
        logger.info("Logging in user with email {}", user.getEmail());
        String token = generateToken();
        tokenBean.createLoginToken(token, user);
        logger.info("User logged in successfully");
        return convertToMyDto(user,token);
    }

    public boolean logout(String token) {
        logger.info("Logging out user with token {}", token);
        UserEntity user = tokenBean.findUserByToken(token);
        if (user != null) {
            logger.info("User found, removing token");
            tokenBean.removeToken(token);
            return true;
        }
        logger.error("User not found");
        return false;
    }

    /**
     * Logs out the user
     *
     * @param token
     */
    public void forcedLogout(TokenEntity token) {
        logger.info("Forced logout for user with token {}", token.getToken());
        tokenBean.removeToken(token.getToken());
    }


    public String generateToken() {
        return encryptHelper.generateToken();
    }

    /**
     * Checks if the password is valid
     *
     * @param password
     * @return
     */
    public boolean isPasswordValid(String password) {
        logger.info("Checking if password is valid");
        if (password.length() <= 8) {
            logger.error("Password is too short");
            return false;
        } else if (!password.matches(".*[a-z].*")) {
            logger.error("Password does not contain lowercase letters");
            return false;
        } else if (!password.matches(".*[A-Z].*")) {
            logger.error("Password does not contain uppercase letters");
            return false;
        } else if (!password.matches(".*[0-9].*")) {
            logger.error("Password does not contain numbers");
            return false;

        } else return password.matches(".*[!@#$%^&*].*");
    }

    public UserEntity findUserByToken(String token) {

        return tokenBean.findUserByToken(token);
    }

    public UserEntity findUserByEmail(String email) {

        return userDao.findUserByEmail(email);
    }

    /**
     * Registers a new user
     *
     * @param email
     * @param password
     * @return
     */
    public boolean register(String email, String password) {
        logger.info("Registering user with email {}", email);
        boolean registered = false;
        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setPwdHash(encryptHelper.encryptPassword(password));
        user.setCreationDate(LocalDateTime.now());
        user.setActive(true);
        userDao.persist(user);
        tokenBean.createRegisterToken(encryptHelper.generateToken(),user);

        if (emailBean.sendConfirmationEmail(user)) {
            logger.info("Email sent successfully");
            registered = true;
        }
        logger.info("User registered? {}", registered);
        return registered;
    }

    /**
     * Confirms the user
     *
     * @param auxToken
     * @param userConfirmation
     * @return
     */
    public UserEntity confirmUser(String auxToken, UserConfirmation userConfirmation) {
        logger.info("Confirming user with token {}", auxToken);
        UserEntity user = tokenBean.findUserByToken(auxToken);
        if (user != null) {
            logger.info("User found, confirming user");
            user.setUserPhoto(userConfirmation.getUserPhoto());
            user.setFirstName(userConfirmation.getFirstName());
            user.setLastName(userConfirmation.getLastName());
            user.setNickname(userConfirmation.getNickname());
            user.setBio(userConfirmation.getBio());
            user.setLocation(labDao.findLabByLocation(LabEntity.Lab.valueOf(userConfirmation.getLabLocation())));
            user.setRole(UserEntity.Role.User);
            user.setIsConfirmed(LocalDate.now());
            user.setPrivacy(false);
            tokenBean.removeToken(auxToken);
            userDao.merge(user);
            logger.info("User confirmed successfully");
            return user;
        }
        logger.error("User not found");
        return null;
    }

    /**
     * Checks if the email is valid
     *
     * @param email
     * @return
     */
    public boolean isEmailValid(String email) {
        logger.info("Checking if email is valid");
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }




    public UserDto convertToDto(UserEntity user) {

        UserDto userDto = new UserDto();
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setNickname(user.getNickname());
        userDto.setBio(user.getBio());
        userDto.setLabLocation(user.getLocation().getLocation().name());
        userDto.setUserPhoto(user.getUserPhoto());
        userDto.setRole(user.getRole());
        userDto.setUserId(user.getId());
        userDto.setPrivacy(user.getPrivacy());
        List<String> projects = new ArrayList<>();
        for (ProjectUserEntity projectUser : user.getProjectUsers()) {
            projects.add(projectUser.getProject().getName());
        }
        userDto.setProjects(projects);
        List<SkillDto> skills = new ArrayList<>();
        Set<SkillEntity> userSkills = user.getSkills();
        for (SkillEntity skill : userSkills) {
            skills.add(skillBean.toSkillDtos(skill));
        }
        userDto.setSkills(skills);
        List<InterestDto> interests = new ArrayList<>();
        Set<InterestEntity> userInterests = user.getInterests();
        for (InterestEntity interest : userInterests) {
            interests.add(interestBean.toInterestDto(interest));
        }
        userDto.setInterests(interests);

        return userDto;
    }

    public ProjectUserDto convertToProjectUserDto(ProjectUserEntity projectUser) {

        ProjectUserDto projectUserDto = new ProjectUserDto();
        projectUserDto.setFirstName(projectUser.getUser().getFirstName());
        projectUserDto.setLastName(projectUser.getUser().getLastName());
        projectUserDto.setNickname( projectUser.getUser().getNickname());
        projectUserDto.setUserPhoto(projectUser.getUser().getUserPhoto());
        projectUserDto.setProjectManager(projectUser.isProjectManager());
        projectUserDto.setUserId(projectUser.getUser().getId());
        projectUserDto.setApprovalStatus(projectUser.getApprovalStatus().name());

        return projectUserDto;
    }

    public UserEntity findUserById(int id) {

        return userDao.findUserById(id);
    }

    public UserDto findUserDtoById(int id) {

        UserEntity user = findUserById(id);


        return convertToDto(user);
    }
    public boolean setAdminStatus(String token, int id) {
        logger.info("Setting admin status for user with id {}", id);
        UserEntity user = findUserByToken(token);
        if (user == null) {
            logger.error("User not found");
            return false;
        }
        UserEntity user2 = findUserById(id);
        if (user2 == null) {
            logger.error("User not found");
            return false;
        }
        if ((user.getRole() == UserEntity.Role.Admin || user.getRole() == UserEntity.Role.Manager) && user2.getRole() == UserEntity.Role.User){
            user2.setRole(UserEntity.Role.Manager);
            userDao.merge(user2);
            logger.info("Admin status set successfully");
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setUserId(user2.getId());
            notificationDto.setOtherUserId(user.getId());
            notificationDto.setType("PROMOTED_ADMIN");
            notificationDto.setSeen(false);
            notificationDto.setTime(LocalDateTime.now());
            notificationDto.setRead(false);
            notificationDto.setProjectName("System");
            notificationBean.sendNotification(notificationDto);
            return true;
        }else if(user.getRole() == UserEntity.Role.Admin && user2.getRole() == UserEntity.Role.Manager){
            user2.setRole(UserEntity.Role.User);
            userDao.merge(user2);
            logger.info("Admin status removed successfully");
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setUserId(user2.getId());
            notificationDto.setOtherUserId(user.getId());
            notificationDto.setType("DEMOTED_ADMIN");
            notificationDto.setSeen(false);
            notificationDto.setTime(LocalDateTime.now());
            notificationDto.setRead(false);
            notificationDto.setProjectName("System");
            notificationBean.sendNotification(notificationDto);
            return true;
        }
        logger.error("User not allowed to set admin status");
        return false;
    }


    public boolean updateUser(int id, UserDto userDto) {
        logger.info("Updating user with id {}", id);
        UserEntity user = findUserById(id);
        if (user == null) {
            logger.error("User not found");
            return false;
        }
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setNickname(userDto.getNickname());
        user.setBio(userDto.getBio());
        user.setLocation(labDao.findLabByLocation(LabEntity.Lab.valueOf(userDto.getLabLocation())));
        user.setUserPhoto(userDto.getUserPhoto());
        userDao.merge(user);
        logger.info("User {} updated successfully", id);
        return true;
    }

    public void setLastActivity(String token) {
        logger.info("Setting last activity for user with token {}", token);
        TokenEntity tokenEntity = tokenBean.findTokenByToken(token);
        tokenEntity.setLastActivity(LocalDateTime.now());
        tokenBean.updateToken(tokenEntity);
    }

    public boolean addSkillToUser(String token, SkillEntity skill) {
        logger.info("Adding skill to user with token {}", token);
        UserEntity user = findUserByToken(token);
        if (user == null) {
            logger.error("User not found");
            return false;
        }
        user.getSkills().add(skill);
        userDao.merge(user);
        logger.info("Skill added to user successfully");
        return true;
    }
    public boolean removeSkillFromUser(String token, SkillEntity skill) {
        logger.info("Removing skill from user with token {}", token);
        UserEntity user = findUserByToken(token);
        if (user == null) {
            logger.error("User not found");
            return false;
        }
        SkillEntity skillEntity = skillDao.findSkillByName(skill.getName());
        skillEntity.getUsers().size(); // This line initializes the users collection
        user.getSkills().remove(skillEntity);
        skillEntity.getUsers().remove(user);
        userDao.merge(user); // Update the UserEntity in the database
        skillDao.merge(skillEntity); // Update the SkillEntity in the database
        logger.info("Skill removed from user successfully");
        return true;
    }
    public boolean addInterestToUser(String token, InterestEntity interest) {
        logger.info("Adding interest to user with token {}", token);
        UserEntity user = findUserByToken(token);
        if (user == null) {
            logger.error("User not found");
            return false;
        }
        user.getInterests().add(interest);
        userDao.merge(user);
        logger.info("Interest added to user successfully");
        return true;
    }
    public boolean removeInterestFromUser(String token, InterestEntity interest) {
        logger.info("Removing interest from user with token {}", token);
        UserEntity user = findUserByToken(token);
        if (user == null) {
            logger.error("User not found");
            return false;
        }
        InterestEntity interestEntity = interestBean.findInterestByName(interest.getName());
        interestEntity.getUsers().remove(user); // This line initializes the users collection
        user.getInterests().remove(interest);
        intererestDao.merge(interestEntity); // Update the InterestEntity in the database
        userDao.merge(user);
        logger.info("Interest {} removed from user successfully from user with token {}", interest.getName(), token);
        return true;
    }
    public boolean resetPassword(UserEntity user) {
        logger.info("Resetting password for user with email {}", user.getEmail());
        tokenBean.createPasswordToken(encryptHelper.generateToken(), user);
        user.setActive(false);
        userDao.merge(user);
        emailBean.sendPasswordResetEmail(user);
        logger.info("Password reset successfully");
        return true;
    }
    public boolean confirmPasswordReset(String auxToken, PasswordDto password) {
        logger.info("Confirming password reset for user with token {}", auxToken);
        UserEntity user = tokenBean.findUserByToken(auxToken);
        if (user == null) {
            logger.error("User not found");
            return false;
        }
        user.setPwdHash(encryptHelper.encryptPassword(password.getPassword()));
        tokenBean.removeToken(auxToken);
        user.setActive(true);
        userDao.merge(user);
        logger.info("Password reset confirmed successfully");
        return true;
    }
    public boolean setPrivate(String token){
        logger.info("Setting privacy for user with token {}", token);
        UserEntity user = findUserByToken(token);
        if(user == null){
            logger.error("User not found");
            return false;
        }
        user.setPrivacy(!user.getPrivacy());
        userDao.merge(user);
        logger.info("Privacy set successfully");
        return true;
    }
}
