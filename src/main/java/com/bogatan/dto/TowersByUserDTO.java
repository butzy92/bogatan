package com.bogatan.dto;

import com.bogatan.persistance.Users;

/**
 * Created by mbutan on 4/13/2017.
 */
public class TowersByUserDTO {
    private UsersDTO user;
    private long towers;

    public UsersDTO getUser() {
        return user;
    }

    public TowersByUserDTO setUser(UsersDTO user) {
        this.user = user;
        return this;
    }

    public long getTowers() {
        return towers;
    }

    public TowersByUserDTO setTowers(long towers) {
        this.towers = towers;
        return this;
    }
}
