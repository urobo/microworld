/**
 * 
 */
package org.microworld.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import eu.fbk.dycapo.factories.json.DycapoJSONObjects;

/**
 * @author riccardo
 * 
 */

public class Trip implements DycapoJSONObjects {
	public static final String TAG = "Trip";

	public static final String ID = "id";
	public static final String PUBLISHED = "published";
	public static final String UPDATED = "updated";
	public static final String EXPIRES = "expires";
	public static final String CONTENT = "content";
	public static final String AUTHOR = "author";
	public static final String MODE = "modality";
	public static final String PREFERENCES = "preferences";
	public static final String LOCATIONS = "locations";

	public static final String HREF = "href";

	public static final String ACTIVE = "active";

	protected Integer id; // if daniel puts it inside the trip
	protected Boolean active; // must
	protected Date published; // may
	protected Date updated; // should
	protected Date expires; // must
	protected Person author; // must
	protected Mode mode; // must
	protected Preferences preferences; // must
	protected Location origin; // must
	protected Location destination; // must
	protected ArrayList<Location> waypoints; // must
	protected String href;

	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * @param expires
	 * @param author
	 * @param content
	 */
	public Trip(Date expires, Person author, Mode mode,
			Preferences preferences, Location origin, Location destination) {
		this.expires = expires;
		this.author = author;
		this.destination = destination;
		this.origin = origin;
		this.mode = mode;
		this.preferences = preferences;
	}

	public Trip() {

	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the published
	 */
	public Date getPublished() {
		return published;
	}

	/**
	 * @param published
	 *            the published to set
	 */
	public void setPublished(Date published) {
		this.published = published;
	}

	/**
	 * @return the updated
	 */
	public Date getUpdated() {
		return updated;
	}

	/**
	 * @param updated
	 *            the updated to set
	 */
	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	/**
	 * @return the expires
	 */
	public Date getExpires() {
		return expires;
	}

	/**
	 * @param expires
	 *            the expires to set
	 */
	public void setExpires(Date expires) {
		this.expires = expires;
	}

	/**
	 * @return the author
	 */
	public Person getAuthor() {
		return author;
	}

	/**
	 * @param author
	 *            the author to set
	 */
	public void setAuthor(Person author) {
		this.author = author;
	}

	/**
	 * @return the mode
	 */
	public Mode getMode() {
		return mode;
	}

	/**
	 * @param mode
	 *            the mode to set
	 */
	public void setMode(Mode mode) {
		this.mode = mode;
	}

	/**
	 * @return the preferences
	 */
	public Preferences getPreferences() {
		return preferences;
	}

	/**
	 * @param preferences
	 *            the preferences to set
	 */
	public void setPreferences(Preferences preferences) {
		this.preferences = preferences;
	}

	/**
	 * @return the origin
	 */
	public Location getOrigin() {
		return origin;
	}

	/**
	 * @param origin
	 *            the origin to set
	 */
	public void setOrigin(Location origin) {
		this.origin = origin;
	}

	/**
	 * @return the destination
	 */
	public Location getDestination() {
		return destination;
	}

	/**
	 * @param destination
	 *            the destination to set
	 */
	public void setDestination(Location destination) {
		this.destination = destination;
	}

	/**
	 * @return the waypoints
	 */
	public ArrayList<Location> getWaypoints() {
		return waypoints;
	}

	/**
	 * @param waypoints
	 *            the waypoints to set
	 */
	public void setWaypoints(ArrayList<Location> waypoints) {
		this.waypoints = waypoints;
	}

	/**
	 * @return the href
	 */
	public String getHref() {
		return href;
	}

	/**
	 * @param href
	 *            the href to set
	 */
	public void setHref(String href) {
		this.href = href;
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject result = new JSONObject();
		try {
			if (this.active instanceof Boolean)
				result.put(Trip.ACTIVE, this.active.booleanValue());

			if (this.author instanceof Person)
				result.put(Trip.AUTHOR, this.author.toJSONObject());

			JSONArray locations = new JSONArray();

			if (this.origin instanceof Location)
				locations.put(this.origin.toJSONObject());

			if (this.destination instanceof Location)
				locations.put(this.destination.toJSONObject());

			if (this.mode instanceof Mode)
				result.put(Trip.MODE, this.mode.toJSONObject());

			if (this.preferences instanceof Preferences)
				result.put(Trip.PREFERENCES, this.preferences.toJSONObject());

			if (this.waypoints instanceof ArrayList<?>) {
				int size = this.waypoints.size();

				for (int i = 0; i < size; i++)
					locations.put(this.waypoints.get(i).toJSONObject());

			}
			result.put(Trip.LOCATIONS, locations);

			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss");

			if (this.expires instanceof java.util.Date) {
				result.put(Trip.EXPIRES, formatter.format(this.expires));

			}

			if (this.published instanceof java.util.Date)
				result.put(Trip.PUBLISHED, formatter.format(this.published));

			if (this.updated instanceof java.util.Date)
				result.put(Trip.UPDATED, formatter.format(this.updated));

			return result;

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();

		if (this.id instanceof Integer)
			out.append(ID + " : " + this.id.toString() + "\n");

		if (this.author instanceof Person)
			out.append(AUTHOR + " : " + this.author.getUsername() + "\n");

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		if (this.published instanceof java.util.Date)
			out.append(PUBLISHED + " : " + formatter.format(this.published)
					+ "\n");

		if (this.expires instanceof java.util.Date) {
			out.append(EXPIRES + " : " + formatter.format(this.expires) + "\n");

		}

		return out.toString();
	}

	public String toVerboseString() {
		StringBuilder out = new StringBuilder();

		if (this.author instanceof Person)
			out.append(AUTHOR + " : " + this.author.toString() + "\n");

		if (this.origin instanceof Location)
			out.append("origin : " + this.origin.toString() + "\n");

		if (this.destination instanceof Location)
			out.append("destination : " + this.destination.toString() + "\n");

		if (this.mode instanceof Mode)
			out.append(MODE + " : " + this.mode.toString() + "\n");

		if (this.preferences instanceof Preferences)
			out.append(PREFERENCES + " : " + this.preferences.toString() + "\n");

		if (this.waypoints instanceof ArrayList<?>) {
			int size = this.waypoints.size();

			for (int i = 0; i < size; i++)
				out.append("waypoint : " + this.waypoints.toString() + "\n");

		}

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		if (this.expires instanceof java.util.Date)
			out.append(EXPIRES + " : " + formatter.format(this.expires));

		if (this.published instanceof java.util.Date)
			out.append(PUBLISHED + " : " + formatter.format(this.published)
					+ "\n");

		if (this.updated instanceof java.util.Date)
			out.append(UPDATED + " : " + formatter.format(this.updated) + "\n");
		return out.toString();
	}
}
