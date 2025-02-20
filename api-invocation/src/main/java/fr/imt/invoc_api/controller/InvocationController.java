package fr.imt.invoc_api.controller;

import fr.imt.invoc_api.model.Invocation;
import fr.imt.invoc_api.service.InvocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/invocations")
public class InvocationController {

    @Autowired
    InvocationService invocationService;

    @GetMapping("/random")
    public Invocation getInvocation() {
        return invocationService.getRandomInvocation();
    }

    @GetMapping
    public List<Invocation> getInvocations() {
        return invocationService.getInvocations();
    }

    @PostMapping("/batch")
    public List<Invocation> createAllInvocations(@RequestBody List<Invocation> invocations) {
        return invocationService.createAllInvocations(invocations);
    }

    @DeleteMapping("/")
    public ResponseEntity<Void> deleteAllInvocations() {
        invocationService.deleteAllInvocations();
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
