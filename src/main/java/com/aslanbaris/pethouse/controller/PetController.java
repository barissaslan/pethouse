package com.aslanbaris.pethouse.controller;

import com.aslanbaris.pethouse.entity.Pet;
import com.aslanbaris.pethouse.model.MailRequest;
import com.aslanbaris.pethouse.service.MailService;
import com.aslanbaris.pethouse.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pets")
public class PetController {

    private final PetService petService;
    private final MailService mailService;

    @GetMapping(value = "/")
    public ResponseEntity<List<Pet>> getAllPets() {
        List<Pet> petList = petService.findAll();
        return new ResponseEntity<>(petList, HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<Pet> addPet(@RequestBody Pet pet) {
        pet = petService.save(pet);
        return new ResponseEntity<>(pet, HttpStatus.OK);
    }

}
