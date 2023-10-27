package executor.service.service.impl.proxy;

import executor.service.model.ProxyConfigHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The {@code ProxySourceQueue} class represents a thread-safe queue for storing
 * and retrieving instances of {@link ProxyConfigHolder}.
 * It provides methods for putting and getting proxy configurations.
 * <p>
 * This class uses a blocking queue internally to ensure thread safety when
 * adding and retrieving proxy configurations.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see BlockingQueue
 * @see InterruptedException
 * @see RuntimeException
 */
@Component
public class ProxySourceQueue {

    private static final Logger log = LoggerFactory.getLogger(ProxySourceQueue.class);

    private final BlockingQueue<ProxyConfigHolder> queue;

    public ProxySourceQueue() {
        queue = new LinkedBlockingQueue<>();
    }

    /**
     * Put a proxy configuration into the queue.
     *
     * @param proxy The proxy configuration to be added to the queue.
     */
    public void putProxy(ProxyConfigHolder proxy) {
        try {
            queue.put(proxy);
        } catch (InterruptedException e) {
            try {
                log.error("Thread '" + "'" + Thread.currentThread().getName() + " was interrupted");
                queue.put(proxy);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * Get a proxy configuration from the queue.
     *
     * @return The proxy configuration retrieved from the queue.
     */
    public ProxyConfigHolder getProxy() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            log.error("Thread '" + "'" + Thread.currentThread().getName() + " was interrupted");
            try {
                return queue.take();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
