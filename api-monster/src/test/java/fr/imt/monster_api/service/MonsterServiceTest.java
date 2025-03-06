package fr.imt.monster_api.service;

import fr.imt.monster_api.model.Monster;
import fr.imt.monster_api.model.Skill;
import fr.imt.monster_api.repository.MonsterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MonsterServiceTest {

    private MonsterRepository monsterRepository;
    private MonsterService monsterService;

    @BeforeEach
    void setUp() {
        monsterRepository = mock(MonsterRepository.class);
        monsterService = new MonsterService(monsterRepository);
    }

    @Test
    void getAllMonsters_ShouldReturnList() {
        when(monsterRepository.findAll()).thenReturn(List.of(Monster.builder().build(), Monster.builder().build()));

        List<Monster> monsters = monsterService.getAllMonsters();

        assertEquals(2, monsters.size());
    }

    @Test
    void getMonsterById_Found_ShouldReturnMonster() {
        Monster monster = Monster.builder().build();
        monster.setId("123");
        when(monsterRepository.findById("123")).thenReturn(Optional.of(monster));

        Optional<Monster> result = monsterService.getMonsterById("123");

        assertTrue(result.isPresent());
        assertEquals("123", result.get().getId());
    }

    @Test
    void getMonsterById_NotFound_ShouldReturnEmpty() {
        when(monsterRepository.findById("999")).thenReturn(Optional.empty());

        Optional<Monster> result = monsterService.getMonsterById("999");

        assertFalse(result.isPresent());
    }

    @Test
    void addMonster_ShouldSaveAndReturnMonster() {
        Monster monster = Monster.builder().build();
        when(monsterRepository.save(monster)).thenReturn(monster);

        Monster savedMonster = monsterService.addMonster(monster);

        assertNotNull(savedMonster);
        verify(monsterRepository).save(monster);
    }

    @Test
    void updateMonster_Valid_ShouldSave() {
        Monster monster = Monster.builder().build();
        monster.setId("123");

        monsterService.updateMonster(monster);

        verify(monsterRepository).save(monster);
    }

    @Test
    void updateMonster_Invalid_ShouldThrowException() {
        Monster monster = Monster.builder().build();
        monster.setSkills(List.of(new Skill(), new Skill(), new Skill(), new Skill()));

        assertThrows(IllegalArgumentException.class, () -> monsterService.updateMonster(monster));
    }

    @Test
    void deleteMonster_ShouldCallRepository() {
        doNothing().when(monsterRepository).deleteById("123");

        monsterService.deleteMonster("123");

        verify(monsterRepository).deleteById("123");
    }

    @Test
    void levelUpMonster_Found_ShouldIncreaseLevel() {
        Monster monster = Monster.builder().build();
        monster.setId("123");
        monster.setLevel(5);

        when(monsterRepository.findById("123")).thenReturn(Optional.of(monster));

        monsterService.levelUpMonster("123");

        assertEquals(6, monster.getLevel()); // Vérifie que le niveau a bien augmenté
        verify(monsterRepository).save(monster); // Vérifie que save() a bien été appelé
    }


    @Test
    void levelUpMonster_NotFound_ShouldThrowException() {
        when(monsterRepository.findById("999")).thenReturn(Optional.empty());

        NoSuchElementException thrown = assertThrows(NoSuchElementException.class,
                () -> monsterService.levelUpMonster("999"));

        assertEquals("Monstre non trouvé avec l'ID : 999", thrown.getMessage());
        verify(monsterRepository, never()).save(any());
    }

}
