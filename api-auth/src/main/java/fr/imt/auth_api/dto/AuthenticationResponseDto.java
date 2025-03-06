package fr.imt.auth_api.dto;

import lombok.*;

@Builder
@Value
public class AuthenticationResponseDto {

    String token;

}