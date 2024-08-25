package org.pancakelab.domain.repository;

import org.pancakelab.domain.PancakeOrderEntity;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public interface OrderRepository {
    Optional<PancakeOrderEntity> findById(UUID orderId);
    ConcurrentMap<UUID, PancakeOrderEntity> findAll();
    void save(PancakeOrderEntity pancakeOrderEntity);
    void deleteById(UUID orderId);
    public Set<UUID> getCompletedOrders();
    public Set<UUID> getPreparedOrders();

}