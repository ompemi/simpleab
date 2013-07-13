package com.androidsx.simpleab;

public interface SimpleABExperiment {
    
    /** @return the alternative identifier of your experiment */
	public String A();
	
	/** @see #A */
	public String B();
}
