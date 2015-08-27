package cn.gov.dl.ga.gxga.util;

import java.io.StringWriter;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gov.dl.ga.gxga.model.ReadHeadBranchFile;

public class JSONMaker {
	private static Logger logger = LoggerFactory.getLogger(JSONMaker.class);

	public static String makeJson(Object object) {
		ObjectMapper objectMapper = new ObjectMapper();

		StringWriter sw = new StringWriter();

		JsonGenerator generator;

		try {
			generator = objectMapper.getJsonFactory().createJsonGenerator(sw);
			objectMapper.writeValue(generator, object);
		} catch (Exception e) {
			logger.error("{}", ExceptionUtils.getStackTrace(e));
		}

		return sw.toString();

	}

	public static void main(String[] args) {
		String jsonString = makeJson(new ReadHeadBranchFile("AAA", "BBB",
				"CCC", "DDD"));
		System.out.println(jsonString);
	}
}
