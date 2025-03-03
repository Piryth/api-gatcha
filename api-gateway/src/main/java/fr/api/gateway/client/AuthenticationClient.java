package fr.api.gateway.client;

import fr.api.gateway.domain.AuthenticationRequest;
import fr.api.gateway.domain.AuthenticationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "api-authentication")
public interface AuthenticationClient {

    @RequestMapping(value = "token", method = RequestMethod.POST)
    ResponseEntity<AuthenticationResponse> getToken(@RequestBody AuthenticationRequest authenticationRequest
    );

    @RequestMapping(value = "validate", method = RequestMethod.GET)
    ResponseEntity<AuthenticationResponse> validateToken();

}
