package com.aslanbaris.pethouse.dao.repository;

import com.aslanbaris.pethouse.dao.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {

}
