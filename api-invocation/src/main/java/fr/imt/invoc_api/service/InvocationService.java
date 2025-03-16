package fr.imt.invoc_api.service;

import fr.imt.invoc_api.client.MonsterClient;
import fr.imt.invoc_api.client.PlayerClient;
import fr.imt.invoc_api.model.Invocation;
import fr.imt.invoc_api.dto.MonsterResponse;
import fr.imt.invoc_api.model.save.SaveInvocation;
import fr.imt.invoc_api.repository.InvocationRepository;
import fr.imt.invoc_api.repository.SaveInvocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class InvocationService {

    private final InvocationRepository invocationRepository;
    private final SaveInvocationRepository saveInvocationRepository;

    private static final Random random = new Random();
    private final MonsterClient monsterClient;
    private final PlayerClient playerClient;

    public InvocationService(InvocationRepository invocationRepository, SaveInvocationRepository saveInvocationRepository, MonsterClient monsterClient, PlayerClient playerClient) {
        this.invocationRepository = invocationRepository;
        this.saveInvocationRepository = saveInvocationRepository;
        this.monsterClient = monsterClient;
        this.playerClient = playerClient;
    }

    public MonsterResponse getRandomInvocation(String playerId) throws IllegalStateException {
        List<Invocation> invocations = invocationRepository.findAll();

        if (invocations.isEmpty()) {
            return null;
        }

        float randVal = random.nextFloat();

        float cumulative = 0.0f;
        Invocation invocation = null;

        for (Invocation _invocation : invocations) {
            cumulative += _invocation.getLootRate();
            if (randVal <= cumulative) {
                invocation = _invocation;
                break;
            }
        }

        if (invocation == null) {
            throw new IllegalStateException("No invocation found");
        }

        SaveInvocation saveInvocation = SaveInvocation.builder()
                .playerId(playerId)
                .invocation(invocation)
                .build();

        MonsterResponse monster = monsterClient.createMonster(invocation.convertToMonsterRequest());
        playerClient.addMonster(playerId, monster.getId());

        saveInvocationRepository.save(saveInvocation);

        return monster;
    }

    public List<Invocation> getInvocations() {
        return invocationRepository.findAll();
    }

    public List<Invocation> createAllInvocations(List<Invocation> invocations) throws IllegalArgumentException {
        // Check if the sum of loot rates is 1
        float sum = invocations.stream()
                .map(Invocation::getLootRate)
                .reduce(0.0f, Float::sum);

        float epsilon = 1e-6f;

        if (Math.abs(sum - 1.0f) >= epsilon) {
            throw new IllegalArgumentException("Sum is incorrect! Expected ~1.0 but got: " + sum);
        }

        return invocationRepository.saveAll(invocations);
    }

    public void deleteAllInvocations() {
        invocationRepository.deleteAll();
    }

}
