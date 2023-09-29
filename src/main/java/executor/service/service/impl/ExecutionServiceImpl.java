package executor.service.service.impl;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.model.WebDriverConfig;
import executor.service.service.ExecutionService;
import executor.service.service.ScenarioExecutor;
import executor.service.service.WebDriverInitializer;
import org.openqa.selenium.WebDriver;

/**
 * The facade for execute ScenarioExecutor.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class ExecutionServiceImpl implements ExecutionService {

    private final ScenarioExecutor scenarioExecutor;
    private final WebDriverConfig webDriverConfig;
    private final WebDriverInitializer webDriverInitializer;

    public ExecutionServiceImpl(ScenarioExecutor scenarioExecutor,
                                WebDriverConfig webDriverConfig, WebDriverInitializer webDriverInitializer) {
        this.scenarioExecutor = scenarioExecutor;
        this.webDriverConfig = webDriverConfig;
        this.webDriverInitializer = webDriverInitializer;
    }

    /**
     * Execute ScenarioExecutor.
     *
     * @param scenario the scenario
     * @param proxy    the proxy
     */
    @Override
    public void execute(Scenario scenario, ProxyConfigHolder proxy) {
        WebDriver webDriver = getWebDriverPrototype(webDriverConfig, proxy);

        scenarioExecutor.execute(scenario, webDriver);
    }

    /**
     * Get WebDriver as Prototype.
     *
     * @param webDriverConfig   the WebDriverConfig entity
     * @param proxyConfigHolder the ProxyConfigHolder entity
     */
    private WebDriver getWebDriverPrototype(WebDriverConfig webDriverConfig, ProxyConfigHolder proxyConfigHolder) {
        return webDriverInitializer.getInstance(webDriverConfig, proxyConfigHolder);
    }
}
