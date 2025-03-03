package fr.imt.monster_api.service;

import fr.imt.monster_api.model.Monster;
import fr.imt.monster_api.repository.MonsterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MonsterService {

    private final MonsterRepository monsterRepository;

    public MonsterService(MonsterRepository monsterRepository) {
        this.monsterRepository = monsterRepository;
    }

    // 1️⃣ Récupérer tous les monstres
    public List<Monster> getAllMonsters() {
        return monsterRepository.findAll();
    }

    // 2️⃣ Récupérer un monstre par ID
    public Optional<Monster> getMonsterById(String id) {
        return monsterRepository.findById(id);  // Renvoie un Optional<Monster>
    }

    // 3️⃣ Ajouter un monstre
    public Monster addMonster(Monster monster) {
        return monsterRepository.save(monster);
    }

    // 4️⃣ Ajouter plusieurs monstres (batch)
    public List<Monster> addMonsters(List<Monster> monsters) {
        return monsterRepository.saveAll(monsters);
    }

    // 5️⃣ Supprimer un monstre par ID
    public void deleteMonster(String id) {
        monsterRepository.deleteById(id);
    }

    // 6️⃣ Récupérer tous les monstres par une liste d'IDs
    public List<Monster> getAllMonstersByIds(List<String> ids) {
        return monsterRepository.findAllById(ids);
    }

    // 7️⃣ Supprimer tous les monstres par une liste d'IDs
    public void deleteAllMonstersByIds(List<String> ids) {
        monsterRepository.deleteAllById(ids);
    }
}
