package com.mhj.ms.acess.control.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mhj.ms.acess.control.security.util.JacksonMapper;
import com.mhj.ms.acess.control.security.util.JacksonViews.AuthorizationEnablerView;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class AuthException extends Exception {

	private static final long serialVersionUID = -2032090792650462544L;

	private static final String FIELD_STATUS = "status";

	private static final String FIELD_ERROR = "error";

	private static final String FIELD_EXCEPTION = "exception";

	private static final String FIELD_MESSAGE = "message";

	private static final String FIELD_PATH = "path";

	private static final String FIELD_TIMESTAMP = "timestamp";

	private static final int DEFAULT_ERR_CODE = 500;

	@JsonView(AuthorizationEnablerView.class)
	private final int status;

	@JsonView(AuthorizationEnablerView.class)
	private String error;

	@JsonView(AuthorizationEnablerView.class)
	private String message;

	@JsonView(AuthorizationEnablerView.class)
	private String exception;

	@JsonView(AuthorizationEnablerView.class)
	private String path;

	@JsonView(AuthorizationEnablerView.class)
	private long timestamp;

	public AuthException() {
		this(DEFAULT_ERR_CODE);
	}

	public AuthException(final String error, final Exception exception) {
		this(DEFAULT_ERR_CODE);

		this.error = error;
		this.message = exception.getMessage();
		this.exception = this.getStackTrace(exception);
	}

	public AuthException(final String error, final String message, final String exception) {
		this(DEFAULT_ERR_CODE);

		this.error = error;
		this.message = message;
		this.exception = exception;
	}

	public AuthException(final int status, final String error, final Exception exception) {
		this(status);

		this.error = error;
		this.message = exception.getMessage();
		this.exception = this.getStackTrace(exception);
	}

	public AuthException(final int status, final String error, final String message, final String exception) {
		this(status);

		this.error = error;
		this.message = message;
		this.exception = exception;
	}

	public AuthException(final int status) {
		this.status = status;

		final Date now = new Date();
		this.timestamp = now.getTime();
	}

	@JsonProperty(FIELD_STATUS)
	public int getStatus() {
		return this.status;
	}

	public void setError(final String error) {
		this.error = error;
	}

	@JsonProperty(FIELD_ERROR)
	public String getError() {
		return this.error;
	}

	public void setException(final String exception) {
		this.exception = exception;
	}

	@JsonProperty(FIELD_EXCEPTION)
	public String getException() {
		return this.exception;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	@JsonProperty(FIELD_MESSAGE)
	public String getMessage() {
		return this.message;
	}

	public void setPath(final String path) {
		this.path = path;
	}

	@JsonProperty(FIELD_PATH)
	public String getPath() {
		return this.path;
	}

	public void setTimestamp(final long timestamp) {
		this.timestamp = timestamp;
	}

	@JsonProperty(FIELD_TIMESTAMP)
	public long getTimestamp() {
		return this.timestamp;
	}

	public Object getBodyExceptionMessage() {
		final Map<String, Object> mapBodyException = new HashMap<String, Object>();

		mapBodyException.put(FIELD_ERROR, this.error);
		mapBodyException.put(FIELD_MESSAGE, this.message);
//        mapBodyException.put(FIELD_EXCEPTION, this.exception) ;
		mapBodyException.put(FIELD_PATH, this.path);
		mapBodyException.put(FIELD_TIMESTAMP, this.timestamp);

		return mapBodyException;
	}

	@Override
	public String toString() {
		try {
			return JacksonMapper.getInstance().writerWithDefaultPrettyPrinter().writeValueAsString(this);
		} catch (JsonProcessingException jsonProcessingExc) {
			return "Exception converting to Json string: " + jsonProcessingExc.getMessage();
		}
	}

	private String getStackTrace(final Exception exception) {
		final StringWriter stringWriter = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(stringWriter);
		exception.printStackTrace(printWriter);

		return stringWriter.toString();
	}
}
