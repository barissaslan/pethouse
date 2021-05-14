package com.aslanbaris.pethouse.api.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegisterRequest {

    @Email
    @NotNull
    @Size(max = 50)
    private String email;

    @NotNull
    private String password;

}
