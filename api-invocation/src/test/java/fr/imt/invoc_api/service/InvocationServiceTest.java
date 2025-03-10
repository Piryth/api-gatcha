package fr.imt.invoc_api.service;

import fr.imt.invoc_api.client.MonsterApiService;
import fr.imt.invoc_api.client.PlayerApiService;
import fr.imt.invoc_api.model.Invocation;
import fr.imt.invoc_api.model.Type;
import fr.imt.invoc_api.repository.InvocationRepository;
import fr.imt.invoc_api.repository.SaveInvocationRepository;
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

    @Mock
    private SaveInvocationRepository saveInvocationRepository;

    @Mock
    private MonsterApiService monsterApiService;

    @Mock
    private PlayerApiService playerApiService;

    @InjectMocks
    private InvocationService invocationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getRandomInvocationReturnsInvocationWhenListIsNotEmpty() {
        List<Invocation> invocations = Arrays.asList(
                Invocation.builder().id("1").element(Type.WATER).lootRate(0.3f).build(),
                Invocation.builder().id("2").element(Type.FIRE).lootRate(0.3f).build(),
                Invocation.builder().id("3").element(Type.EARTH).lootRate(0.3f).build(),
                Invocation.builder().id("4").element(Type.WIND).lootRate(0.1f).build()
        );
        when(invocationRepository.findAll()).thenReturn(invocations);

        Invocation result = invocationService.getRandomInvocation();

        assertNotNull(result);
        assertTrue(invocations.contains(result));
    }

    @Test
    void getRandomInvocationReturnsInvocationBasedOnLootRates() {
        List<Invocation> invocations = Arrays.asList(
                Invocation.builder().id("1").element(Type.WATER).lootRate(0.4f).build(),
                Invocation.builder().id("2").element(Type.FIRE).lootRate(0.3f).build(),
                Invocation.builder().id("3").element(Type.EARTH).lootRate(0.2f).build(),
                Invocation.builder().id("4").element(Type.WIND).lootRate(0.1f).build()
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
                Invocation.builder().id("1").element(Type.WATER).lootRate(0.3f).build(),
                Invocation.builder().id("2").element(Type.FIRE).lootRate(0.3f).build(),
                Invocation.builder().id("3").element(Type.EARTH).lootRate(0.3f).build(),
                Invocation.builder().id("4").element(Type.WIND).lootRate(0.1f).build()
        );
        when(invocationRepository.findAll()).thenReturn(invocations);

        List<Invocation> result = invocationService.getInvocations();

        assertEquals(invocations, result);
    }

    @Test
    void createAllInvocationsSavesAndReturnsAllInvocations() {
        List<Invocation> invocations = Arrays.asList(
                Invocation.builder().id("1").element(Type.WATER).lootRate(0.3f).build(),
                Invocation.builder().id("2").element(Type.FIRE).lootRate(0.3f).build(),
                Invocation.builder().id("3").element(Type.EARTH).lootRate(0.3f).build(),
                Invocation.builder().id("4").element(Type.WIND).lootRate(0.1f).build()
        );
        when(invocationRepository.saveAll(invocations)).thenReturn(invocations);

        List<Invocation> result = invocationService.createAllInvocations(invocations);

        assertEquals(invocations, result);
    }

    @Test
    void createAllInvocationsThrowsIllegalArgumentExceptionWhenSumIsNotOne() {
        List<Invocation> invocations = Arrays.asList(
                Invocation.builder().id("1").element(Type.WATER).lootRate(0.3f).build(),
                Invocation.builder().id("2").element(Type.FIRE).lootRate(0.3f).build(),
                Invocation.builder().id("3").element(Type.EARTH).lootRate(0.3f).build(),
                Invocation.builder().id("4").element(Type.WIND).lootRate(0.5f).build()
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
