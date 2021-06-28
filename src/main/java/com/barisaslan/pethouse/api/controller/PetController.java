package com.barisaslan.pethouse.api.controller;

import com.barisaslan.pethouse.api.request.AddPetRequest;
import com.barisaslan.pethouse.dao.entity.Pet;
import com.barisaslan.pethouse.domain.service.PetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pets")
public class PetController {

    private final PetService petService;

    @GetMapping(value = "/")
    public ResponseEntity<List<Pet>> getAllPets() {
        log.info("Log Test #41");
        List<Pet> petList = petService.findAll();
        return new ResponseEntity<>(petList, HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<Pet> addPet(@RequestBody @Valid AddPetRequest request) {
        Pet pet = petService.add(request.getPetName(), request.getPetType(), request.getPetBirthDate());
        return new ResponseEntity<>(pet, HttpStatus.CREATED);
    }

}
