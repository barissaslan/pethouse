package com.barisaslan.pethouse.domain.service;

import com.barisaslan.pethouse.TestHelper;
import com.barisaslan.pethouse.dao.entity.Pet;
import com.barisaslan.pethouse.dao.repository.PetRepository;
import com.barisaslan.pethouse.domain.model.PetType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @InjectMocks
    private PetServiceImpl petService;

    @Mock
    private PetRepository petRepository;

    @Test
    void findAllShouldReturnPets() {
        when(petRepository.findAll()).thenReturn(TestHelper.getDummyPetList());

        final List<Pet> petList = petService.findAll();

        verify(petRepository).findAll();
        assertEquals(2, petList.size());
        assertEquals("pet1", petList.get(0).getName());
    }

    @Test
    void findByIdShouldReturnPet() {
        when(petRepository.findById(anyLong())).thenReturn(Optional.of(TestHelper.getDummyPet()));

        final Optional<Pet> pet = petService.findById(1L);

        verify(petRepository).findById(anyLong());
        assertTrue(pet.isPresent());
        assertEquals("pet1", pet.get().getName());
    }

    @Test
    void addShouldReturnPet() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        petService.add("pet5", PetType.BIRD, new Date());

        ArgumentCaptor<Pet> petCaptor = ArgumentCaptor.forClass(Pet.class);
        verify(petRepository).save(petCaptor.capture());
        verifyNoMoreInteractions(petRepository);

        verify(petRepository).save(any(Pet.class));
        assertEquals("pet5", petCaptor.getValue().getName());
        assertEquals(PetType.BIRD, petCaptor.getValue().getPetType());
    }

    @Test
    void saveShouldReturnPet() {
        when(petRepository.save(any(Pet.class))).thenReturn(TestHelper.getDummyPet());

        final Pet pet = petService.save(TestHelper.getDummyPet());

        verify(petRepository).save(any(Pet.class));
        assertEquals("pet1", pet.getName());
    }

}
