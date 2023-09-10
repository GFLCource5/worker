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

    private ScenarioExecutor scenarioExecutor;
    private WebDriverConfig webDriverConfig;

    public ExecutionServiceImpl() {
    }

    public ExecutionServiceImpl(ScenarioExecutor scenarioExecutor,
                                WebDriverConfig webDriverConfig) {
        this.scenarioExecutor = scenarioExecutor;
        this.webDriverConfig = webDriverConfig;
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
        if (webDriver == null) return;

        scenarioExecutor.execute(scenario, webDriver);
    }

    /**
     * Get WebDriver as Prototype.
     *
     * @param webDriverConfig   the WebDriverConfig entity
     * @param proxyConfigHolder the ProxyConfigHolder entity
     */
    private WebDriver getWebDriverPrototype(WebDriverConfig webDriverConfig, ProxyConfigHolder proxyConfigHolder) {
        WebDriverInitializer webDriverInitializer = new WebDriverInitializerImpl();
        return webDriverInitializer.getInstance(webDriverConfig, proxyConfigHolder);
    }
}
