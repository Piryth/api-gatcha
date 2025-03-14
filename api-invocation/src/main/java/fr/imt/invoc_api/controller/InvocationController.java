package fr.imt.invoc_api.controller;

import fr.imt.invoc_api.model.Invocation;
import fr.imt.invoc_api.service.InvocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invocation-api/v1/invocations")
public class InvocationController {

    private final InvocationService invocationService;

    public InvocationController(InvocationService invocationService) {
        this.invocationService = invocationService;
    }

    @GetMapping("/random/{playerId}")
    public ResponseEntity<Invocation> getInvocation(@PathVariable String playerId) {
        try {
            return ResponseEntity.ok(invocationService.getRandomInvocation(playerId));
        } catch (IllegalStateException e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Invocation>> getInvocations() {
        try {
            return ResponseEntity.ok(invocationService.getInvocations());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PostMapping("/batch")
    public List<Invocation> createAllInvocations(@RequestBody List<Invocation> invocations) {
        return invocationService.createAllInvocations(invocations);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteAllInvocations() {
        invocationService.deleteAllInvocations();
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
