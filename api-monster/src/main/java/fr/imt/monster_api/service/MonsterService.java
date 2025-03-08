package fr.imt.monster_api.service;

import fr.imt.monster_api.model.Monster;
import fr.imt.monster_api.repository.MonsterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MonsterService {

    private final MonsterRepository monsterRepository;

    public MonsterService(MonsterRepository monsterRepository) {
        this.monsterRepository = monsterRepository;
    }

    // Récupérer tous les monstres
    public List<Monster> getAllMonsters() {
        return monsterRepository.findAll();
    }

    // Récupérer un monstre par ID
    public Optional<Monster> getMonsterById(String id) {
        return monsterRepository.findById(id);  // Renvoie un Optional<Monster>
    }

    // Ajouter un monstre
    public Monster addMonster(Monster monster) {
        monster.setLevel(0);
        return monsterRepository.save(monster);
    }

    // Ajouter plusieurs monstres
    public List<Monster> addMonsters(List<Monster> monsters) {
        return monsterRepository.saveAll(monsters);
    }

    // Mettre à jour un monstre
    public void updateMonster(Monster monster) {
        if (monster.getSkills() != null && monster.getSkills().size() > 3) {
            throw new IllegalArgumentException("Un monstre ne peut pas avoir plus de 3 compétences.");
        }
        monsterRepository.save(monster);
    }

    // Mettre à jour le level d'un monstre
    public void levelUpMonster(String id) {
        Monster monster = monsterRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Monstre non trouvé avec l'ID : " + id));
        monster.setLevel(monster.getLevel() + 1);
        monsterRepository.save(monster);
    }


    // Mettre à jour le level d'un monstre
    public void addUpgradePointMonster(String id) {
        Monster monster = monsterRepository.findById(id).orElseThrow(() -> new NoSuchElementException(("Monstre non trouvé avec l'ID : " + id)));
        monster.setUpgradePoints(monster.getUpgradePoints() + 1);
        monsterRepository.save(monster);
    }


    // Supprimer un monstre par ID
    public void deleteMonster(String id) {
        monsterRepository.deleteById(id);
    }

    // Récupérer tous les monstres par une liste d'IDs
    public List<Monster> getAllMonstersByIds(List<String> ids) {
        return monsterRepository.findAllById(ids);
    }

    // Supprimer tous les monstres par une liste d'IDs
    public void deleteAllMonstersByIds(List<String> ids) {
        monsterRepository.deleteAllById(ids);
    }
}
