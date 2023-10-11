package com.lfin.assignment.common.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;


@Component
@Order(2)
public class LoggingReqResFilter implements Filter {

    private final Logger loggger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //호출시간
        Instant start = Instant.now();

        // 전처리
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        chain.doFilter(requestWrapper, responseWrapper);

        // 후처리
        String requestBody = getRequestBody(requestWrapper); //요청전문
        String responseBody = getResponseBody(responseWrapper); //응답전문

        //request
        String remoteAddr = req.getRemoteAddr(); //clientIP


        //시간
        long duration = Duration.between(start, Instant.now()).toMillis(); //처리시간

        loggger.info("클라이언트IP : {}\t 호출시간 : {}\t 요청전문 : {}\t 응답전문 : {}\t 처리시간 : {}",
                remoteAddr, start, requestBody, responseBody, duration);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }


    /**
     * Request body 가져오기
     *
     * @param request
     * @return
     */
    private String getRequestBody(ContentCachingRequestWrapper request) {
        String body = "";
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                try {
                    body = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                } catch (UnsupportedEncodingException e) {
                    body = "";
                }
            }
        }
        return body;
    }

    /**
     * Response Body 가져오기
     *
     * @param response
     * @return
     * @throws IOException
     */
    private String getResponseBody(final HttpServletResponse response) throws IOException {
        String body = "";
        ContentCachingResponseWrapper wrapper =
                WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                body = new String(buf, 0, buf.length, StandardCharsets.UTF_8);
                wrapper.copyBodyToResponse();
            }
        }
        return body;
    }
}
