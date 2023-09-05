package executor.service.service.parallel;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.service.impl.ExecutionServiceImpl;

import java.util.Queue;

/**
 * Execution task.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class ExecutionWorker implements Runnable {

    private final ExecutionServiceImpl service;
    private final Queue<Scenario> scenarioQueue;
    private final Queue<ProxyConfigHolder> proxyQueue;

    public ExecutionWorker(ExecutionServiceImpl service,
                           Queue<Scenario> scenarioQueue,
                           Queue<ProxyConfigHolder> proxyQueue) {
        this.service = service;
        this.scenarioQueue = scenarioQueue;
        this.proxyQueue = proxyQueue;
    }

    @Override
    public void run() {
        service.execute(scenarioQueue, proxyQueue);
    }
}
