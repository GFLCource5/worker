package executor.service.service.impl;

import executor.service.model.Scenario;
import executor.service.model.Step;
import executor.service.service.ScenarioExecutor;
import executor.service.service.StepExecutionClickCss;
import executor.service.service.StepExecutionClickXpath;
import executor.service.service.StepExecutionSleep;
import org.openqa.selenium.WebDriver;

/**
 * Class for reading properties from properties file.
 *
 *  @author Oleksandr Tuleninov
 *  @version 01
 * */
public class ScenarioExecutorImpl implements ScenarioExecutor {

    private StepExecutionClickCss stepExecutionClickCss;
    private StepExecutionSleep stepExecutionSleep;
    private StepExecutionClickXpath stepExecutionClickXpath;

    public ScenarioExecutorImpl() {
    }

    public ScenarioExecutorImpl(StepExecutionClickCss stepExecutionClickCss,
                                StepExecutionSleep stepExecutionSleep,
                                StepExecutionClickXpath stepExecutionClickXpath) {
        this.stepExecutionClickCss = stepExecutionClickCss;
        this.stepExecutionSleep = stepExecutionSleep;
        this.stepExecutionClickXpath = stepExecutionClickXpath;
    }

    @Override
    public void execute(Scenario scenario, WebDriver webDriver) {
        for (Step step : scenario.getSteps()) {
            String action = step.getAction().getName();
            switch (action) {
                case "clickCss" -> stepExecutionClickCss.step(webDriver, step);
                case "sleep" -> stepExecutionClickXpath.step(webDriver, step);
                case "clickXpath" -> stepExecutionSleep.step(webDriver, step);
                default -> throw new IllegalArgumentException("Invalid step action: " + action);
            }
        }
    }
}
