package com.rest.api.v1.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbsractRestControllerTest {

	public static String asJsonString(final Object obj) {
	    try {
	        final ObjectMapper mapper = new ObjectMapper();
	        final String jsonContent = mapper.writeValueAsString(obj);
	        return jsonContent;
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}  
}
