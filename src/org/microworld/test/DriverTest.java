/**
 * 
 */
package org.microworld.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.microworld.logging.Log;
import org.microworld.models.Location;
import org.microworld.models.Mode;
import org.microworld.models.Participation;
import org.microworld.models.Person;
import org.microworld.models.Preferences;
import org.microworld.models.Trip;
import org.microworld.robots.Agent;
import org.microworld.robots.BehavioralPatterns;
import org.microworld.robots.DriverAgent;
import org.microworld.robots.RiderAgent;
import org.microworld.robots.Robot;

/**
 * @author riccardo
 *
 */
public class DriverTest {
	private static DriverAgent driver = new DriverAgent(1);
	private static Trip trip = new Trip();
	static Person person = new Person();
	
	private String[] reqsUN ={"request1","request2","request3"};
	private List<RiderAgent> requesters = new ArrayList<RiderAgent>();
	private List<Participation> participations = new ArrayList<Participation>();
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
		
		
		person.setUsername("drivertest");
		person.setPassword("password");
		person.setGender(Person.FEMALE);
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
		Log.verbose("driverTest", driver.getUser().toString());
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
		Log.verbose("driverTest", "trip : " +  trip.toVerboseString());
		Trip result = driver.postTrip(trip);
		assertNotNull(result);
		assertTrue(result instanceof Trip);
		trip = result;
	}

	/**
	 * Test method for {@link org.microworld.robots.DriverAgent#activateTrip(org.microworld.models.Trip)}.
	 */
	@Test
	public void testActivateTrip() {
//		if (!(this.trip.getHref() instanceof String))
//			this.driver.postTrip(trip);
//		Log.verbose("driverTest", "Trip HREF : "+ trip.getHref());
//		assertTrue(driver.activateTrip(trip));
		fail("feature non supported on the server");
	}

	/**
	 * Test method for {@link org.microworld.robots.DriverAgent#checkRideRequests(org.microworld.models.Trip)}.
	 */
	@Test
	public void testCheckRideRequests() {
		driver.activateTrip(trip);
		for (int i =0;i<3;i++){
			this.requesters.add(new RiderAgent(new Person(this.reqsUN[i], "password", "request" + this.reqsUN[i] + "@gmail.com", String.valueOf(i*100000),
			Person.MALE),1));
			Log.verbose("checkRideRequests", this.requesters.get(i).getUser().toString());
			this.requesters.get(i).register(this.requesters.get(i).getUser());
			this.requesters.get(i).postParticipation(new Participation(this.requesters.get(i).getUser(),Participation.REQUESTED), trip);
		}
		List<Participation> list = driver.checkRideRequests(trip);
		assertEquals(list.size(), this.requesters.size());
		for(int i = 0; i<list.size();i++)
			for (int j=0; j<this.requesters.size();j++)
				if(list.get(i).getAuthor().getUsername().equals(this.requesters.get(j).getUser().getUsername()))
					assertEquals(list.get(i).getHref(),this.requesters.get(j).getParticipation().getHref());
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
