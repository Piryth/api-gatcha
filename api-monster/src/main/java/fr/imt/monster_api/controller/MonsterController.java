package fr.imt.monster_api.controller;

import fr.imt.monster_api.model.Monster;
import fr.imt.monster_api.service.MonsterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/monsters")
public class MonsterController {

    private final MonsterService monsterService;

    public MonsterController(MonsterService monsterService) {
        this.monsterService = monsterService;
    }

    // 1️⃣ Récupérer tous les monstres
    @GetMapping
    public ResponseEntity<List<Monster>> getAllMonsters() {
        List<Monster> monsters = monsterService.getAllMonsters();
        return ResponseEntity.ok(monsters);
    }

    // 2️⃣ Récupérer un monstre par ID
    @GetMapping("/{id}")
    public ResponseEntity<Monster> getMonsterById(@PathVariable String id) {
        Optional<Monster> monster = monsterService.getMonsterById(id);
        return monster.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 3️⃣ Ajouter un monstre
    @PostMapping
    public ResponseEntity<Monster> addMonster(@RequestBody Monster monster) {
        try {
            Monster savedMonster = monsterService.addMonster(monster);
            return ResponseEntity.ok(savedMonster);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Erreur si plus de 3 compétences
        }
    }

    // 4️⃣ Ajouter plusieurs monstres (batch)
    @PostMapping("/batch")
    public ResponseEntity<List<Monster>> addMonsters(@RequestBody List<Monster> monsters) {
        try {
            List<Monster> savedMonsters = monsterService.addMonsters(monsters);
            return ResponseEntity.ok(savedMonsters);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Erreur si plus de 3 compétences
        }
    }

    // 5️⃣ Supprimer un monstre par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMonster(@PathVariable String id) {
        monsterService.deleteMonster(id);
        return ResponseEntity.noContent().build(); // Confirmation de suppression
    }

    // 6️⃣ Récupérer tous les monstres par une liste d'IDs
    @GetMapping("/ids")
    public ResponseEntity<List<Monster>> getMonstersByIds(@RequestParam List<String> ids) {
        List<Monster> monsters = monsterService.getAllMonstersByIds(ids);
        return ResponseEntity.ok(monsters);
    }

    // 7️⃣ Supprimer tous les monstres par une liste d'IDs
    @DeleteMapping("/ids")
    public ResponseEntity<Void> deleteMonstersByIds(@RequestParam List<String> ids) {
        monsterService.deleteAllMonstersByIds(ids);
        return ResponseEntity.noContent().build(); // Confirmation de suppression
    }
}
