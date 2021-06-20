package com.barisaslan.pethouse.domain.service;

import com.barisaslan.pethouse.dao.entity.Pet;
import com.barisaslan.pethouse.dao.entity.User;
import com.barisaslan.pethouse.dao.repository.PetRepository;
import com.barisaslan.pethouse.domain.model.PetType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    @Override
    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    @Override
    public Optional<Pet> findById(Long id) {
        return petRepository.findById(id);
    }

    @Override
    public Pet add(String name, PetType type, Date birthDate) {
        Pet pet = new Pet();
        pet.setName(name);
        pet.setPetType(type);
        pet.setBirthDate(birthDate);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        pet.setUser(user);

        return petRepository.save(pet);
    }

    @Override
    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }

}
