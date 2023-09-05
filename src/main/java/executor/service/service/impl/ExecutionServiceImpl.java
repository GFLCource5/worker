package executor.service.service.impl;

import executor.service.config.properties.PropertiesConfig;
import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.model.WebDriverConfig;
import executor.service.service.ExecutionService;
import executor.service.service.ScenarioExecutor;
import executor.service.service.WebDriverInitializer;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.BlockingQueue;

import static executor.service.config.properties.PropertiesConstants.*;

/**
 * The facade for execute ScenarioExecutor.
 *
 *  @author Oleksandr Tuleninov
 *  @version 01
 * */
public class ExecutionServiceImpl implements ExecutionService {

    private ScenarioExecutor scenarioExecutor;
    private PropertiesConfig propertiesConfig;
    private WebDriverConfig webDriverConfig;

    public ExecutionServiceImpl() {
    }

    public ExecutionServiceImpl(ScenarioExecutor scenarioExecutor,
                                PropertiesConfig propertiesConfig,
                                WebDriverConfig webDriverConfig) {
        this.scenarioExecutor = scenarioExecutor;
        this.propertiesConfig = propertiesConfig;
        this.webDriverConfig = webDriverConfig;
    }

    /**
     * Execute ScenarioExecutor.
     *
     * @param scenarios the queue with scenarios
     * @param proxies   the queue with proxies
     */
    @Override
    public void execute(BlockingQueue<Scenario> scenarios,
                        BlockingQueue<ProxyConfigHolder> proxies) {
        WebDriverConfig configuredWebDriverConfig = configureWebDriverConfig(propertiesConfig, webDriverConfig);

        while (true) {
            try {
                Scenario scenario = scenarios.take();
                ProxyConfigHolder proxy = proxies.take();

                WebDriver webDriver = getWebDriverPrototype(configuredWebDriverConfig, proxy);
                if (webDriver == null) continue;

                scenarioExecutor.execute(scenario, webDriver);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Configure WebDriverConfig from properties file.
     *
     * @param propertiesConfig the properties from resources file
     * @param webDriverConfig  the WebDriverConfig entity
     */
    private WebDriverConfig configureWebDriverConfig(PropertiesConfig propertiesConfig, WebDriverConfig webDriverConfig) {
        var properties = propertiesConfig.getProperties(WEB_DRIVER);
        var webDriverExecutable = properties.getProperty(WEB_DRIVER_EXECUTABLE);
        var userAgent = properties.getProperty(USER_AGENT);
        var pageLoadTimeout = Long.parseLong(properties.getProperty(PAGE_LOAD_TIMEOUT));
        var implicitlyWait = Long.parseLong(properties.getProperty(IMPLICITLY_WAIT));
        webDriverConfig.setWebDriverExecutable(webDriverExecutable);
        webDriverConfig.setUserAgent(userAgent);
        webDriverConfig.setPageLoadTimeout(pageLoadTimeout);
        webDriverConfig.setImplicitlyWait(implicitlyWait);

        return webDriverConfig;
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
