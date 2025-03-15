package fr.imt.combat_api.service;

import fr.imt.combat_api.client.MonsterClient;
import fr.imt.combat_api.model.Combat;
import fr.imt.combat_api.model.Ratio;
import fr.imt.combat_api.model.Round;
import fr.imt.combat_api.model.response.MonsterResponse;
import fr.imt.combat_api.model.response.SkillResponse;
import fr.imt.combat_api.repository.CombatRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CombatService {

    private final CombatRepository combatRepository;
    private final MonsterClient monsterClient;

    public CombatService(CombatRepository combatRepository, MonsterClient monsterClient) {
        this.combatRepository = combatRepository;
        this.monsterClient = monsterClient;
    }

    public Combat startCombat(String idMonstre1, String idMonstre2) {
        Combat combat = new Combat();
        combat.setMonsterIds(Arrays.asList(idMonstre1, idMonstre2));
        combat.setRounds(new ArrayList<>());

        MonsterResponse monster1 = monsterClient.getMonster(idMonstre1);
        MonsterResponse monster2 = monsterClient.getMonster(idMonstre2);

        assert monster1 != null;
        assert monster2 != null;

        Map<Integer, Integer> cooldownsMonster1 = initCooldowns(monster1);
        Map<Integer, Integer> cooldownsMonster2 = initCooldowns(monster2);

        simulateCombat(combat, monster1, monster2, cooldownsMonster1, cooldownsMonster2);

        combatRepository.save(combat);
        return combat;
    }

    private void simulateCombat(Combat combat, MonsterResponse monster1, MonsterResponse monster2,
                                Map<Integer, Integer> cooldownsMonster1, Map<Integer, Integer> cooldownsMonster2) {
        int roundCounter = 1;
        double monster1Hp = monster1.getHp();
        double monster2Hp = monster2.getHp();

        while (monster1Hp > 0 && monster2Hp > 0) {
            Round round = new Round();
            round.setId("round" + roundCounter);

            if (roundCounter % 2 == 1) {
                int skillIndex = getAvailableSkillIndex(monster1, cooldownsMonster1);
                if (skillIndex != -1) {
                    round.setIdAttacker(monster1.getId());
                    round.setIdSkillUsed(String.valueOf(skillIndex + 1));

                    SkillResponse skill = monster1.getSkills().get(skillIndex);
                    double damage = calculateDamage(monster1, skill, monster2);

                    round.setDamageDealt(damage);
                    monster2Hp = Math.max(0, monster2Hp - damage);
                    cooldownsMonster1.put(skillIndex, skill.getCooldown());
                }
            } else {
                int skillIndex = getAvailableSkillIndex(monster2, cooldownsMonster2);
                if (skillIndex != -1) {
                    round.setIdAttacker(monster2.getId());
                    round.setIdSkillUsed(String.valueOf(skillIndex + 1));

                    SkillResponse skill = monster2.getSkills().get(skillIndex);
                    double damage = calculateDamage(monster2, skill, monster1);

                    round.setDamageDealt(damage);
                    monster1Hp = Math.max(0, monster1Hp - damage);
                    cooldownsMonster2.put(skillIndex, skill.getCooldown());
                }
            }

            updateCooldowns(cooldownsMonster1);
            updateCooldowns(cooldownsMonster2);

            combat.getRounds().add(round);
            roundCounter++;

            if (roundCounter > 10000) {
                break;
            }
        }

        combat.setWinner(monster1Hp > 0 ? monster1.getId() : monster2.getId());
    }

    private int getAvailableSkillIndex(MonsterResponse monster, Map<Integer, Integer> cooldowns) {
        List<SkillResponse> skills = monster.getSkills();
        for (int i = skills.size() - 1; i >= 0; i--) {
            if (cooldowns.getOrDefault(i, 0) == 0) {
                return i;
            }
        }
        return -1;
    }

    private double calculateDamage(MonsterResponse attacker, SkillResponse skill, MonsterResponse defender) {
        double baseDamage = skill.getDamage();
        Ratio ratio = skill.getRatio();
        if (ratio == null) {
            return baseDamage;
        }

        double statValue = switch (ratio.getStat()) {
            case ATK -> attacker.getAtk();
            case HP -> attacker.getHp();
            case DEF -> attacker.getDef();
            case VIT -> attacker.getVit();
        };

        double bonusDamage = statValue * (ratio.getPercent() / 100.0);
        double totalDamage = baseDamage + bonusDamage;
        double finalDamage = totalDamage / (1 + (defender.getDef() / 100.0));

        return Math.max(1, finalDamage);
    }

    private Map<Integer, Integer> initCooldowns(MonsterResponse monster) {
        Map<Integer, Integer> cooldowns = new HashMap<>();
        for (int i = 0; i < monster.getSkills().size(); i++) {
            cooldowns.put(i, 0);
        }
        return cooldowns;
    }

    private void updateCooldowns(Map<Integer, Integer> cooldowns) {
        cooldowns.replaceAll((k, v) -> Math.max(0, v - 1));
    }

    public List<Combat> getAllCombats() {
        return combatRepository.findAll();
    }

    public Optional<Combat> getCombat(String id) {
        return combatRepository.findById(id);
    }
}
