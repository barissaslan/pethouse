package com.aslanbaris.pethouse.api.controller;

import com.aslanbaris.pethouse.api.request.AddPetRequest;
import com.aslanbaris.pethouse.dao.entity.Pet;
import com.aslanbaris.pethouse.domain.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pets")
public class PetController {

    private final PetService petService;

    @GetMapping(value = "/")
    public ResponseEntity<List<Pet>> getAllPets() {
        List<Pet> petList = petService.findAll();
        return new ResponseEntity<>(petList, HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<Pet> addPet(@RequestBody @Valid AddPetRequest request) {
        Pet pet = petService.add(request.getPetName(), request.getPetType(), request.getPetBirthDate());
        return new ResponseEntity<>(pet, HttpStatus.CREATED);
    }

}
