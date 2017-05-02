package com.bogatan.dto;

import java.util.Date;

/**
 * Created by mbutan on 4/13/2017.
 */
public class UsersDTO {

    private String id;
    private String email;
    private String name;
    private String profilePicture;
    private Date addDate;

    public String getId() {
        return id;
    }

    public UsersDTO setId(String id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UsersDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getName() {
        return name;
    }

    public UsersDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public UsersDTO setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    public Date getAddDate() {
        return addDate;
    }

    public UsersDTO setAddDate(Date addDate) {
        this.addDate = addDate;
        return this;
    }
}
