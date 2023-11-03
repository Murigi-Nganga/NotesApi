package com.example.notesapi.domain;

/**
 * DTO for {@link User}
 */
public class UserResponseDTO {
    private final Long id;
    private final String firstName;
    private final String secondName;
    private final String email;
    private final Long phoneNumber;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.secondName = user.getSecondName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getEmail() {
        return email;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

}