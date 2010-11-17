/**
 * 
 */
package org.microworld.robots;

import java.util.ArrayList;
import java.util.List;

import org.microworld.utils.Point;

/**
 * @author riccardo
 *
 */
public class Robot implements BehaviorManager {
	private List<Point> list;
	private int currentPoint = -1;
	
	public Robot (){
		this.list = new ArrayList<Point>();
	}
	public Robot (List<Point> path){
		this.setList(path);
	}

	@Override
	public String getNextPoint() {
		if (this.list != null && (!this.list.isEmpty())){
			this.currentPoint++;
			return this.list.get(currentPoint).toGeoRSSPoint();
		}
		return null;
	}

	@Override
	public int size() {
		return this.list.size();
	}

	@Override
	public boolean hasNext() {
		if(this.currentPoint < this.list.size()-1)
			return true;
		else return false;
	}

	@Override
	public void setPattern(List<Point> path) {
		this.setList(path);
	}

	/**
	 * @param list the list to set
	 */
	protected void setList(List<Point> list) {
		this.list = list;
	}

	/**
	 * @return the list
	 */
	protected List<Point> getList() {
		return list;
	}
	
	@Override
	public int pointsLeft() {
		return this.list.size() - this.currentPoint;
	}

}
