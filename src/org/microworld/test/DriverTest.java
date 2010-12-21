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
import org.microworld.models.Person;
import org.microworld.models.Preferences;
import org.microworld.models.Trip;
import org.microworld.robots.Agent;
import org.microworld.robots.BehavioralPatterns;
import org.microworld.robots.DriverAgent;
import org.microworld.robots.Robot;

/**
 * @author riccardo
 *
 */
public class DriverTest {
	private static DriverAgent driver = new DriverAgent(1);
	private static Trip trip = new Trip();
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		Robot path = new Robot(BehavioralPatterns.getBehavioralPattern(BehavioralPatterns.STRAIGHT_LINE));
		path.setRole(Robot.DRIVER);
		driver.setPath(path);
		
		Location origin = new Location();
		Location destination = new Location();
		
		origin.setLeaves(new Date(System.currentTimeMillis()));
		destination.setLeaves(new Date(System.currentTimeMillis()));
		
		origin.setPoint(Location.ORIG);
		destination.setPoint(Location.DEST);
		
		origin.setGeorss_point(path.getList().get(0).toGeoRSSPoint());
		destination.setGeorss_point(path.getList().get(path.getList().size()-1).toGeoRSSPoint());
		
		Mode modality = new Mode();
		modality.setLic("ASDFGH");
		modality.setKind("auto");
		modality.setCapacity(5);
		modality.setVacancy(4);
		modality.setMake("BMW");
		modality.setModel("M3");
		
		Person person = new Person();
		person.setUsername("agent01drivertest");
		person.setPassword("password");
		person.setEmail("agent01test@asd.com");
		person.setPhone("122333444466");
		driver.setUser(person);
		
		
		trip = new Trip();
		trip.setAuthor(person);
		trip.setDestination(destination);
		trip.setOrigin(origin);
		trip.setExpires(new Date(System.currentTimeMillis()));
		trip.setMode(modality);
		trip.setPreferences(new Preferences());
		trip.setActive(false);
		
		driver.setTrip(trip);
		driver.register(driver.getUser());
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
	 * Test method for {@link org.microworld.robots.DriverAgent#postTrip(org.microworld.models.Trip)}.
	 */
	@Test
	public void testPostTrip() {
		Trip result = driver.postTrip(trip);
		assertNotNull(result);
		assertTrue(result.equals(trip));
	}

	/**
	 * Test method for {@link org.microworld.robots.DriverAgent#activateTrip(org.microworld.models.Trip)}.
	 */
	@Test
	public void testActivateTrip() {
		driver.postTrip(trip);
		assertTrue(driver.activateTrip(trip));
	}

	/**
	 * Test method for {@link org.microworld.robots.DriverAgent#checkRideRequests(org.microworld.models.Trip)}.
	 */
	@Test
	public void testCheckRideRequests() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.microworld.robots.DriverAgent#acceptRideRequests(java.util.List)}.
	 */
	@Test
	public void testAcceptRideRequests() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.microworld.robots.DriverAgent#finishTrip(org.microworld.models.Trip)}.
	 */
	@Test
	public void testFinishTrip() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.microworld.robots.DriverAgent#refuseRideRequests(java.util.List)}.
	 */
	@Test
	public void testRefuseRideRequests() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.microworld.robots.DriverAgent#fetchRideRequests(java.util.List)}.
	 */
	@Test
	public void testFetchRideRequests() {
		fail("Not yet implemented");
	}

}
