package fr.imt.invoc_api.service;

import fr.imt.invoc_api.model.Invocation;
import fr.imt.invoc_api.repository.InvocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class InvocationService {

    @Autowired
    private InvocationRepository invocationRepository;

    private static final Random random = new Random();

    public Invocation getRandomInvocation() {
        List<Invocation> invocations = invocationRepository.findAll();

        if (invocations.isEmpty()) {
            return null;
        }

        float randVal = random.nextFloat();

        float cumulative = 0.0f;
        for (Invocation invocation : invocations) {
            cumulative += invocation.getLootRate();
            if (randVal <= cumulative) {
                return invocation;
            }
        }
        return null;
    }

    public List<Invocation> getInvocations() {
        return invocationRepository.findAll();
    }

    public List<Invocation> createAllInvocations(List<Invocation> invocations) throws IllegalArgumentException{
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
