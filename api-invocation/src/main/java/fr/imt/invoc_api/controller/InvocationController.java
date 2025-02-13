package fr.imt.invoc_api.controller;

import fr.imt.invoc_api.model.Invocation;
import fr.imt.invoc_api.service.InvocationService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public Invocation createInvocation(@RequestBody Invocation invocation) {
        return invocationService.createInvocation(invocation);
    }

    @PostMapping("/batch")
    public List<Invocation> createAllInvocations(@RequestBody List<Invocation> invocations) {
        return invocationService.createAllInvocations(invocations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvocation(@PathVariable String id) {
        invocationService.deleteInvocation(id);

        return ResponseEntity.ok().build();
    }

}
