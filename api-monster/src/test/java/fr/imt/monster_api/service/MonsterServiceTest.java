package fr.imt.monster_api.service;

import fr.imt.monster_api.dto.MonsterRequest;
import fr.imt.monster_api.dto.SkillRequest;
import fr.imt.monster_api.model.ElementType;
import fr.imt.monster_api.model.Monster;
import fr.imt.monster_api.model.Skill;
import fr.imt.monster_api.repository.MonsterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MonsterServiceTest {

    @Mock
    private MonsterRepository monsterRepository;

    @InjectMocks
    private MonsterService monsterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllMonsters_ShouldReturnList() {
        when(monsterRepository.findAll()).thenReturn(List.of(new Monster(), new Monster()));

        List<Monster> monsters = monsterService.getAllMonsters();

        assertEquals(2, monsters.size());
    }

    @Test
    void getMonsterById_Found_ShouldReturnMonster() {
        Monster monster = new Monster();
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
        MonsterRequest monsterRequest = MonsterRequest.builder()
                .name("Fire Dragon")
                .element(ElementType.FIRE)
                .hp(100)
                .atk(50)
                .def(30)
                .vit(10)
                .skills(List.of(SkillRequest.builder().build()))
                .build();

        Monster monster = monsterRequest.convertToMonster();
        monster.setId("monster-123");

        when(monsterRepository.save(any(Monster.class))).thenAnswer(invocation -> {
            Monster toSave = invocation.getArgument(0);
            toSave.setId("monster-123"); // Simulate ID assignment
            return toSave;
        });

        Monster result = monsterService.addMonster(monsterRequest);

        assertNotNull(result);

        assertEquals(monster.getName(), result.getName());
        assertEquals(monster.getElement(), result.getElement());
        assertEquals(monster.getHp(), result.getHp());
        assertEquals(monster.getAtk(), result.getAtk());
        assertEquals(monster.getDef(), result.getDef());
        assertEquals(monster.getVit(), result.getVit());
        assertEquals(monster.getSkills(), result.getSkills());

        verify(monsterRepository, times(1)).save(monster);
    }

    @Test
    void updateMonster_Valid_ShouldSave() {
        Monster monster = new Monster();
        monster.setId("123");

        monsterService.updateMonster(monster);

        verify(monsterRepository).save(monster);
    }

    @Test
    void updateMonster_Invalid_ShouldThrowException() {
        Monster monster = new Monster();
        monster.setSkills(List.of(
                Skill.builder().build(),
                Skill.builder().build(),
                Skill.builder().build(),
                Skill.builder().build()
        ));

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
        Monster monster = new Monster();
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
