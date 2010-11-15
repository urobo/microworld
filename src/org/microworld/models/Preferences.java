/**
 * 
 */
package org.microworld.models;

import org.json.JSONException;
import org.json.JSONObject;

import eu.fbk.dycapo.factories.json.DycapoJSONObjects;

/**
 * @author riccardo
 * 
 */
public class Preferences implements DycapoJSONObjects {
	public static final String TAG = "Preferences";

	/**
	 * @return the nonsmoking
	 */
	public Boolean getNonsmoking() {
		return nonsmoking;
	}

	/**
	 * @param nonsmoking
	 *            the nonsmoking to set
	 */
	public void setNonsmoking(Boolean nonsmoking) {
		this.nonsmoking = nonsmoking;
	}

	/**
	 * @return the gender
	 */
	public Integer getGender() {
		return gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(Integer gender) {
		this.gender = gender;
	}

	/**
	 * @return the drive
	 */
	public Boolean getDrive() {
		return drive;
	}

	/**
	 * @param drive
	 *            the drive to set
	 */
	public void setDrive(Boolean drive) {
		this.drive = drive;
	}

	/**
	 * @return the ride
	 */
	public Boolean getRide() {
		return ride;
	}

	/**
	 * @param ride
	 *            the ride to set
	 */
	public void setRide(Boolean ride) {
		this.ride = ride;
	}

	/**
	 * @return the pet
	 */
	public Boolean getPet() {
		return pet;
	}

	/**
	 * @param pet
	 *            the pet to set
	 */
	public void setPet(Boolean pet) {
		this.pet = pet;
	}

	public static final String[] GENDER_PREFS = { "M", "F", "B" };

	public static final int MALE = 0;
	public static final int FEMALE = 1;
	public static final int BOTH = 2;

	public static final String AGE = "age";
	public static final String NONSMOKING = "nonsmoking";
	public static final String GENDER = "gender";
	public static final String DRIVE = "drive";
	public static final String RIDE = "ride";
	public static final String PET = "pet";

	public static final String HREF = "href";

	private String age; // may
	private Boolean nonsmoking; // may
	private Integer gender; // may
	private Boolean drive; // may
	private Boolean ride; // may
	private Boolean pet; // may
	protected String href;

	/**
	 * @return the age
	 */
	public String getAge() {
		return age;
	}

	/**
	 * @param age
	 *            the age to set
	 */
	public void setAge(String age) {
		this.age = age;
	}

	public Preferences() {

	}

	/**
	 * @param age
	 * @param nonsmoking
	 * @param gender
	 * @param drive
	 * @param ride
	 */
	public Preferences(String age, Boolean nonsmoking, Integer gender,
			Boolean drive, Boolean ride) {
		this.age = age;
		this.nonsmoking = nonsmoking;
		this.gender = gender;
		this.drive = drive;
		this.ride = ride;
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
			if (this.age instanceof java.lang.String)
				result.put(Preferences.AGE, this.age);

			if (this.drive instanceof java.lang.Boolean)
				result.put(Preferences.DRIVE, this.drive.booleanValue());

			if (this.gender instanceof java.lang.Integer)
				result.put(Preferences.GENDER,
						Preferences.GENDER_PREFS[this.gender]);

			if (this.nonsmoking instanceof java.lang.Boolean)
				result.put(Preferences.NONSMOKING,
						this.nonsmoking.booleanValue());

			if (this.ride instanceof java.lang.Boolean)
				result.put(Preferences.RIDE, this.ride.booleanValue());

			if (this.pet instanceof Boolean)
				result.put(Preferences.PET, this.pet.booleanValue());

			return result;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
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

		if (this.age instanceof java.lang.String)
			out.append(AGE + " : " + this.age);

		if (this.drive instanceof java.lang.Boolean)
			out.append(DRIVE + " : " + this.drive.booleanValue());

		if (this.gender instanceof java.lang.Integer)
			out.append(GENDER + " : " + GENDER_PREFS[this.gender]);

		if (this.nonsmoking instanceof java.lang.Boolean)
			out.append(NONSMOKING + " : " + this.nonsmoking.booleanValue());

		if (this.ride instanceof java.lang.Boolean)
			out.append(RIDE + " : " + this.ride.booleanValue());

		if (this.pet instanceof Boolean)
			out.append(PET + " : " + this.pet.booleanValue());

		return out.toString();
	}

}
