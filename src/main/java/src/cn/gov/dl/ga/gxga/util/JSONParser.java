package cn.gov.dl.ga.gxga.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JSON Parser - Parse the JSON data to a HashMap
 * 
 * @author Nan Lei
 * 
 */
public class JSONParser {
	private static final Logger logger = LoggerFactory
			.getLogger(JSONParser.class);

	/**
	 * Parsing the JSON data to a HashMap
	 * 
	 * @param jsonText
	 *            The JSON data from front-end
	 * @return A HashMap<String, String> object populated the data from jsonText
	 */
	public static HashMap<String, String> parseJSON(String jsonText) {

		if (StringUtils.isBlank(jsonText)) {
			return null;
		}

		HashMap<String, String> map = new HashMap<String, String>();

		try {

			JsonFactory jsonFactory = new MappingJsonFactory();

			JsonParser jsonParser = jsonFactory.createJsonParser(jsonText)
					.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS,
							true);

			jsonParser.nextToken();

			while (jsonParser.nextToken() != JsonToken.END_OBJECT) {

				jsonParser.nextToken();

				map.put(jsonParser.getCurrentName(), jsonParser.getText());
			}

		} catch (Exception e) {

			logger.error(ExceptionUtils.getStackTrace(e));

		}
		return map;
	}

	/**
	 * Parsing the JSON data to a List
	 * 
	 * @param jsonText
	 *            The JSON data from front-end
	 * @return A List<HashMap<String, String>> object populated the data from
	 *         jsonText
	 */
	@SuppressWarnings("unchecked")
	public static List<HashMap<String, String>> parseJSONToList(String jsonText) {

		if (StringUtils.isBlank(jsonText)) {
			return null;
		}

		List<HashMap<String, String>> list = null;

		try {

			list = (List<HashMap<String, String>>) new ObjectMapper()
					.readValue(jsonText, List.class);

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		return list;
	}

	public static Object parseJSONToObject(String jsonText, Class<?> clazz) {

		if (StringUtils.isEmpty(jsonText)) {
			return null;
		}

		Object obj = null;

		ObjectMapper mapper = new ObjectMapper();

		try {

			obj = mapper.readValue(jsonText, clazz);

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		return obj;
	}

	/**
	 * Parsing the jsonText to a given class, and populate the fields of the
	 * object
	 * 
	 * @param jsonText
	 *            The JSON data from front-end
	 * @param clazz
	 *            The Class type of the object
	 * @return A given class object populated the data from jsonText
	 */
	public static Object parseJSON(String jsonText, Class<?> clazz) {

		HashMap<String, String> hashMap = parseJSON(jsonText);
		Object bean = null;
		try {
			bean = clazz.newInstance();

			BeanUtils.populate(bean, hashMap);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		return bean;
	}

	/**
	 * Main method for testing
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// Test For HashMap
		String jsonText = "{\"name\":\"nanlei\",\"age\":\"24\"}";
		HashMap<String, String> userInfoMap = JSONParser.parseJSON(jsonText);
		System.out.println(userInfoMap.get("name"));
		System.out.println(userInfoMap.get("age"));

		// Test For Object
		//String jsonText2 = "{\"newPassword\":\"123456\",\"newPasswordConfirm\":\"123456\"}";

		// Test For List
		String jsonText3 = "[{\"name\":\"nanlei\",\"age\":\"24\"},{\"name\":\"nanlei2\",\"age\":\"25\"}]";
		List<HashMap<String, String>> list = JSONParser
				.parseJSONToList(jsonText3);
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, String> map = list.get(i);
			Set<String> set = map.keySet();
			for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
				String key = iterator.next();
				System.out.println(key + ":" + map.get(key));
			}
			System.out.println("------------------");
		}

		List<HashMap<String, String>> lst = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", "nanlei");
		map.put("age", "23");
		lst.add(map);
	}
}
