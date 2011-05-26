/**
 * 
 */
package org.microworlds.scenario;

import java.util.Date;

import org.microworld.logging.Log;
import org.microworld.models.Location;
import org.microworld.models.Mode;
import org.microworld.models.Person;
import org.microworld.models.Preferences;
import org.microworld.models.Trip;
import org.microworld.robots.BehavioralPatterns;
import org.microworld.robots.DriverAgent;
import org.microworld.robots.RiderAgent;
import org.microworld.robots.Robot;
import org.microworld.robots.Role;
import org.microworld.utils.Point;

/**
 * @author riccardo
 *
 */
public class Scenario2 extends Scenario implements DycapoScenario{
	int agentCounter = 0;
	RiderAgent rider;
	Trip trip;

	@Override
	public void setUp() {
		Log.verbose("Scenario2 SetUp", "starting");
		rider = new RiderAgent(1.00);
		Robot path = new Robot(
				BehavioralPatterns
						.getBehavioralPattern(BehavioralPatterns.STRAIGHT_LINE));
		path.setRole(Role.RIDER);
		rider.setPath(path);

		Location origin = new Location();
		Location destination = new Location();

		origin.setLeaves(new Date(System.currentTimeMillis()));
		destination.setLeaves(new Date(System.currentTimeMillis()));

		origin.setPoint(Location.ORIG);
		destination.setPoint(Location.DEST);

		origin.setGeorss_point(path.getList().get(0).toGeoRSSPoint());
		destination.setGeorss_point(path.getList()
				.get(path.getList().size() - 1).toGeoRSSPoint());

		Mode modality = new Mode();
		modality.setLic("ASDFGH");
		modality.setKind("auto");
		modality.setCapacity(5);
		modality.setVacancy(4);
		modality.setMake("BMW");
		modality.setModel("M3");

		Person person = new Person();
		person.setUsername("mockuser2");
		person.setPassword(LOGIN_PASSWORD);
		person.setEmail("asdasdasd@asdasd.com");
		person.setPhone("1223334444555");
		person.setGender(Person.MALE);
		rider.setUser(person);
		Log.verbose("Scenario1", person.toString());
		
		trip = new Trip();
		trip.setAuthor(person);
		trip.setDestination(destination);
		trip.setOrigin(origin);
		trip.setExpires(new Date(System.currentTimeMillis()));
		trip.setMode(modality);
		trip.setPreferences(new Preferences());
		trip.setActive(false);

		rider.setTrip(trip);
		rider.register(rider.getUser());
		Point point = rider.getPath().getList().get(0);
		rider.updatePosition(Point.getPositionFromPoint(point.toGeoRSSPoint()));
		Log.verbose("Scenario1 SetUp",
				"finished ready to start the simulation ...");
	}

	@Override
	public void start() {
		Log.verbose("Scenario2 Start", "starting the scenario");

		rider.start();

		Log.verbose("Scenario2 Start", "Simulation began");
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void load(String resource) {
		// TODO Auto-generated method stub
		
	}

}
