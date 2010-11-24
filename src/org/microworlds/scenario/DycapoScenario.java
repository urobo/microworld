/**
 * 
 */
package org.microworlds.scenario;

/**
 * @author riccardo
 * 
 */
public interface DycapoScenario {
	void setUp();

	void start();

	void stop();

	void load(String resource);
}
