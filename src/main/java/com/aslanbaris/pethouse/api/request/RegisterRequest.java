package com.aslanbaris.pethouse.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @Email
    @NotNull
    @Size(max = 50)
    private String email;

    @NotNull
    private String password;

}
