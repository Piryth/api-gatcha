package fr.imt.invoc_api.controller;

import fr.imt.invoc_api.model.Invocation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import fr.imt.invoc_api.service.InvocationService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class InvocationControllerTest {

    @Mock
    private InvocationService invocationService;

    @InjectMocks
    private InvocationController invocationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getInvocationReturnsRandomInvocation() {
        Invocation invocation = Invocation.builder().id("1").build();

        when(invocationService.getRandomInvocation("playerId")).thenReturn(invocation);

        Invocation result = invocationController.getInvocation("playerId").getBody();

        assertEquals(invocation, result);
    }

    @Test
    void getInvocationReturnsNullWhenNoInvocation() {
        when(invocationService.getRandomInvocation("playerId")).thenReturn(null);

        Invocation result = invocationController.getInvocation("playerId").getBody();

        assertNull(result);
    }

    @Test
    void getInvocationsReturnsListOfInvocations() {
        List<Invocation> invocations = Arrays.asList(
                Invocation.builder().id("1").build(),
                Invocation.builder().id("2").build()
        );
        when(invocationService.getInvocations()).thenReturn(invocations);

        List<Invocation> result = invocationController.getInvocations().getBody();

        assertEquals(invocations, result);
    }

    @Test
    void createAllInvocationsReturnsListOfCreatedInvocations() {
        List<Invocation> invocations = Arrays.asList(
                Invocation.builder().id("1").lootRate(0.3f).build(),
                Invocation.builder().id("2").lootRate(0.3f).build(),
                Invocation.builder().id("3").lootRate(0.3f).build(),
                Invocation.builder().id("4").lootRate(0.1f).build()
        );
        when(invocationService.createAllInvocations(invocations)).thenReturn(invocations);

        List<Invocation> result = invocationController.createAllInvocations(invocations);

        assertEquals(invocations, result);
    }

    @Test
    void deleteInvocationReturnsOkResponse() {
        doNothing().when(invocationService).deleteAllInvocations();

        ResponseEntity<Void> response = invocationController.deleteAllInvocations();

        assertEquals(ResponseEntity.ok().build(), response);
    }
}
