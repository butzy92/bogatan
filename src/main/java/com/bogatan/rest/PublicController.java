package com.bogatan.rest;

import com.bogatan.dto.TowersByUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by mbutan on 4/14/2017.
 */
@RestController
@RequestMapping("api/public")
public class PublicController {

    @Autowired
    private TowersController towersController;

    @RequestMapping("/top_ten/towers")
    public ResponseEntity<List<TowersByUserDTO>> getTopTen(){
        /* TO BE CACHED IN THE FUTURE */
        return ResponseEntity.ok(towersController.getAllTowers(new PageRequest(0,10)).getBody().getContent());
    }

}
