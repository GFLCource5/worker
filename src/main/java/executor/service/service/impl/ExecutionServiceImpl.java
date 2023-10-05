package executor.service.service.impl;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.model.WebDriverConfig;
import executor.service.service.ExecutionService;
import executor.service.service.ScenarioExecutor;
import executor.service.service.WebDriverInitializer;
import org.openqa.selenium.WebDriver;

/**
 * The {@code ExecutionServiceImpl} class implements the {@link ExecutionService} interface and
 * is responsible for executing scenarios using a WebDriver.
 * It utilizes a {@link ScenarioExecutor} to perform the execution and relies on {@link WebDriverConfig} and
 * {@link WebDriverInitializer} for configuring and initializing WebDriver instances.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see executor.service.service.ScenarioExecutor
 * @see executor.service.model.WebDriverConfig
 * @see executor.service.service.WebDriverInitializer
 */
public class ExecutionServiceImpl implements ExecutionService {

    private final ScenarioExecutor scenarioExecutor;
    private final WebDriverConfig webDriverConfig;
    private final WebDriverInitializer webDriverInitializer;

    public ExecutionServiceImpl(ScenarioExecutor scenarioExecutor,
                                WebDriverConfig webDriverConfig,
                                WebDriverInitializer webDriverInitializer) {
        this.scenarioExecutor = scenarioExecutor;
        this.webDriverConfig = webDriverConfig;
        this.webDriverInitializer = webDriverInitializer;
    }

    /**
     * Executes a scenario using the provided WebDriver and optional proxy configuration.
     *
     * @param scenario the scenario
     * @param proxy    the proxy
     */
    @Override
    public void execute(Scenario scenario, ProxyConfigHolder proxy) {
        WebDriver webDriver = getWebDriver(webDriverConfig, proxy);

        scenarioExecutor.execute(scenario, webDriver);
    }

    /**
     * Retrieves a WebDriver instance as a prototype.
     *
     * @param webDriverConfig   the WebDriverConfig entity
     * @param proxyConfigHolder the ProxyConfigHolder entity
     */
    private WebDriver getWebDriver(WebDriverConfig webDriverConfig, ProxyConfigHolder proxyConfigHolder) {
        return webDriverInitializer.getInstance(webDriverConfig, proxyConfigHolder);
    }
}
