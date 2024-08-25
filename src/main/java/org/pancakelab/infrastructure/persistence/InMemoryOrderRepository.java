package org.pancakelab.infrastructure.persistence;

import lombok.Data;
import org.pancakelab.domain.PancakeOrderEntity;
import org.pancakelab.domain.repository.OrderRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * InMemoryOrderRepository provides an in-memory implementation of the OrderRepository.
 * It manages orders, including completed and prepared orders, using thread-safe collections.
 */
@Data
public class InMemoryOrderRepository implements OrderRepository {

    private final ConcurrentMap<UUID, PancakeOrderEntity> orders = new ConcurrentHashMap<>();
    private final Set<UUID> completedOrders = ConcurrentHashMap.newKeySet();
    private final Set<UUID> preparedOrders = ConcurrentHashMap.newKeySet();

    /**
     * Finds an order by its UUID.
     *
     * @param orderId The UUID of the order.
     * @return An Optional containing the found Order, or empty if not found.
     */
    @Override
    public Optional<PancakeOrderEntity> findById(UUID orderId) {
        return Optional.of(orders.get(orderId));
    }

    /**
     * Retrieves all orders.
     *
     * @return A list of all orders.
     */
    @Override
    public ConcurrentMap<UUID, PancakeOrderEntity> findAll() {
        return orders;
    }

    /**
     * Saves an order to the repository.
     *
     * @param pancakeOrderEntity The order to be saved.
     */
    @Override
    public void save(PancakeOrderEntity pancakeOrderEntity) {
        orders.put(pancakeOrderEntity.getOrderId(), pancakeOrderEntity);
    }

    /**
     * Deletes an order by its UUID.
     *
     * @param orderId The UUID of the order to delete.
     */
    @Override
    public void deleteById(UUID orderId) {
        // Implementation needed
    }

    /**
     * Retrieves all completed orders' UUIDs.
     *
     * @return A set of UUIDs representing completed orders.
     */
    public Set<UUID> getCompletedOrders() {
        return completedOrders;
    }

    /**
     * Retrieves all prepared orders' UUIDs.
     *
     * @return A set of UUIDs representing prepared orders.
     */
    public Set<UUID> getPreparedOrders() {
        return preparedOrders;
    }
}
