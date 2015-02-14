package cn.gov.dl.ga.gxga.core.view;

import java.io.StringWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.Assert;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * Sub class of MappingJacksonJsonView - Adding the base64 Encoding of JSON
 * Response
 * 
 * @author Nan Lei
 * 
 */
public class JsonView extends MappingJackson2JsonView {
	public static final String DEFAULT_CONTENT_TYPE = "application/json";

	private ObjectMapper objectMapper = new ObjectMapper();

	private boolean prefixJson = false;

	public JsonView() {
		setContentType(DEFAULT_CONTENT_TYPE);
		setExposePathVariables(false);
	}

	public void setObjectMapper(ObjectMapper objectMapper) {
		Assert.notNull(objectMapper, "'objectMapper' must not be null");
		this.objectMapper = objectMapper;
	}

	public void setPrefixJson(boolean prefixJson) {
		this.prefixJson = prefixJson;
	}

	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Object value = filterModel(model);

		StringWriter sw = new StringWriter();

		JsonGenerator generator = this.objectMapper.getJsonFactory()
				.createJsonGenerator(sw);

		if (this.prefixJson) {
			generator.writeRaw("{} && ");
		}
		this.objectMapper.writeValue(generator, value);

		response.getWriter().write(sw.toString());
	}
}
