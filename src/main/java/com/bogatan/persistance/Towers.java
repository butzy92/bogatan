package com.bogatan.persistance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by mbutan on 4/12/2017.
 */
@Entity
public class Towers implements Serializable{

    @Id
    @GenericGenerator(
            name = "sequence",
            strategy = "sequence",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "sequence",
                            value = "sequence"
                    )

            })
    @GeneratedValue(generator = "sequence")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(nullable = false)
    private Integer value;

    @Column(nullable = false)
    private Date addDate;

    public long getId() {
        return id;
    }

    public Towers setId(long id) {
        this.id = id;
        return this;
    }

    public Users getUser() {
        return user;
    }

    public Towers setUser(Users user) {
        this.user = user;
        return this;
    }

    public Integer getValue() {
        return value;
    }

    public Towers setValue(Integer value) {
        this.value = value;
        return this;
    }

    public Date getAddDate() {
        return addDate;
    }

    public Towers setAddDate(Date addDate) {
        this.addDate = addDate;
        return this;
    }

    @Override
    public String toString() {
        return "Towers{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", value=" + value +
                ", addDate=" + addDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Towers)) return false;

        Towers towers = (Towers) o;

        return getId() == towers.getId();
    }

    @Override
    public int hashCode() {
        return (int) (getId() ^ (getId() >>> 32));
    }
}
