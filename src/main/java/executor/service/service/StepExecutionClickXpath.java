package executor.service.service;

import executor.service.model.request.StepRequest;
import org.openqa.selenium.WebDriver;

public interface StepExecutionClickXpath extends StepExecution{

    String getStepAction();

    void step(WebDriver webDriver, StepRequest stepRequest);

}
