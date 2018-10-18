package de.adesso.kicker.user;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.adesso.masterTest.MasterTest;
import junit.framework.TestCase;
import junit.framework.TestResult;

public class UserServiceTest extends TestCase{

	@InjectMocks
	private UserService service;
	
	@Mock
	private UserRepository rep;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void test_GetUserByName() {
		
		TestResult result = this.createResult();
		MasterTest.printTestResult(this, result);
		service.getUserByNameSearchbar("Jan", "Schneider");
	}

}
