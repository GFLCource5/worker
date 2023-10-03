package executor.service.service.impl;

import executor.service.config.properties.PropertiesConfig;
import executor.service.config.properties.PropertiesConstants;
import executor.service.model.Scenario;
import executor.service.service.ItemHandler;
import executor.service.service.ScenarioProvider;
import executor.service.service.ScenarioSourceListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

/**
 * The {@code ScenarioSourceListenerImpl} class implements the {@link ScenarioSourceListener} interface
 * that reads scenarios from a {@link ScenarioProvider}
 * and emits them as a {@link Flux} stream with a specified delay.
 * <p>
 *
 * @author Yurii Kotsiuba, Oleksandr Tuleninov, Oleksii Bondarenko
 * @version 01
 * @see executor.service.service.ScenarioProvider
 * @see executor.service.config.properties.PropertiesConfig
 */
public class ScenarioSourceListenerImpl implements ScenarioSourceListener {
    private final ScenarioProvider provider;
    private final PropertiesConfig propertiesConfig;
    private static final Logger log = LoggerFactory.getLogger(ScenarioSourceListener.class);

    public ScenarioSourceListenerImpl(ScenarioProvider provider,
                                      PropertiesConfig propertiesConfig) {
        this.provider = provider;
        this.propertiesConfig = propertiesConfig;
    }

    /**
     * Executes the scenario source listener by reading scenarios from the {@link ScenarioProvider},
     * validating them, and emitting them as a continuous stream using a {@link Flux}.
     *
     * @param handler The {@link ItemHandler} to handle received scenarios.
     */
    @Override
    public void execute(ItemHandler handler) {
        List<Scenario> scenarios = provider.readScenarios();
        validateScenarios(scenarios);
        Flux<Scenario> scenariosFlux = getScenarioFlux(scenarios);
        scenariosFlux.subscribe(handler::onItemReceived);
    }

    /**
     * Check the list of ProxyConfigHolder entities.
     *
     * @param scenarios list of Scenario entities
     */
    private void validateScenarios(List<Scenario> scenarios) {
        if (scenarios == null || scenarios.isEmpty()) {
            log.error("The scenarios list is bad.");
        }
    }

    /**
     * Retrieves a {@link Flux} stream with {@link Scenario} entities continuously, applying a delay between emissions.
     *
     * @param scenarios The list of {@link Scenario} entities to emit.
     * @return A {@link Flux} stream emitting scenarios with the specified delay.
     */
    private Flux<Scenario> getScenarioFlux(List<Scenario> scenarios) {
        return Flux.fromIterable(scenarios)
                .log()
                .delayElements(Duration.ofSeconds(getDelay()))
                .repeat();
    }

    private Long getDelay() {
        Properties properties = propertiesConfig.getProperties(PropertiesConstants.SOURCES_PROPERTIES);
        return Long.parseLong(properties.getProperty(PropertiesConstants.DELAY_SCENARIO_SECONDS));
    }
}
