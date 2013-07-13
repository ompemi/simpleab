package com.androidsx.simpleab;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SimpleAB {

    /** Map of of the test names and the choice option description chosen by our framework */
    private static Map<String, String> cachedChosenExperimentAlternatives = new HashMap<String, String>();

    /**
     * Execute randomly one of your altenatives of your {@link SimpleABExperiment}
     * 
     * @param experimentId An identifier for your experiment
     * @param experiment the experiment with the possible alternatives
     */
    public static void test(String experimentId, SimpleABExperiment experiment) {
        final boolean randomCondition = UUID.randomUUID().getLeastSignificantBits() % 2 == 0;
        final String chosenAlternativeId = (randomCondition) ? experiment.A() : experiment.B();

        // Cache the chosen alternative in case the goal is reached
        SimpleAB.cachedChosenExperimentAlternatives.put(experimentId, chosenAlternativeId);
    }

    /**
     * Retrieves which was the chosen alternative of your previous experiment and runs the {@code callback}, usually
     * called when the goal of your experiment has been reached.
     * 
     * @param experimentId Experiment identifier, should be one of the experiments you previously run with
     *            {@link #test(String, SimpleABExperiment)}
     * @param callback Callback with the action you want to perform based on the alternative chosen in your experiment
     */
    public static void goalReached(String experimentId, SimpleABGoalReachedCallback callback) {
        callback.measure(experimentId, SimpleAB.cachedChosenExperimentAlternatives.get(experimentId));
    }
}
