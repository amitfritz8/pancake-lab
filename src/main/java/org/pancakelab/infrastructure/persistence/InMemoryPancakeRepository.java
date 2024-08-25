package org.pancakelab.infrastructure.persistence;

import org.pancakelab.domain.PancakeOrderEntity;
import org.pancakelab.domain.model.pancakes.Pancake;
import org.pancakelab.domain.repository.PancakeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryPancakeRepository implements PancakeRepository {

    private final ConcurrentMap<UUID, List<Pancake>> pancakeStorage = new ConcurrentHashMap<>();

    private final List<Pancake> pancakes = new ArrayList<>();

    @Override
    public void addPancake(Pancake pancake) {
        pancakes.add(pancake);
    }

    @Override
    public List<Pancake> findByOrderId(UUID orderId) {
       return pancakeStorage.get(orderId);
    }

    @Override
    public void removePancakes(UUID orderId, String description, int count) {
        final AtomicInteger removedCount = new AtomicInteger(0);
        pancakes.removeIf(pancake -> pancake.getOrderId().equals(orderId) &&
                pancake.description().equals(description) &&
                removedCount.getAndIncrement() < count);
    }

    @Override
    public void saveAll(UUID orderId, List<Pancake> pancakes) {
        pancakeStorage.put(orderId, pancakes);
    }

    @Override
    public void save(PancakeOrderEntity pancakeOrderEntity) {
        pancakeStorage.put(pancakeOrderEntity.getOrderId(), new ArrayList<>());
    }

    @Override
    public void deleteByOrderId(UUID orderId) {
        pancakeStorage.remove(orderId);
    }

    @Override
    public List<Pancake> findAll() {
        return pancakeStorage.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}