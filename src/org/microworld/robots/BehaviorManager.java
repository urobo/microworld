/**
 * 
 */
package org.microworld.robots;

import java.util.List;

import org.microworld.utils.Point;

/**
 * @author riccardo
 *
 */
public interface BehaviorManager {
	public String getNextPoint();
	public int size();
	public boolean hasNext();
	public void setPattern(List<Point> path);
	public int pointsLeft();
}
