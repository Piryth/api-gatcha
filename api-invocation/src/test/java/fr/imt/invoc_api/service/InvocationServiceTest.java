package fr.imt.invoc_api.service;

import fr.imt.invoc_api.model.Invocation;
import fr.imt.invoc_api.model.Type;
import fr.imt.invoc_api.repository.InvocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InvocationServiceTest {

    @Mock
    private InvocationRepository invocationRepository;

    @InjectMocks
    private InvocationService invocationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getRandomInvocationReturnsInvocationWhenListIsNotEmpty() {
        List<Invocation> invocations = Arrays.asList(
                new Invocation("1", Type.WATER, 0, 0, 0, 0, null, 0.3f),
                new Invocation("2", Type.FIRE, 0, 0, 0, 0, null, 0.3f),
                new Invocation("3", Type.EARTH, 0, 0, 0, 0, null, 0.3f),
                new Invocation("4", Type.WIND, 0, 0, 0, 0, null, 0.1f)
        );
        when(invocationRepository.findAll()).thenReturn(invocations);

        Invocation result = invocationService.getRandomInvocation();

        assertNotNull(result);
        assertTrue(invocations.contains(result));
    }

    @Test
    void getRandomInvocationReturnsInvocationBasedOnLootRates() {
        List<Invocation> invocations = Arrays.asList(
                new Invocation("1", Type.WATER, 0, 0, 0, 0, null, 0.4f),
                new Invocation("2", Type.FIRE, 0, 0, 0, 0, null, 0.3f),
                new Invocation("3", Type.EARTH, 0, 0, 0, 0, null, 0.2f),
                new Invocation("4", Type.WIND, 0, 0, 0, 0, null, 0.1f)
        );
        when(invocationRepository.findAll()).thenReturn(invocations);

        int waterCount = 0;
        int fireCount = 0;
        int earthCount = 0;
        int windCount = 0;
        int iterations = 100000;

        for (int i = 0; i < iterations; i++) {
            Invocation result = invocationService.getRandomInvocation();
            assertNotNull(result);
            switch (result.getId()) {
                case "1":
                    waterCount++;
                    break;
                case "2":
                    fireCount++;
                    break;
                case "3":
                    earthCount++;
                    break;
                case "4":
                    windCount++;
                    break;
            }
        }

        assertEquals(0.4, (double) waterCount / iterations, 0.01);
        assertEquals(0.3, (double) fireCount / iterations, 0.01);
        assertEquals(0.2, (double) earthCount / iterations, 0.01);
        assertEquals(0.1, (double) windCount / iterations, 0.01);
    }

    @Test
    void getInvocationsReturnsAllInvocations() {
        List<Invocation> invocations = Arrays.asList(
                new Invocation("1", Type.WATER, 0, 0, 0, 0, null, 0.3f),
                new Invocation("2", Type.FIRE, 0, 0, 0, 0, null, 0.3f)
        );
        when(invocationRepository.findAll()).thenReturn(invocations);

        List<Invocation> result = invocationService.getInvocations();

        assertEquals(invocations, result);
    }

    @Test
    void createAllInvocationsSavesAndReturnsAllInvocations() {
        List<Invocation> invocations = Arrays.asList(
                new Invocation("1", Type.WATER, 0, 0, 0, 0, null, 0.3f),
                new Invocation("2", Type.FIRE, 0, 0, 0, 0, null, 0.3f),
                new Invocation("3", Type.FIRE, 0, 0, 0, 0, null, 0.3f),
                new Invocation("4", Type.FIRE, 0, 0, 0, 0, null, 0.1f)
        );
        when(invocationRepository.saveAll(invocations)).thenReturn(invocations);

        List<Invocation> result = invocationService.createAllInvocations(invocations);

        assertEquals(invocations, result);
    }

    @Test
    void createAllInvocationsThrowsIllegalArgumentExceptionWhenSumIsNotOne() {
        List<Invocation> invocations = Arrays.asList(
                new Invocation("1", Type.WATER, 0, 0, 0, 0, null, 0.3f),
                new Invocation("2", Type.FIRE, 0, 0, 0, 0, null, 0.3f),
                new Invocation("3", Type.FIRE, 0, 0, 0, 0, null, 0.3f),
                new Invocation("4", Type.FIRE, 0, 0, 0, 0, null, 0.2f)
        );

        assertThrows(IllegalArgumentException.class, () -> invocationService.createAllInvocations(invocations));
    }

    @Test
    void deleteInvocationDeletesInvocationById() {
        doNothing().when(invocationRepository).deleteAll();

        invocationService.deleteAllInvocations();

        verify(invocationRepository, times(1)).deleteAll();
    }
}
