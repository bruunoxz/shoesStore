package com.brunohenrique.store.controller;

import com.brunohenrique.store.domain.Shoe;
import com.brunohenrique.store.dtos.RequestShoe;
import com.brunohenrique.store.service.ShoeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/shoes")
public class ShoeController {
    @Autowired
    ShoeService shoeService;

    @GetMapping
    public ResponseEntity<List<Shoe>> getAllShoes(){return ResponseEntity.ok(shoeService.listAllShoes());}

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Shoe>> getShoeById(@PathVariable String id){return ResponseEntity.ok(shoeService.listShoeById(id));}

    @PostMapping
    public ResponseEntity registerShoe(@RequestBody @Valid RequestShoe data){
        shoeService.createShoe(data);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Shoe> updateShoe(@PathVariable String id, @RequestBody @Valid RequestShoe data){return ResponseEntity.ok(shoeService.update(data));}

    @DeleteMapping("/{id}")
    public ResponseEntity deleteShoe(@PathVariable String id){
        shoeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}