simpleab
========

Uber-simple AB testing framework (for Android). Provides a simple way to add AB testing into your android apps, agnostic to any metrics engine. 

Motivation
-----------
Simple way to check the performance of few buttons and texts in several Android apps, using Flurry as our metrics engine. For any advanced AB testing framework, use arise.io.

Limitations
-----------
 * It does not measure the number of times you show the AB test to the user, it only provides a callback when the goal is reached.
 * Only 2 alternatives for the moment
 * No dynamic data-driven tests
 * The integration with any metrics engine such as Flurry, Mixpanel, etc. is on you.

Usage
-----------
For running an AB test you need to provide an experiment identifier. The output of each experiment alternative is a name that describes it, it will be the identifier you persist as the option chosen by the user: 


```java
final Button goProButton = (Button) findViewById(R.id.btn_go_pro);

SimpleAB.test("AB Button Text Experiment", new SimpleABExperiment() {
  
	@Override
	public String A() {
		goProButton.setText("Go Pro");
		
		return "go_pro";
	}
	
	@Override
	public String B() {
		goProButton.setText("Unlock features");
		
		return "unlock_features";
	}
	
});
```

Whenever the goal is reached, just call SimpleAB.goalReached with the experiment identifier and provide a callback. Here you have an example using Flurry, where in order to preserve funnels we create a new event called "AB Go Pro Button" with one parameter called "AB Button Text Experiment".

```java
SimpleAB.goalReached("AB Button Text Experiment", new SimpleABGoalReachedCallback() {

		@Override
		public void measure(String experimentId, String alternativeId) {
            final Map<String, String> params = new HashMap<String, String>();
            params.put(experimentId, alternativeId);
            
            FlurryAgent.logEvent("My AB event in Flurry", params);
		}
	});

```
That's it, you will have a nice pie chart in flurry with the alternatives chosen by the user.

Integration
-----------

Add the jar from Releases tab in github into the libs folder of your android project, and add it to your build path.

