package bean;

import dao.UserDao;
import entities.UserEntity;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import utilities.EncryptHelper;

@Singleton
public class UserBean {
    public UserBean() {
    }

    @Inject
    private UserDao userDao;
    @Inject
    private EncryptHelper encryptHelper;

    public void logout(String token) {
        UserEntity user = userDao.findUserByToken(token);
        if (user != null) {
            user.setToken(null);
            userDao.updateToken(user);
        }
    }

    public void forcedLogout(String token) {
        UserEntity user = userDao.findUserByToken(token);
        if (user != null) {
            user.setToken(null);
            userDao.updateToken(user);
        }
    }

    public String login(String email, String password) {
        UserEntity user = userDao.findUserByEmail(email);
        String password1 = encryptHelper.encryptPassword(password);
        if (user != null && user.getPwdHash().equals(password1)) {
            String token = generateToken();
            user.setToken(token);
            userDao.updateToken(user);
            return token;
        }
        return null;
    }

    public String generateToken() {
        return java.util.UUID.randomUUID().toString();
    }

    public boolean isPasswordValid(String password) {
        // Check if password is at least 8 characters long
        if (password.length() < 8) {
            return false;
        }
        // Check if password contains at least one digit
        if (!password.matches(".*\\d.*")) {
            return false;
        }
        // Check if password contains at least one lowercase letter
        if (!password.matches(".*[a-z].*")) {
            return false;
        }
        // Check if password contains at least one uppercase letter
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }
        // Check if password contains at least one special character
        if (!password.matches(".*[!@#$%^&*].*")) {
            return false;
        }
        // If all conditions are met, return true
        return true;
    }
    public boolean isEmailValid(String email) {
        // Check if email is valid
        if (!email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
            return false;
        }
        // If email is valid, return true
        return true;
    }
    public boolean isEmailAvailable(String email) {
        // Check if email is available
        if (userDao.findUserByEmail(email) != null) {
            return false;
        }
        // If email is available, return true
        return true;
    }
    public boolean isNicknameAvailable(String nickname) {
        // Check if nickname is available
        if (userDao.findUserByNickname(nickname) != null) {
            return false;
        }
        // If nickname is available, return true
        return true;
    }
}
