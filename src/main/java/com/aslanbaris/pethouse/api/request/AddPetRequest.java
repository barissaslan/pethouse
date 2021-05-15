package com.aslanbaris.pethouse.api.request;

import com.aslanbaris.pethouse.domain.model.PetType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class AddPetRequest {

    @NotNull
    private String petName;

    @NotNull
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date petBirthDate;

    @NotNull
    private PetType petType;

}
