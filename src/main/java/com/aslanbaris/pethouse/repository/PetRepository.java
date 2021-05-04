package com.aslanbaris.pethouse.repository;

import com.aslanbaris.pethouse.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {

}
