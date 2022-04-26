package org.leo.energy.service.boot.security.filter;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.leo.energy.common.utils.Func;
import org.leo.energy.common.utils.JsonUtil;
import org.leo.energy.common.utils.SpringUtil;
import org.leo.energy.service.boot.security.SecureUtil;
import org.leo.energy.service.boot.security.auth.AuthProvider;
import org.leo.energy.service.boot.security.prop.SecureProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author:gonglong
 * @Date:2022/4/15 16:10
 */
@Slf4j
@AllArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    private final SecureProperties secureProperties;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String path = request.getRequestURI();
        if (isSkip(path)) {
             chain.doFilter(request,response);
             return;
        }
        String headerToken = request.getHeader(AuthProvider.AUTH_KEY);
        String paramToken = request.getParameter(AuthProvider.AUTH_KEY);
        if (StringUtils.isAllBlank(headerToken, paramToken)) {
            System.out.println("**********headerToken*********"+headerToken+"**********headerToken***********");
            System.out.println("**********paramToken*********"+paramToken+"************paramToken*********");
            unAuth(response, "缺失令牌,鉴权失败");
            return;
        }
        String auth = Func.isBlank(headerToken) ? paramToken : headerToken;

        String token = SecureUtil.getToken(auth);
        Claims claims = SecureUtil.parseJWT(token);
        if (claims == null) {
            System.out.println("********auth********"+ auth +"************auth********");
            System.out.println("********token********"+ token +"************token********");
            unAuth(response, "请求未授权");
            return;
        }
        chain.doFilter(request,response);
    }

    private void unAuth(HttpServletResponse resp, String msg) {
        resp.setStatus(HttpStatus.UNAUTHORIZED.value());
        resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        Map<String, Object> map = new HashMap<>(16);
        map.put("code", HttpStatus.UNAUTHORIZED.value());
        map.put("msg", msg);
        map.put("data", null);
        try {
            PrintWriter writer = resp.getWriter();
            writer.write(Objects.requireNonNull(JsonUtil.toJson(map)));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
    }

    private boolean isSkip(String path) {
        return AuthProvider.getDefaultSkipUrl().stream().anyMatch(pattern -> antPathMatcher.match(pattern, path))
                || secureProperties.getSkipUrl().stream().anyMatch(pattern -> antPathMatcher.match(pattern, path));
    }
}
