package com.mhj.ms.acess.control.security.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JacksonMapper extends ObjectMapper {

	private static final long serialVersionUID = -7079372794138698585L;
	
	private static ObjectMapper INSTANCE = new JacksonMapper();

	public static ObjectMapper getInstance() {
		return INSTANCE;
	}

	protected static class FilteredTypeResolverBuilder extends DefaultTypeResolverBuilder {

		private static final long serialVersionUID = -7374274192186227092L;

		public FilteredTypeResolverBuilder(final DefaultTyping defaultTyping) {
			super(defaultTyping);
		}
	}

	private void initializeInstance() {
		this.setDefaultTyping(new FilteredTypeResolverBuilder(ObjectMapper.DefaultTyping.NON_FINAL)
				.init(JsonTypeInfo.Id.MINIMAL_CLASS, null).inclusion(JsonTypeInfo.As.WRAPPER_OBJECT));
		this.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		this.setConfig(this.getSerializationConfig().withView(JacksonViews.AuthorizationEnablerView.class));
	}

	private JacksonMapper() {
		this.initializeInstance();
	}
}
