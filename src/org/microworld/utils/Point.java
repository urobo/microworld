/**
 * 
 */
package org.microworld.utils;

/**
 * @author riccardo
 *
 */
public class Point {
	private double lat = 0;
	private double lng = 0;
	
	private String GeoRSSPoint;
	
	public Point (double lat,double lng){
		this.lat = lat;
		this.lng = lng;
		this.GeoRSSPoint = lat + " " + lng;
	}

	/**
	 * @return the lat
	 */
	public double getLat() {
		return lat;
	}

	/**
	 * @param lat the lat to set
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}

	/**
	 * @return the lng
	 */
	public double getLng() {
		return lng;
	}

	/**
	 * @param lng the lng to set
	 */
	public void setLng(double lng) {
		this.lng = lng;
	}

	/**
	 * @return the geoRSSPoint
	 */
	public String toGeoRSSPoint() {
		return GeoRSSPoint;
	}

	/**
	 * @param geoRSSPoint the geoRSSPoint to set
	 */
	public void setGeoRSSPoint(String geoRSSPoint) {
		GeoRSSPoint = geoRSSPoint;
	}
}
