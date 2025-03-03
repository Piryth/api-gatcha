package fr.imt.invoc_api.service;

import fr.imt.invoc_api.client.MonsterApiService;
import fr.imt.invoc_api.client.PlayerApiService;
import fr.imt.invoc_api.model.Invocation;
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
    private final MonsterApiService monsterApiService;
    private final PlayerApiService playerApiService;

    public InvocationService(InvocationRepository invocationRepository, SaveInvocationRepository saveInvocationRepository, MonsterApiService monsterApiService, PlayerApiService playerApiService) {
        this.invocationRepository = invocationRepository;
        this.saveInvocationRepository = saveInvocationRepository;
        this.monsterApiService = monsterApiService;
        this.playerApiService = playerApiService;
    }

    public Invocation getRandomInvocation() throws IllegalStateException {
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

        String playerId = "playerId";
        SaveInvocation saveInvocation = SaveInvocation.builder()
                .playerId(playerId)
                .invocation(invocation)
                .build();

        saveInvocationRepository.save(saveInvocation);
        String monsterId = monsterApiService.createMonster(invocation);
        playerApiService.addMonster(playerId, monsterId);

        return invocation;
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
