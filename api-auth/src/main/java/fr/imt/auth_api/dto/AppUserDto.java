package fr.imt.auth_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record AppUserDto(@Size(min = 8) String username,
                         @Email String email,
                         @Size(min = 8) String password) {}