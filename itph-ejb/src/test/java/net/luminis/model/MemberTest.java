package net.luminis.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MemberTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void nameShouldBeSet(){
		Member sut = new Member(); //sut staat voor system under test
		
		//when
		sut.setName("Ted");
		
		//then
		assertEquals("Ted", sut.getName());
	}


}
