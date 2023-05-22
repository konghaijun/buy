package com.jiguang.supplement.ws;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.server.HandshakeInterceptor;
import  org.springframework.web.socket.WebSocketHandler;
import java.util.Map;

//WebSocket拦截器
@Component
public class WebSocketInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            //从请求参数中获取用户编号
            String userId = servletRequest.getServletRequest().getParameter("userId");
            if (StringUtils.isEmpty(userId)) {
                return false;
            }
            //将用户编号放入WebSocketSession的attributes中
            attributes.put("userId", userId);
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception exception) {
    }



}
