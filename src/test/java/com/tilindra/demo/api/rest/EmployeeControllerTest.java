package com.tilindra.demo.api.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tilindra.demo.Application;
import com.tilindra.demo.api.rest.EmployeeController;
import com.tilindra.demo.domain.Employee;

import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.regex.Pattern;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = Application.class)
@Profile("test")
public class EmployeeControllerTest {

	private static final String RESOURCE_LOCATION_PATTERN = "http://localhost/demo/createEmployee/[0-9]+";

	@InjectMocks
	EmployeeController controller;

	@Autowired
	WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void initTests() {
		MockitoAnnotations.initMocks(this);
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void shouldCreateRetrieveDelete() throws Exception {
		Employee r1 = mockEmployee("shouldCreateRetrieveDelete");
		byte[] r1Json = toJson(r1);

		// CREATE
		MvcResult result = mvc
				.perform(post("/demo/createEmployee").content(r1Json).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(redirectedUrlPattern(RESOURCE_LOCATION_PATTERN)).andReturn();
		long id = getResourceIdFromUrl(result.getResponse().getRedirectedUrl());

		// RETRIEVE
		mvc.perform(get("/demo/employee/" + id).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is((int) id))).andExpect(jsonPath("$.name", is(r1.getName())))
				.andExpect(jsonPath("$.city", is(r1.getCity())))
				.andExpect(jsonPath("$.description", is(r1.getDescription())));

		// DELETE
		mvc.perform(delete("/demo/employee/" + id)).andExpect(status().isNoContent());

		// RETRIEVE should fail
		mvc.perform(get("/demo/employee/" + id).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());

	}

	@Test
	public void shouldCreateAndUpdateAndDelete() throws Exception {
		Employee r1 = mockEmployee("shouldCreateAndUpdate");
		byte[] r1Json = toJson(r1);

		// CREATE
		MvcResult result = mvc
				.perform(post("/demo/createEmployee").content(r1Json).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(redirectedUrlPattern(RESOURCE_LOCATION_PATTERN)).andReturn();
		long id = getResourceIdFromUrl(result.getResponse().getRedirectedUrl());

		Employee r2 = mockEmployee("shouldCreateAndUpdate2");
		r2.setId(id);
		byte[] r2Json = toJson(r2);

		// UPDATE
		result = mvc.perform(put("/demo/employee/" + id).content(r2Json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent()).andReturn();

		// RETRIEVE the updated record
		mvc.perform(get("/demo/employee/" + id).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is((int) id))).andExpect(jsonPath("$.name", is(r2.getName())))
				.andExpect(jsonPath("$.city", is(r2.getCity())))
				.andExpect(jsonPath("$.description", is(r2.getDescription())));

		// DELETE
		mvc.perform(delete("/demo/employee/" + id)).andExpect(status().isNoContent());
	}

	private long getResourceIdFromUrl(String locationUrl) {
		String[] parts = locationUrl.split("/");
		return Long.valueOf(parts[parts.length - 1]);
	}

	private Employee mockEmployee(String prefix) {
		Employee r = new Employee();
		r.setCity(prefix + "_city");
		r.setDescription(prefix + "_description");
		r.setName(prefix + "_name");
		return r;
	}

	private byte[] toJson(Object r) throws Exception {
		ObjectMapper map = new ObjectMapper();
		return map.writeValueAsString(r).getBytes();
	}

	// match redirect header URL (Location header)
	private static ResultMatcher redirectedUrlPattern(final String expectedUrlPattern) {
		return new ResultMatcher() {
			public void match(MvcResult result) {
				Pattern pattern = Pattern.compile("\\A" + expectedUrlPattern + "\\z");
				assertTrue(pattern.matcher(result.getResponse().getRedirectedUrl()).find());
			}
		};
	}

}
