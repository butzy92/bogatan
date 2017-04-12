package com.bogatan.persistance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Users implements Serializable {
    @Id
    private String id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String profilePicture;

    @Column(nullable = false)
    private Date addDate;

    public String getId() {
        return id;
    }

    public Users setId(String id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Users setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getName() {
        return name;
    }

    public Users setName(String name) {
        this.name = name;
        return this;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public Users setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    public Date getAddDate() {
        return addDate;
    }

    public Users setAddDate(Date addDate) {
        this.addDate = addDate;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Users)) return false;

        Users users = (Users) o;

        return getId().equals(users.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
