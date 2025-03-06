package fr.imt.monster_api.controller;

import fr.imt.monster_api.model.Monster;
import fr.imt.monster_api.service.MonsterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/monster-api/v1/monsters")
@CrossOrigin(origins = "http://localhost:5173")
public class MonsterController {

    private final MonsterService monsterService;

    public MonsterController(MonsterService monsterService) {
        this.monsterService = monsterService;
    }

    // Récupérer tous les monstres
    @GetMapping
    public ResponseEntity<List<Monster>> getAllMonsters() {
        List<Monster> monsters = monsterService.getAllMonsters();
        return ResponseEntity.ok(monsters);
    }

    // Récupérer un monstre par ID
    @GetMapping("/{id}")
    public ResponseEntity<Monster> getMonsterById(@PathVariable String id) {
        Optional<Monster> monster = monsterService.getMonsterById(id);
        return monster.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Ajouter un monstre
    @PostMapping
    public ResponseEntity<Monster> addMonster(@RequestBody Monster monster) {
        try {
            Monster savedMonster = monsterService.addMonster(monster);
            return ResponseEntity.ok(savedMonster);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Erreur si plus de 3 compétences
        }
    }

    // Ajouter plusieurs monstres
    @PostMapping("/batch")
    public ResponseEntity<List<Monster>> addMonsters(@RequestBody List<Monster> monsters) {
        try {
            List<Monster> savedMonsters = monsterService.addMonsters(monsters);
            return ResponseEntity.ok(savedMonsters);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Erreur si plus de 3 compétences
        }
    }

    // Mettre à jour un monstre
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMonster(@PathVariable String id, @RequestBody Monster monster) {
        try {
            monster.setId(id);
            monsterService.updateMonster(monster);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Mettre à jour le niveau d'un monstre
    @PutMapping("/{id}/levelUp")
    public ResponseEntity<Void> levelUpMonster(@PathVariable String id) {
        try {
            monsterService.levelUpMonster(id);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Mettre à jour le niveau d'un monstre
    @PutMapping("/{id}/addUpgradePoint")
    public ResponseEntity<Void> addUpgradePointMonster(@PathVariable String id) {
        try {
            monsterService.addUpgradePointMonster(id);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Supprimer un monstre par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMonster(@PathVariable String id) {
        monsterService.deleteMonster(id);
        return ResponseEntity.noContent().build(); // Confirmation de suppression
    }

    // Récupérer tous les monstres par une liste d'IDs
    @GetMapping("/ids")
    public ResponseEntity<List<Monster>> getMonstersByIds(@RequestParam List<String> ids) {
        List<Monster> monsters = monsterService.getAllMonstersByIds(ids);
        return ResponseEntity.ok(monsters);
    }

    // Supprimer tous les monstres par une liste d'IDs
    @DeleteMapping("/ids")
    public ResponseEntity<Void> deleteMonstersByIds(@RequestParam List<String> ids) {
        monsterService.deleteAllMonstersByIds(ids);
        return ResponseEntity.noContent().build(); // Confirmation de suppression
    }
}
