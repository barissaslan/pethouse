package com.barisaslan.pethouse.api.controller;

import com.barisaslan.pethouse.dao.entity.Pet;
import com.barisaslan.pethouse.domain.service.PetService;
import com.barisaslan.pethouse.TestHelper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

    private final String PET_CONTROLLER_URL = "/api/pets/";

    @InjectMocks
    private PetController petController;

    @Mock
    private PetService petService;

    private ObjectMapper objectMapper;

    private MockMvc mvc;

    @BeforeEach
    void setup() {
        this.mvc = MockMvcBuilders.standaloneSetup(petController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllPetsShouldReturnSuccess() throws Exception {
        when(petService.findAll()).thenReturn(TestHelper.getDummyPetList());

        MvcResult result = mvc.perform(get(PET_CONTROLLER_URL)).andExpect(status().isOk()).andReturn();

        final List<Pet> petList = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        assertEquals(2, petList.size());
        assertEquals("pet1", petList.get(0).getName());

        verify(petService).findAll();
        verifyNoMoreInteractions(petService);
    }

    @Test
    void addPetShouldReturnSuccess() throws Exception {
        when(petService.add(anyString(), any(), any())).thenReturn(TestHelper.getDummyPet());

        MvcResult result = mvc.perform(post(PET_CONTROLLER_URL)
                .content(TestHelper.asJsonString(TestHelper.getDummyAddPetRequest()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        final Pet pet = objectMapper.readValue(result.getResponse().getContentAsString(), Pet.class);

        assertEquals("pet1", pet.getName());

        verify(petService).add(anyString(), any(), any());
        verifyNoMoreInteractions(petService);
    }

}
