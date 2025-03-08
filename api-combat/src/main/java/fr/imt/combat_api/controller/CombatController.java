package fr.imt.combat_api.controller;

import fr.imt.combat_api.model.Combat;
import fr.imt.combat_api.service.CombatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/combat")
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
        Combat combat = combatService.getCombat(id);
        if (combat != null) {
            return ResponseEntity.ok(combat);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
