package com.bogatan.repository;

import com.bogatan.dto.TowersByUserDTO;
import com.bogatan.dto.UsersDTO;
import com.bogatan.model.TowersByUser;
import com.bogatan.persistance.Towers;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Transactional
public interface TowersRepository extends JpaRepository<Towers, BigDecimal> {

    @Query("select new com.bogatan.model.TowersByUser(towers.user, sum(towers.value)) from " +
            " Towers towers " +
            " where towers.user.id = :userId ")
    TowersByUser getTowersByUser(@Param("userId") String userId);


    @Query("select new com.bogatan.model.TowersByUser(towers.user, sum(towers.value)) from " +
            " Towers towers " +
            "GROUP BY  towers.user " +
            "order by sum(towers.value) desc")
    Page<TowersByUser> getTowersPageable(Pageable pageable);


    default Page<TowersByUserDTO> getTowersByUserDTOPageable(Pageable pageable) {
        return getTowersPageable(pageable)
                .map(this::fromTowersByUserToDTO);
    }

    default TowersByUserDTO getTowersByUserDTO(String id) {
        return fromTowersByUserToDTO(getTowersByUser(id));
    }

    default TowersByUserDTO fromTowersByUserToDTO(TowersByUser towersByUser){
        UsersDTO usersDTO = new UsersDTO();
        BeanUtils.copyProperties(towersByUser.getUser(), usersDTO);
        return new TowersByUserDTO()
                .setUser(usersDTO)
                .setTowers(towersByUser.getTowers());
    }


}
