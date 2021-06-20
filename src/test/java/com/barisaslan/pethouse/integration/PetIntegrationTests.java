package com.barisaslan.pethouse.integration;

import com.barisaslan.pethouse.api.controller.PetController;
import com.barisaslan.pethouse.api.request.AddPetRequest;
import com.barisaslan.pethouse.dao.entity.Pet;
import com.barisaslan.pethouse.dao.repository.PetRepository;
import com.barisaslan.pethouse.domain.model.PetType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
import java.util.List;

import static com.barisaslan.pethouse.TestHelper.asJsonString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class PetIntegrationTests {

    private MockMvc mvc;

    @Autowired
    private PetController petController;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        this.mvc = MockMvcBuilders.standaloneSetup(petController).build();
        petRepository.deleteAll();
    }

    @Test
    void getAllPetsShouldReturnPets() throws Exception {
        Pet pet = new Pet();
        pet.setName("pet1");
        pet.setPetType(PetType.DOG);
        petRepository.save(pet);

        pet = new Pet();
        pet.setName("pet2");
        pet.setPetType(PetType.BIRD);
        petRepository.save(pet);

        MvcResult result = mvc.perform(get("/api/pets/")).andExpect(status().isOk()).andReturn();

        List<Pet> petList = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertTrue(petList.size() >= 2);
    }

    @Test
    void addPetShouldSavePet() throws Exception {
        AddPetRequest request = new AddPetRequest();
        request.setPetName("pet3");
        request.setPetType(PetType.CAT);
        request.setPetBirthDate(new Date());

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        MvcResult result = mvc.perform(post("/api/pets/")
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        Pet pet = objectMapper.readValue(result.getResponse().getContentAsString(), Pet.class);

        assertEquals("pet3", pet.getName());
        assertTrue(pet.getId() > 0);
    }

}
