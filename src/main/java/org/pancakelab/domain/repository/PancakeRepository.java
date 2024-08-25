package org.pancakelab.domain.repository;

import org.pancakelab.domain.PancakeOrderEntity;
import org.pancakelab.domain.model.pancakes.Pancake;

import java.util.List;
import java.util.UUID;

public interface PancakeRepository {
    void addPancake(Pancake pancake);
    List<Pancake> findByOrderId(UUID orderId);
    void removePancakes(UUID orderId, String description, int count);

    void saveAll(UUID orderId, List<Pancake> pancakes);

    void save(PancakeOrderEntity pancakeOrderEntity);

    void deleteByOrderId(UUID orderId);

    List<Pancake> findAll();
}
