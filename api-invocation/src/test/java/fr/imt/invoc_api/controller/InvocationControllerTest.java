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
        Invocation invocation = new Invocation("1", null, 0, 0, 0, 0, null, 0.3f);
        when(invocationService.getRandomInvocation()).thenReturn(invocation);

        Invocation result = invocationController.getInvocation();

        assertEquals(invocation, result);
    }

    @Test
    void getInvocationReturnsNullWhenNoInvocation() {
        when(invocationService.getRandomInvocation()).thenReturn(null);

        Invocation result = invocationController.getInvocation();

        assertNull(result);
    }

    @Test
    void getInvocationsReturnsListOfInvocations() {
        List<Invocation> invocations = Arrays.asList(
                new Invocation("1", null, 0, 0, 0, 0, null, 0.3f),
                new Invocation("2", null, 0, 0, 0, 0, null, 0.3f)
        );
        when(invocationService.getInvocations()).thenReturn(invocations);

        List<Invocation> result = invocationController.getInvocations();

        assertEquals(invocations, result);
    }

    @Test
    void createAllInvocationsReturnsListOfCreatedInvocations() {
        List<Invocation> invocations = Arrays.asList(
                new Invocation("1", null, 0, 0, 0, 0, null, 0.3f),
                new Invocation("2", null, 0, 0, 0, 0, null, 0.3f),
                new Invocation("3", null, 0, 0, 0, 0, null, 0.3f),
                new Invocation("4", null, 0, 0, 0, 0, null, 0.3f)
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
