package com.example.demo.dto;



import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDto {
    private Long id;

    @NotEmpty(message = "Nazwa nie może być pusta")
    private String name;
    @NotEmpty(message = "Hasło nie może być puste")
    private String password;

}