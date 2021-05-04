package com.aslanbaris.pethouse.service;

import com.aslanbaris.pethouse.entity.Pet;

import java.util.List;
import java.util.Optional;

public interface PetService {

    List<Pet> findAll();

    Optional<Pet> findById(Long id);

    Pet save(Pet pet);

}
