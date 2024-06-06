package bean;

import dao.LabDao;
import dao.ProjectUserDao;
import dto.MyDto;
import dto.ProjectUserDto;
import dto.UserConfirmation;
import dto.UserDto;
import entities.*;
import jakarta.ejb.Stateless;
import dao.UserDao;
import jakarta.inject.Inject;
import utilities.EncryptHelper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
            admin.setLocation(labDao.findLabByLocation(LabEntity.Lab.Coimbra));
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
    public List<UserDto> findAllUsers() {
        userDao.findAll();
        List <UserDto> userDtos = new ArrayList<>();
        for(UserEntity user : userDao.findAll()) {
            userDtos.add(convertToUserDto(user));
        }
        return userDtos;
    }
    public UserDto convertToUserDto(UserEntity user) {
        UserDto userDto = new UserDto();
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setNickname(user.getNickname());
        userDto.setBio(user.getBio());
        userDto.setLabLocation(user.getLocation().getLocation().name());
        userDto.setUserPhoto(user.getUserPhoto());
        userDto.setRole(user.getRole());
        userDto.setUserId(user.getId());
        return userDto;
    }
    public MyDto convertToMyDto(UserEntity user) {
        MyDto myDto = new MyDto();
        myDto.setFirstName(user.getFirstName());
        myDto.setLastName(user.getLastName());
        myDto.setNickname(user.getNickname());
        myDto.setImage(user.getUserPhoto());
        myDto.setToken(user.getToken());
        myDto.setId(user.getId());
        return myDto;
    }
    /**
     * Logs in the user
     * @param email
     * @param password
     * @return
     */

    public MyDto login(String email, String password) {
        UserEntity user = userDao.findUserByEmail(email);
        if(user != null && user.isActive() && encryptHelper.checkPassword(password, user.getPwdHash())) {
            String token = generateToken();
            user.setToken(token);
            setLastActivity(user);
            MyDto userDto = convertToMyDto(user);
            return userDto;
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
        LocalDateTime time=LocalDateTime.now().minusMinutes(30);
        return userDao.findTimedOutUsers(time);
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
        userDto.setPrivate(user.isPrivate());
        List <String> projects = new ArrayList<>();
        for(ProjectUserEntity projectUser : user.getProjectUsers()) {
            projects.add(projectUser.getProject().getName());
        }
        userDto.setProjects(projects);
        List <String> skills = new ArrayList<>();
        Set<SkillEntity> userSkills = user.getSkills();
        for(SkillEntity skill : userSkills) {
            skills.add(skill.getName());
        }
        userDto.setSkills(skills);
        List <String> interests = new ArrayList<>();
        Set<InterestEntity> userInterests = user.getInterests();
        for(InterestEntity interest : userInterests) {
            interests.add(interest.getName());
        }
        userDto.setInterests(interests);
        return userDto;
    }
    public ProjectUserDto convertToProjectUserDto(UserEntity user) {
        ProjectUserEntity projectUser = projectUserDao.findProjectUserByUser(user);
        if(projectUser == null) {
            return null;
        }
        ProjectUserDto projectUserDto = new ProjectUserDto();
        projectUserDto.setFirstName(user.getFirstName());
        projectUserDto.setLastName(user.getLastName());
        projectUserDto.setNickname(user.getNickname());
        projectUserDto.setUserPhoto(user.getUserPhoto());
        projectUserDto.setProjectManager(projectUser.isProjectManager());
        projectUserDto.setUserId(user.getId());
        return projectUserDto;
    }
    public UserEntity findUserById(int id) {
        return userDao.findUserById(id);
    }
    public UserDto findUserDtoById(int id) {
        UserEntity user = findUserById(id);
        return convertToDto(user);
    }
    public UserEntity findUserByAuxToken(String auxToken) {
        return userDao.findUserByAuxToken(auxToken);
    }
    public boolean updateUser(int id, UserDto userDto) {
        UserEntity user = findUserById(id);
        if(user == null) {
            return false;
        }
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setNickname(userDto.getNickname());
        user.setBio(userDto.getBio());
        user.setLocation(labDao.findLabByLocation(LabEntity.Lab.valueOf(userDto.getLabLocation())));
        user.setUserPhoto(userDto.getUserPhoto());
        user.setBio(userDto.getBio());
        userDao.merge(user);
        return true;
    }
}
