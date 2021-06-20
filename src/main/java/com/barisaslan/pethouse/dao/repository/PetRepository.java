package com.barisaslan.pethouse.dao.repository;

import com.barisaslan.pethouse.dao.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {

}
