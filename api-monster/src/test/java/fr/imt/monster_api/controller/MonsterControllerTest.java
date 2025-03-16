package fr.imt.monster_api.controller;

import fr.imt.monster_api.dto.MonsterRequest;
import fr.imt.monster_api.dto.SkillRequest;
import fr.imt.monster_api.model.ElementType;
import fr.imt.monster_api.model.Monster;
import fr.imt.monster_api.model.Skill;
import fr.imt.monster_api.service.MonsterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

class MonsterControllerTest {

    @Mock
    private MonsterService monsterService;

    @InjectMocks
    private MonsterController monsterController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllMonsters_ShouldReturnList() {
        List<Monster> mockMonsters = List.of(new Monster(), new Monster());
        when(monsterService.getAllMonsters()).thenReturn(mockMonsters);

        ResponseEntity<List<Monster>> response = monsterController.getAllMonsters();

        assertEquals(OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    void getMonsterById_Found_ShouldReturnMonster() {
        Monster monster = new Monster();
        monster.setId("123");
        when(monsterService.getMonsterById("123")).thenReturn(Optional.of(monster));

        ResponseEntity<Monster> response = monsterController.getMonsterById("123");

        assertEquals(OK, response.getStatusCode());
        assertEquals("123", Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    void getMonsterById_NotFound_ShouldReturn404() {
        when(monsterService.getMonsterById("999")).thenReturn(Optional.empty());

        ResponseEntity<Monster> response = monsterController.getMonsterById("999");

        assertEquals(NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void addMonster_Valid_ShouldReturnSavedMonster() {
        MonsterRequest monsterRequest = MonsterRequest.builder()
                .name("Fire Dragon")
                .element(ElementType.FIRE)
                .hp(100)
                .atk(50)
                .def(30)
                .vit(10)
                .skills(List.of(SkillRequest.builder().build()))
                .build();

        Monster expectedMonster = monsterRequest.convertToMonster();
        expectedMonster.setId("monster-123");

        when(monsterService.addMonster(monsterRequest)).thenReturn(expectedMonster);

        Monster result = monsterService.addMonster(monsterRequest);

        assertEquals(expectedMonster.getId(), result.getId());
        assertEquals(expectedMonster.getName(), result.getName());
        assertEquals(expectedMonster.getElement(), result.getElement());
        assertEquals(expectedMonster.getHp(), result.getHp());
        assertEquals(expectedMonster.getAtk(), result.getAtk());
        assertEquals(expectedMonster.getDef(), result.getDef());
        assertEquals(expectedMonster.getVit(), result.getVit());
        assertEquals(expectedMonster.getSkills(), result.getSkills());

        verify(monsterService, times(1)).addMonster(monsterRequest);
    }

    @Test
    void addMonster_Invalid_ShouldReturn400() {
        when(monsterService.addMonster(any())).thenThrow(new IllegalArgumentException());

        ResponseEntity<Monster> response = monsterController.addMonster(new MonsterRequest());

        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void updateMonster_Valid_ShouldReturn200() {
        doNothing().when(monsterService).updateMonster(any());

        ResponseEntity<Void> response = monsterController.updateMonster("123", new Monster());

        assertEquals(OK, response.getStatusCode());
    }

    @Test
    void updateMonster_Invalid_ShouldReturn400() {
        doThrow(new IllegalArgumentException()).when(monsterService).updateMonster(any());

        ResponseEntity<Void> response = monsterController.updateMonster("123", new Monster());

        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void deleteMonster_ShouldReturn204() {
        doNothing().when(monsterService).deleteMonster("123");

        ResponseEntity<Void> response = monsterController.deleteMonster("123");

        assertEquals(NO_CONTENT, response.getStatusCode());
    }
}
