package entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
/**
 * The entity class for the Users table.
 */
@Entity
@Table(name="Users")
@NamedQuery(name = "User.findUserByEmail", query = "SELECT u FROM UserEntity u WHERE u.email = :email")
@NamedQuery(name = "User.findActiveTokens", query = "SELECT t FROM TokenEntity t WHERE t.user = :user AND t.isUsed = false")
@NamedQuery(name = "User.findUserByNickname", query = "SELECT u FROM UserEntity u WHERE u.nickname = :nickname")
@NamedQuery(name = "User.findUserById", query = "SELECT u FROM UserEntity u WHERE u.id = :id")
@NamedQuery(name = "User.findUserByToken", query = "SELECT u FROM UserEntity u JOIN u.tokens t WHERE t.token = :token")
public class UserEntity implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="id", nullable = false, unique = true, updatable = false)
    int id;
    @Column (name="nickname", nullable = true , unique = true)
    String nickname;
    @Column (name="firstName", nullable = true, unique = false)
    String firstName;
    @Column (name="lastName", nullable = true, unique = false)
    String lastName;
    @Column (name="email", nullable = false, unique = true)
    String email;
    @Column (name="password", nullable = false, unique = false)
    String pwdHash;
    @Column (name="contactNumber", nullable = true, unique = false)
    String contactNumber;
    @Column (name="userPhoto", nullable = true, unique = false)
    String userPhoto;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TokenEntity> tokens;

    @Enumerated(EnumType.ORDINAL)
    @Column (name="role", nullable = true, unique = false)
    Role role;
    @Column (name="isActive", nullable = false, unique = false)
    boolean isActive;
    @Column (name="isConfirmed", nullable = true, unique = false)
    LocalDate isConfirmed;

    @Column (name="creationDate", nullable = false, unique = false)
    LocalDateTime creationDate;
    @Column (name="bio", nullable = true, unique = false)
    String bio;
    @Column (name="Privacy", nullable = false, unique = false)
    boolean Privacy;
    @ManyToOne
    @JoinColumn(name = "location_id")
    private LabEntity location;
    @ManyToMany

    @JoinTable(
            name = "user_interests",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "interest_id"))
    private Set<InterestEntity> interests;

    @ManyToMany
    @JoinTable(
            name = "user_skills",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private Set<SkillEntity> skills;
    @OneToMany(mappedBy = "user")
    private Set<ProjectUserEntity> projectUsers;


    public enum Role {
        Admin(0),
        Manager(1),
        User(10),
        Unconfirmed(100);

        private final int value;

        Role(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    @PrePersist
    @PreUpdate
    private void validate() {
        if (isConfirmed != null) {
            if (firstName == null || lastName == null || location == null) {
                throw new IllegalStateException("First name, last name, and location cannot be null when isConfirmed is not null");
            }
        }
    }

    // Inner class for entity listener
    public static class UserEntityListener {
        @PrePersist
        @PreUpdate
        public void validateUser(UserEntity user) {
            user.validate();
        }
    }
    public int getId() {
        return id;
    }

    public void setId(int username) {
        this.id = username;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwdHash() {
        return pwdHash;
    }

    public void setPwdHash(String password) {
        this.pwdHash = password;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        this.isActive = active;
    }

    public LocalDate getIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(LocalDate confirmed) {
        this.isConfirmed = confirmed;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public LabEntity getLocation() {
        return location;
    }

    public void setLocation(LabEntity location) {
        this.location = location;
    }

    public boolean getPrivacy() {
        return Privacy;
    }

    public void setPrivacy(boolean aPrivate) {
        Privacy = aPrivate;
    }

    public Set<InterestEntity> getInterests() {
        return interests;
    }

    public void setInterests(Set<InterestEntity> interests) {
        this.interests = interests;
    }

    public Set<SkillEntity> getSkills() {
        return skills;
    }

    public void setSkills(Set<SkillEntity> skills) {
        this.skills = skills;
    }

    public Set<ProjectUserEntity> getProjectUsers() {
        return projectUsers;
    }

    public void setProjectUsers(Set<ProjectUserEntity> projectUsers) {
        this.projectUsers = projectUsers;
    }

    public List<TokenEntity> getTokens() {
        return tokens;
    }

    public void setTokens(TokenEntity token) {
        this.tokens.add(token);
    }

    public boolean isPrivacy() {
        return Privacy;
    }
}

