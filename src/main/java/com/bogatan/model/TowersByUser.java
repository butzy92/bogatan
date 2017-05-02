package com.bogatan.model;

import com.bogatan.persistance.Users;

import java.io.Serializable;

/**
 * Created by mbutan on 4/13/2017.
 */

public class TowersByUser implements Serializable {

    private Users user;
    private long towers;

    public Users getUser() {
        return user;
    }

    public TowersByUser setUser(Users user) {
        this.user = user;
        return this;
    }

    public long getTowers() {
        return towers;
    }

    public TowersByUser setTowers(long towers) {
        this.towers = towers;
        return this;
    }

    public TowersByUser(Users user, long towers) {
        this.user = user;
        this.towers = towers;
    }

    @Override
    public String toString() {
        return "TowersByUser{" +
                "user=" + user +
                ", towers=" + towers +
                '}';
    }
}
