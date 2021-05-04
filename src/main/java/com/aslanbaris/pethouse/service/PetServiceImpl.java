package com.aslanbaris.pethouse.service;

import com.aslanbaris.pethouse.entity.Pet;
import com.aslanbaris.pethouse.entity.User;
import com.aslanbaris.pethouse.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
    public Pet save(Pet pet) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        pet.setUser(user);
        return petRepository.save(pet);
    }

}
