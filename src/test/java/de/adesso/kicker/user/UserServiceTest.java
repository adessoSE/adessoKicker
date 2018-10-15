package de.adesso.kicker.user;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserServiceTest {

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
		
		service.getUserByName("Jan", "Schneider");
	}

}
