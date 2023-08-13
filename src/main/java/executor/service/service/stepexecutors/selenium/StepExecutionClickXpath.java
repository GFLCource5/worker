package executor.service.service.stepexecutors.selenium;

import executor.service.model.entity.Step;
import executor.service.service.stepexecutors.StepExecution;
import org.openqa.selenium.WebDriver;

public interface StepExecutionClickXpath extends StepExecution {

    String getStepAction();

    void step(WebDriver webDriver, Step step);

}
