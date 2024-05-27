package bean;

import dao.LabDao;
import dto.UserConfirmation;
import entities.LabEntity;
import entities.UserEntity;
import jakarta.ejb.Stateless;
import dao.UserDao;
import jakarta.inject.Inject;
import utilities.EncryptHelper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    public UserBean() {
    }
    /**
     * Creates a default admin user if it doesn't exist
     */
    public void createDefaultUsers() {
        if(userDao.findUserByEmail("admin@admin.com") == null) {
            UserEntity admin = new UserEntity();
            admin.setFirstName("admin");
            admin.setLastName("admin");
            admin.setEmail("admin@admin.com");
            admin.setPwdHash(encryptHelper.encryptPassword("admin"));
            admin.setLocation(labDao.findLabByLocation(LabEntity.Lab.COIMBRA));
            admin.setCreationDate(LocalDateTime.now());
            admin.setIsConfirmed(LocalDate.now());
            admin.setActive(true);
            admin.setRole(UserEntity.Role.Admin);
            userDao.persist(admin);
        }

    }

    public void removeUser(String email) {
        UserEntity user = userDao.findUserByEmail(email);
        if(user != null) {
            userDao.remove(user);
        }
    }
    public List<UserEntity> findAllUsers() {
        return userDao.findAll();
    }
    public String login(String email, String password) {
        UserEntity user = userDao.findUserByEmail(email);
        if(user != null && user.isActive() && encryptHelper.checkPassword(password, user.getPwdHash())) {
            String token = generateToken();
            user.setToken(token);
            setLastActivity(user);
            return token;
        }
        return null;
    }/**
     * Logs in the user and generates a token
     * @param user
     * @return
     */
    public String firstLogin(UserEntity user){
        String token = generateToken();
        user.setToken(token);
        userDao.merge(user);
        return token;
    }
    public boolean logout(String token) {
        UserEntity user = userDao.findUserByToken(token);
        if(user != null) {
            user.setToken(null);
            return true;
        }
        return false;
    }
    /**
     * Logs out the user
     * @param user
     */
    public void forcedLogout(UserEntity user) {
            user.setToken(null);
            user.setLastActivity(null);
            userDao.merge(user);
        }


    public String generateToken() {
        return encryptHelper.generateToken();
    }
    public UserEntity getUserByToken(String token) {
        return userDao.findUserByToken(token);
    }
    public UserEntity getUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }
    /**
     * Checks if the password is valid
     * @param password
     * @return
     */
    public boolean isPasswordValid(String password) {
        if(password.length() <= 8){
        return false;
        } else if (!password.matches(".*[a-z].*")){
        return false;
        } else if (!password.matches(".*[A-Z].*")){
        return false;
        } else if (!password.matches(".*[0-9].*")){
        return false;
        } else return password.matches(".*[!@#$%^&*].*");
    }
    public UserEntity findUserByToken(String token) {
        return userDao.findUserByToken(token);
    }
    public UserEntity findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }
    /**
     * Registers a new user
     * @param email
     * @param password
     * @return
     */
    public boolean register(String email, String password) {
        boolean registered = false;
        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setPwdHash(encryptHelper.encryptPassword(password));
        user.setCreationDate(LocalDateTime.now());
        user.setAuxToken(encryptHelper.generateToken());
        user.setActive(true);
        if (emailBean.sendConfirmationEmail(user)) {
            userDao.persist(user);
            registered = true;
        }
        return registered;
    }
    /**
     * Confirms the user
     * @param auxToken
     * @param userConfirmation
     * @return
     */
    public UserEntity confirmUser(String auxToken, UserConfirmation userConfirmation) {
        UserEntity user = userDao.findUserByAuxToken(auxToken);
        if(user != null) {
            user.setUserPhoto(userConfirmation.getUserPhoto());
            user.setFirstName(userConfirmation.getFirstName());
            user.setLastName(userConfirmation.getLastName());
            user.setNickname(userConfirmation.getNickname());
            user.setBio(userConfirmation.getBio());
            user.setLocation(labDao.findLabByLocation(LabEntity.Lab.valueOf(userConfirmation.getLabLocation())));
            user.setRole(UserEntity.Role.User);
            user.setIsConfirmed(LocalDate.now());
            user.setAuxToken(null);
            userDao.merge(user);
            return user;
        }
        return null;
    }
    /**
     * Checks if the email is valid
     * @param email
     * @return
     */
    public boolean isEmailValid(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }
    /**
     * Sets the last activity of the user
     * @param user
     */
    public void setLastActivity(UserEntity user) {
        user.setLastActivity(LocalDateTime.now());
        userDao.merge(user);
    }
    public List<UserEntity> findTimedOutUsers() {
        LocalDateTime time=LocalDateTime.now().minusMinutes(5);
        return userDao.findTimedOutUsers(time);
    }

}
