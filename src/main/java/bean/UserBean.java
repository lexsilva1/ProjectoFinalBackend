package bean;

import dao.LabDao;
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
            return token;
        }
        return null;
    }
    public boolean logout(String token) {
        UserEntity user = userDao.findUserByToken(token);
        if(user != null) {
            user.setToken(null);
            return true;
        }
        return false;
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
    public void register(String email, String password) {
        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setPwdHash(encryptHelper.encryptPassword(password));
        user.setCreationDate(LocalDateTime.now());
        user.setAuxToken(encryptHelper.generateToken());
        user.setActive(true);
        userDao.persist(user);
        emailBean.sendConfirmationEmail(user);
    }
    public boolean isEmailValid(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }

}
