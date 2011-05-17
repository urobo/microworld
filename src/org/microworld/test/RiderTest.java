/**
 * 
 */
package org.microworld.test;

import static org.junit.Assert.*;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.microworld.models.Location;
import org.microworld.models.Mode;
import org.microworld.models.Participation;
import org.microworld.models.Person;
import org.microworld.models.Preferences;
import org.microworld.models.Search;
import org.microworld.models.Trip;
import org.microworld.robots.BehavioralPatterns;
import org.microworld.robots.DriverAgent;
import org.microworld.robots.RiderAgent;
import org.microworld.robots.Robot;
import org.microworld.robots.Role;

/**
 * @author riccardo
 * 
 */
public class RiderTest {
	private static RiderAgent rider = new RiderAgent(1);
	private static DriverAgent driver = new DriverAgent(1);
	private static Person driverUser = new Person();
	private static Trip trip= new Trip();

	
	private static Person user = new Person();
	/**
	 * @param tripDriver 
	 * @param tripRider 
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Robot path = new Robot(BehavioralPatterns.getBehavioralPattern(BehavioralPatterns.STRAIGHT_LINE));
		path.setRole(Role.RIDER);
		
		user.setUsername("ridertest");
		user.setPassword("password");
		user.setEmail("ridertest@gmail.com");
		user.setPhone("12039013");
		user.setGender(Person.MALE);
		
		driverUser.setUsername("driverRiderTest");
		driverUser.setPassword("password");
		driverUser.setEmail("ridertestdriver@gmail.com");
		driverUser.setPhone("123123435435");
		driverUser.setGender(Person.FEMALE);
		
		driver.setUser(driverUser);
		rider.setUser(user);
		
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
		
		
		trip.setAuthor(driverUser);
		trip.setDestination(destination);
		trip.setOrigin(origin);
		trip.setExpires(new Date(System.currentTimeMillis()));
		trip.setMode(modality);
		trip.setPreferences(new Preferences());
		trip.setActive(false);
		
		rider.setTrip(trip);
		driver.setTrip(trip);
		
		driver.register(driver.getUser());
		rider.register(rider.getUser());
		driver.setTrip(driver.postTrip(driver.getTrip()));
		driver.activateTrip(driver.getTrip());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link org.microworld.robots.RiderAgent#searchTrip(org.microworld.models.Location, org.microworld.models.Location, org.microworld.models.Person)}
	 * .
	 */
	@Test
	public void testSearchTrip() {
		fail("Not supported by the server");
		Search result = rider.searchTrip(rider.getTrip().getOrigin(),rider.getTrip().getDestination(), rider.getUser());
		assertNotNull(result.getTrips());
		assertNotNull(result.getHref());
		assertEquals(result.getAuthor().getUsername(), rider.getUser().getUsername());
	}

	/**
	 * Test method for
	 * {@link org.microworld.robots.RiderAgent#postParticipation(org.microworld.models.Participation, org.microworld.models.Trip)}
	 * .
	 */
	@Test
	public void testPostParticipation() {
		Participation participation = new Participation(rider.getUser(),Participation.REQUESTED);
		Participation result = rider.postParticipation(participation, driver.getTrip());
		assertNotNull(result);
		assertNotNull(result.getHref());
		assertEquals(participation.getAuthor().getUsername(), result.getAuthor().getUsername());
	}

	/**
	 * Test method for
	 * {@link org.microworld.robots.RiderAgent#checkParticipationStatus()}.
	 */
	@Test
	public void testCheckParticipationStatus() {
		String status = rider.checkParticipationStatus();
		assertNotNull(status);
		assertTrue(status.equals(Participation.ACCEPTED)|| status.equals(Participation.REQUESTED) || status.equals(Participation.FINISHED) || status.equals(Participation.STARTED));
	}

	/**
	 * Test method for
	 * {@link org.microworld.robots.RiderAgent#cancelParticipation()}.
	 */
	@Test
	public void testCancelParticipation() {
		assertTrue(rider.cancelParticipation());
	}
	
	/**
	 * Test method for
	 * {@link org.microworld.robots.RiderAgent#startParticipation()}.
	 */
	@Test
	public void testStartParticipation() {
		driver.setTrip(driver.postTrip(driver.getTrip()));
		Participation participation = new Participation(rider.getUser(),Participation.REQUESTED);
		Participation result = rider.postParticipation(participation, driver.getTrip());
		assertNotNull(result);
		driver.acceptRideRequests(driver.checkRideRequests(driver.getTrip()));
		assertTrue(rider.startParticipation());
	}

	/**
	 * Test method for
	 * {@link org.microworld.robots.RiderAgent#finishParticipation()}.
	 */
	@Test
	public void testFinishParticipation() {
		assertTrue(rider.finishParticipation());
	}
	

	
	/**
	 * Test method for
	 * {@link org.microworld.robots.RiderAgent#checkTripStatus(org.microworld.models.Trip)}
	 * .
	 */
	@Test
	public void testCheckTripStatus() {
		fail("Operation not supported in the server");
	}

}
