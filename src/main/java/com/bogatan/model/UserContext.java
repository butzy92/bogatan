package com.bogatan.model;

import com.bogatan.persistance.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by mbutan on 4/10/2017.
 */
public class UserContext implements UserDetails {

    private String id;

    private String email;

    private String name;

    private String profilePicture;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return null;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return id;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    public String getId() {
        return id;
    }

    public UserContext setId(String id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserContext setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserContext setName(String name) {
        this.name = name;
        return this;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public UserContext setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    @JsonIgnore
    public boolean isTheSameUser(Users user) {
        if (!user.getEmail().equals(this.email)) {
            return false;
        }
        if (!user.getName().equals(this.name)) {
            return false;
        }
        if (!user.getProfilePicture().equals(this.profilePicture)) {
            return false;
        }
        return true;
    }


    @JsonIgnore
    public Users getUser() {
        return new Users()
                .setId(this.getId())
                .setEmail(this.getEmail())
                .setName(this.getName())
                .setProfilePicture(this.getProfilePicture());
    }

    @Override
    public String toString() {
        return "UserContext{" +
                "username='" + id + '\'' +
                '}';
    }
}
