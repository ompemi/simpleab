package com.androidsx.simpleab;

import junit.framework.Assert;

import org.junit.Test;

public class SimpleABTest {

    @Test
    public void oneAndOnlyOneChoiceIsAlwaysExecuted() {
        DummySimpleABExperiment experiment = new DummySimpleABExperiment();
        SimpleAB.test("testingSimpleAB", experiment);

        Assert.assertEquals(experiment.alternativesExecuted, 1);
    }

    @Test
    public void correctChoiceIsInjectedWhenGoalReached() {
        DummySimpleABExperiment experiment = new DummySimpleABExperiment();
        SimpleAB.test("testingSimpleAB", experiment);

        DummySimpleABGoalReachedCallback callback = new DummySimpleABGoalReachedCallback();
        SimpleAB.goalReached("testingSimpleAB", callback);

        Assert.assertEquals(experiment.alternativeChosen, callback.alternativeChosen);
    }

    @Test
    public void multipleExperimentsDoesNotInterferGoalReached() {
        DummySimpleABExperiment experiment1 = new DummySimpleABExperiment();
        SimpleAB.test("experimentingSimpleAB1", experiment1);

        DummySimpleABExperiment experiment2 = new DummySimpleABExperiment();
        SimpleAB.test("experimentingSimpleAB2", experiment2);

        DummySimpleABExperiment experiment3 = new DummySimpleABExperiment();
        SimpleAB.test("experimentingSimpleAB3", experiment3);

        DummySimpleABGoalReachedCallback callback1 = new DummySimpleABGoalReachedCallback();
        SimpleAB.goalReached("experimentingSimpleAB1", callback1);

        DummySimpleABGoalReachedCallback callback2 = new DummySimpleABGoalReachedCallback();
        SimpleAB.goalReached("experimentingSimpleAB2", callback2);

        DummySimpleABGoalReachedCallback callback3 = new DummySimpleABGoalReachedCallback();
        SimpleAB.goalReached("experimentingSimpleAB3", callback3);

        Assert.assertEquals(experiment1.alternativeChosen, callback1.alternativeChosen);
        Assert.assertEquals(experiment2.alternativeChosen, callback2.alternativeChosen);
        Assert.assertEquals(experiment3.alternativeChosen, callback3.alternativeChosen);
    }

    /*
     * Instead of using mockito (another dependency to these simple tests), we just provide an implementation with some
     * markers on it for later testing
     */
    private final class DummySimpleABExperiment implements SimpleABExperiment {
        int alternativesExecuted = 0;
        String alternativeChosen;

        @Override
        public String A() {
            alternativesExecuted++;
            alternativeChosen = "Option A";

            return alternativeChosen;
        }

        @Override
        public String B() {
            alternativesExecuted++;
            alternativeChosen = "Option B";

            return alternativeChosen;
        }
    }

    private final class DummySimpleABGoalReachedCallback implements SimpleABGoalReachedCallback {
        String alternativeChosen = null;

        @Override
        public void measure(String experimentId, String alternativeId) {
            alternativeChosen = alternativeId;
        }
    }

}
