package com.barisaslan.pethouse.domain.service;

import com.barisaslan.pethouse.dao.entity.Pet;
import com.barisaslan.pethouse.domain.model.PetType;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PetService {

    List<Pet> findAll();

    Optional<Pet> findById(Long id);

    Pet add(String name, PetType type, Date birthDate);

    Pet save(Pet pet);

}
