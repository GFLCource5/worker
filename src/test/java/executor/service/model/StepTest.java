package executor.service.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class StepTest {

    private static final StepTypes DEFAULT_ACTION = StepTypes.SLEEP;
    private static final String DEFAULT_VALUE = "Default value";
    private static final StepTypes ANOTHER_ACTION = StepTypes.CLICK_CSS;;
    private static final String ANOTHER_VALUE = "Another value";

    @Test
    public void testNoArgsConstructor() {
        Step step = new Step();

        assertNotNull(step);
    }

    @Test
    public void testAllArgsConstructor() {
        Step step = prepareStep();

        assertNotNull(step);
    }

    @Test
    public void testSetters() {
        Step step = new Step();

        step.setAction(DEFAULT_ACTION);
        step.setValue(DEFAULT_VALUE);


        assertNotNull(step.getAction());
        assertNotNull(step.getValue());
    }

    @Test
    public void testSameObjectsEquals() {
        Step step = prepareStep();
        Step sameStep = prepareStep();

        assertEquals(step, sameStep);
    }

    @Test
    public void testDifferentObjectsNotEquals() {
        Step step = prepareStep();
        Step anotherStep = prepareAnotherStep();

        assertNotEquals(step, anotherStep);
    }

    @Test
    public void testSameObjectsHashCode() {
        Step step = prepareStep();
        Step sameStep = prepareStep();

        assertEquals(step.hashCode(), sameStep.hashCode());
    }

    @Test
    public void testDifferentObjectsHashCode() {
        Step step = prepareStep();
        Step anotherStep = prepareAnotherStep();

        assertNotEquals(step.hashCode(), anotherStep.hashCode());
    }

    private Step prepareStep() {
        return new Step(DEFAULT_ACTION, DEFAULT_VALUE);
    }

    private Step prepareAnotherStep() {
        return new Step(ANOTHER_ACTION, ANOTHER_VALUE);
    }
}