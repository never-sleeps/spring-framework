package ru.otus.spring.controller.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

abstract class AbstractControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;


    protected MvcResult perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder
                .characterEncoding(Charsets.UTF_8.name()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    protected MvcResult perform(MockHttpServletRequestBuilder builder, Object content) throws Exception {
        return mockMvc.perform(builder
                .content(objectMapper.writeValueAsString(content))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(Charsets.UTF_8.name()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    protected <T> T performAndGetResult(MockHttpServletRequestBuilder builder, TypeReference<T> valueTypeRef) throws Exception {
        val mvcResult = perform(builder);
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), valueTypeRef);
    }

    protected <T> T performAndGetResult(MockHttpServletRequestBuilder builder, Class<T> cls) throws Exception {
        val mvcResult = perform(builder);
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), cls);
    }

    protected <T> T performAndGetResult(MockHttpServletRequestBuilder builder, Object content, Class<T> cls) throws Exception {
        val mvcResult = perform(builder, content);
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), cls);
    }
}
