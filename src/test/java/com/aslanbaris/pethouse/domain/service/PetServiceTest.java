package com.aslanbaris.pethouse.domain.service;

import com.aslanbaris.pethouse.dao.entity.Pet;
import com.aslanbaris.pethouse.dao.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static com.aslanbaris.pethouse.TestHelper.getDummyPet;
import static com.aslanbaris.pethouse.TestHelper.getDummyPetList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    private PetService petService;

    @BeforeEach
    void setup() {
        petService = new PetServiceImpl(petRepository);
    }

    @Test
    void findAllShouldReturnPets() {
        when(petRepository.findAll()).thenReturn(getDummyPetList());

        final List<Pet> petList = petService.findAll();

        verify(petRepository).findAll();
        assertEquals(2, petList.size());
        assertEquals("pet1", petList.get(0).getName());
    }

    @Test
    void findByIdShouldReturnPet() {
        when(petRepository.findById(anyLong())).thenReturn(Optional.of(getDummyPet()));

        final Optional<Pet> pet = petService.findById(1L);

        verify(petRepository).findById(anyLong());
        assertTrue(pet.isPresent());
        assertEquals("pet1", pet.get().getName());
    }

    @Test
    void saveShouldReturnPet() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(petRepository.save(any(Pet.class))).thenReturn(getDummyPet());

        final Pet pet = petService.save(getDummyPet());

        verify(petRepository).save(any(Pet.class));
        assertEquals("pet1", pet.getName());
    }

}
