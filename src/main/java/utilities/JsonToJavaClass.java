package utilities;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonToJavaClass {
	public static <T> String javaToJson(T objet) throws JsonProcessingException {
		final ObjectMapper ob = new ObjectMapper();
		String json = ob.writeValueAsString(objet);
		return json;
	}
	
	public static <T> T jsonToJava(String json, Class<T> clazz)	throws JsonParseException, JsonMappingException, IOException {
		final ObjectMapper ob = new ObjectMapper();
		T res = ob.readValue(json, clazz);
		return res;
	}
}
