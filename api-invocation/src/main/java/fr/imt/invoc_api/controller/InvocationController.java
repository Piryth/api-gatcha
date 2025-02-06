package fr.imt.invoc_api.controller;

import fr.imt.invoc_api.model.Invocation;
import fr.imt.invoc_api.service.InvocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invocations")
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
    public void saveInvocation(@RequestBody Invocation invocation) {
        invocationService.saveInvocation(invocation);
    }

    @DeleteMapping("/{id}")
    public void deleteInvocation(@PathVariable String id) {
        invocationService.deleteInvocation(id);
    }

}
