package fr.imt.combat_api.controller;

import fr.imt.combat_api.model.Combat;
import fr.imt.combat_api.service.CombatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/combat-api/v1/combats")
public class CombatController {

    private final CombatService combatService;

    public CombatController(CombatService combatService) {
        this.combatService = combatService;
    }

    /**
     * Démarre un combat avec deux monstres donnés.
     *
     * @param monsterIds le tableau avec les id des monstres
     * @return La réponse avec l'objet Combat, contenant le vainqueur et les logs du combat.
     */
    @PostMapping("/start")
    public ResponseEntity<Combat> startCombat(@RequestBody List<String> monsterIds) {
        if (monsterIds.size() < 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please provide at least 2 monster ids");
        }
        Combat startedCombat = combatService.startCombat(monsterIds.getFirst(), monsterIds.get(1));
        return ResponseEntity.ok(startedCombat);
    }

    /**
     * Récupère tous les combats effectués.
     *
     * @return Une liste des combats passés.
     */
    @GetMapping("/history")
    public ResponseEntity<List<Combat>> getAllCombats() {
        List<Combat> allCombats = combatService.getAllCombats();
        return ResponseEntity.ok(allCombats);
    }

    /**
     * Récupère un combat spécifique à partir de son ID.
     *
     * @param id L'ID du combat à récupérer.
     * @return La réponse avec l'objet Combat spécifique, si trouvé.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Combat> getCombat(@PathVariable String id) {
        Optional<Combat> combat = combatService.getCombat(id);
        return combat.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
