package com.bogatan.rest;

import com.bogatan.dto.TowersByUserDTO;
import com.bogatan.dto.UsersDTO;
import com.bogatan.exception.UnauthenticatedUserException;
import com.bogatan.model.TowerAdd;
import com.bogatan.model.TowersByUser;
import com.bogatan.model.UserContext;
import com.bogatan.persistance.Towers;
import com.bogatan.repository.TowersRepository;
import com.bogatan.security.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("api/towers")
public class TowersController {

    @Autowired
    private TowersRepository towersRepository;


    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Towers> addTower(@RequestBody TowerAdd ta) throws UnauthenticatedUserException {
        UserContext uc = SecurityUtils.getAuthenticatedUserContext();
        return ResponseEntity.status(HttpStatus.CREATED).body(towersRepository.save(new Towers()
                .setUser(uc.getUser())
                .setValue(ta.getValue())
                .setAddDate(new Date())));

    }


    @RequestMapping()
    public ResponseEntity<TowersByUserDTO> getMyTowers() throws UnauthenticatedUserException {
        UserContext uc = SecurityUtils.getAuthenticatedUserContext();
        return ResponseEntity.ok(towersRepository.getTowersByUserDTO(uc.getId()));
    }

    @RequestMapping("/all")
    public ResponseEntity<Page<TowersByUserDTO>> getAllTowers(Pageable pageable){
        return ResponseEntity.ok(towersRepository.getTowersByUserDTOPageable(pageable));
    }
}
