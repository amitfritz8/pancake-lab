package org.pancakelab.domain.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * Utility class providing methods to execute actions with thread-safety
 * ensured by a lock.
 */
@Slf4j
public class LockUtils {

    /**
     * Executes the given action with thread-safety ensured by a lock.
     *
     * @param lock   The lock to ensure thread safety.
     * @param action The action to execute.
     */
    public static void executeWithLock(Lock lock, Runnable action) {
        boolean acquired = false;
        try {
            acquired = lock.tryLock(10, TimeUnit.SECONDS);
            if (acquired) {
                action.run();
            } else {
                log.warn("Could not acquire lock.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Thread was interrupted.", e);
        } finally {
            if (acquired) {
                lock.unlock();
            }
        }
    }

    /**
     * Executes the given action with thread-safety ensured by a lock.
     *
     * @param lock   The lock to ensure thread safety.
     * @param action The action to execute.
     * @param <T>    The return type of the action.
     * @return The result of the action, or null if the lock was not acquired.
     */
    public static <T> T executeWithLock(Lock lock, LockCallback<T> action) {
        boolean acquired = false;
        try {
            acquired = lock.tryLock(10, TimeUnit.SECONDS);
            if (acquired) {
                return action.run();
            } else {
                log.warn("Could not acquire lock.");
                return null;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Thread was interrupted.", e);
            return null;
        } finally {
            if (acquired) {
                lock.unlock();
            }
        }
    }

    /**
     * Functional interface for executing actions within a lock.
     *
     * @param <T> The return type of the action.
     */
    @FunctionalInterface
    public interface LockCallback<T> {
        T run();
    }
}
