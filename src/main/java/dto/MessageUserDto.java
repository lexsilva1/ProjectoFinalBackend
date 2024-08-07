package dto;

import entities.UserEntity;
/**
 * The DTO class for the message user.
 */
public class MessageUserDto {
    private int id;
    private String firstName;
    private String lastName;
    private String image;

    public MessageUserDto() {
    }

    public MessageUserDto(UserEntity user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.image = user.getUserPhoto();
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
