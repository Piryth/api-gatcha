package fr.imt.invoc_api.service;

import fr.imt.invoc_api.client.MonsterClient;
import fr.imt.invoc_api.client.PlayerClient;
import fr.imt.invoc_api.dto.MonsterRequest;
import fr.imt.invoc_api.dto.MonsterResponse;
import fr.imt.invoc_api.model.Invocation;
import fr.imt.invoc_api.model.ElementType;
import fr.imt.invoc_api.repository.InvocationRepository;
import fr.imt.invoc_api.repository.SaveInvocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InvocationServiceTest {

    @Mock
    private InvocationRepository invocationRepository;

    @Mock
    private SaveInvocationRepository saveInvocationRepository;

    @Mock
    private MonsterClient monsterClient;

    @Mock
    private PlayerClient playerClient;

    @InjectMocks
    private InvocationService invocationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getRandomInvocation_ShouldReturnMonsterResponse_WhenInvocationsExist() {
        Invocation invocation1 = Invocation.builder()
                .id("1")
                .name("Fire Dragon")
                .element(ElementType.FIRE)
                .lootRate(0.6f)
                .build();

        Invocation invocation2 = Invocation.builder()
                .id("2")
                .name("Water Serpent")
                .element(ElementType.WATER)
                .lootRate(0.4f)
                .build();

        List<Invocation> mockInvocations = Arrays.asList(invocation1, invocation2);

        when(invocationRepository.findAll()).thenReturn(mockInvocations);

        MonsterResponse mockMonster = MonsterResponse.builder()
                .id("123")
                .name("Fire Dragon")
                .element(ElementType.FIRE)
                .build();
        when(monsterClient.createMonster(any())).thenReturn(mockMonster);


        MonsterResponse result = invocationService.getRandomInvocation("player-123");

        assertNotNull(result);
        assertEquals("Fire Dragon", result.getName());
        assertEquals(ElementType.FIRE, result.getElement());

        verify(monsterClient).createMonster(any());
        verify(playerClient).addMonster("player-123", "123");
        verify(saveInvocationRepository).save(any());
    }

    @Test
    void getRandomInvocation_ShouldReturnNull_WhenNoInvocationsExist() {
        when(invocationRepository.findAll()).thenReturn(List.of());

        MonsterResponse result = invocationService.getRandomInvocation("player-123");

        assertNull(result);

        verify(monsterClient, never()).createMonster(any());
        verify(playerClient, never()).addMonster(any(), any());
        verify(saveInvocationRepository, never()).save(any());
    }

    @Test
    void getRandomInvocation_ShouldThrowException_WhenNoValidInvocationSelected() {
        Invocation invocation = Invocation.builder()
                .id("1")
                .name("Fire Dragon")
                .element(ElementType.FIRE)
                .lootRate(0.0f)
                .build();

        when(invocationRepository.findAll()).thenReturn(List.of(invocation));

        assertThrows(IllegalStateException.class, () -> invocationService.getRandomInvocation("player-123"));

        verify(monsterClient, never()).createMonster(any());
        verify(playerClient, never()).addMonster(any(), any());
        verify(saveInvocationRepository, never()).save(any());
    }

    @Test
    void getRandomInvocationReturnsInvocationBasedOnLootRates() {
        Invocation invocation1 = Invocation.builder()
                .id("1")
                .name("Fire Dragon")
                .element(ElementType.FIRE)
                .lootRate(0.6f)
                .build();

        Invocation invocation2 = Invocation.builder()
                .id("2")
                .name("Water Serpent")
                .element(ElementType.WATER)
                .lootRate(0.3f)
                .build();

        Invocation invocation3 = Invocation.builder()
                .id("3")
                .name("Earth Golem")
                .element(ElementType.EARTH)
                .lootRate(0.1f)
                .build();


        List<Invocation> mockInvocations = List.of(invocation1, invocation2, invocation3);
        when(invocationRepository.findAll()).thenReturn(mockInvocations);

        when(monsterClient.createMonster(any())).thenAnswer(invocation -> {
            MonsterRequest request = invocation.getArgument(0);
            return MonsterResponse.builder().name(request.getName()).element(request.getElement()).build();
        });

        doNothing().when(playerClient).addMonster(any(), any());

        Map<String, Integer> selectionCounts = new HashMap<>();
        selectionCounts.put("Fire Dragon", 0);
        selectionCounts.put("Water Serpent", 0);
        selectionCounts.put("Earth Golem", 0);

        int iterations = 100000;

        for (int i = 0; i < iterations; i++) {
            MonsterResponse result = invocationService.getRandomInvocation("player-123");
            if (result != null) {
                selectionCounts.put(result.getName(), selectionCounts.get(result.getName()) + 1);
            }
        }

        double fireDragonRate = selectionCounts.get("Fire Dragon") / (double) iterations;
        double waterSerpentRate = selectionCounts.get("Water Serpent") / (double) iterations;
        double earthGolemRate = selectionCounts.get("Earth Golem") / (double) iterations;

        double expectedFireDragonRate = 0.6;
        double expectedWaterSerpentRate = 0.3;
        double expectedEarthGolemRate = 0.1;

        double delta = 0.02;

        assertEquals(expectedFireDragonRate, fireDragonRate, delta, "Fire Dragon rate is incorrect");
        assertEquals(expectedWaterSerpentRate, waterSerpentRate, delta, "Water Serpent rate is incorrect");
        assertEquals(expectedEarthGolemRate, earthGolemRate, delta, "Earth Golem rate is incorrect");

    }

    @Test
    void getInvocationsReturnsAllInvocations() {
        List<Invocation> invocations = Arrays.asList(
                Invocation.builder().id("1").element(ElementType.WATER).lootRate(0.3f).build(),
                Invocation.builder().id("2").element(ElementType.FIRE).lootRate(0.3f).build(),
                Invocation.builder().id("3").element(ElementType.EARTH).lootRate(0.3f).build(),
                Invocation.builder().id("4").element(ElementType.WIND).lootRate(0.1f).build()
        );
        when(invocationRepository.findAll()).thenReturn(invocations);

        List<Invocation> result = invocationService.getInvocations();

        assertEquals(invocations, result);
    }

    @Test
    void createAllInvocationsSavesAndReturnsAllInvocations() {
        List<Invocation> invocations = Arrays.asList(
                Invocation.builder().id("1").element(ElementType.WATER).lootRate(0.3f).build(),
                Invocation.builder().id("2").element(ElementType.FIRE).lootRate(0.3f).build(),
                Invocation.builder().id("3").element(ElementType.EARTH).lootRate(0.3f).build(),
                Invocation.builder().id("4").element(ElementType.WIND).lootRate(0.1f).build()
        );
        when(invocationRepository.saveAll(invocations)).thenReturn(invocations);

        List<Invocation> result = invocationService.createAllInvocations(invocations);

        assertEquals(invocations, result);
    }

    @Test
    void createAllInvocationsThrowsIllegalArgumentExceptionWhenSumIsNotOne() {
        List<Invocation> invocations = Arrays.asList(
                Invocation.builder().id("1").element(ElementType.WATER).lootRate(0.3f).build(),
                Invocation.builder().id("2").element(ElementType.FIRE).lootRate(0.3f).build(),
                Invocation.builder().id("3").element(ElementType.EARTH).lootRate(0.3f).build(),
                Invocation.builder().id("4").element(ElementType.WIND).lootRate(0.5f).build()
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
