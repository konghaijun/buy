package com.jiguang.supplement.ws;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//WebSocket消息处理器
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    //在线用户列表，key为用户编号，value为WebSocketSession对象
    private static final Map<String, WebSocketSession> onlineUsers = new ConcurrentHashMap<>();

    //处理收到的消息
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        JSONObject jsonObject = JSON.parseObject(payload);

        String userId = jsonObject.getString("userId");

        String toUserId = jsonObject.getString("toUserId");
        String content = jsonObject.getString("content");
        //将消息存储到redis中，key为接收者的用户编号，value为消息内容
        redisTemplate.opsForList().leftPush(toUserId, content);
        //如果接收者在线，则直接发送消息
        WebSocketSession toUserSession = onlineUsers.get(toUserId);
        if (toUserSession != null && toUserSession.isOpen()) {
            TextMessage textMessage = new TextMessage(content);
            toUserSession.sendMessage(textMessage);
        }

    }

    //连接建立后触发
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = getUserId(session);
        //将连接的用户加入在线用户列表
        onlineUsers.put(userId, session);
        //从redis中获取该用户的离线消息
        List<Object> offlineMessages = redisTemplate.opsForList().range(userId, 0, -1);
        if (offlineMessages != null && offlineMessages.size() > 0) {
            //将离线消息发送给用户
            for (Object message : offlineMessages) {
                TextMessage textMessage = new TextMessage((String) message);
                session.sendMessage(textMessage);
            }
            //删除redis中的离线消息
            redisTemplate.delete(userId);
        }
    }

    //连接关闭后触发
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = getUserId(session);
        //将连接的用户从在线用户列表中移除
        onlineUsers.remove(userId);
    }


    //从WebSocketSession中获取用户编号
    private String getUserId(WebSocketSession session) {
        Map<String, Object> attributes = session.getAttributes();
        String userId = (String) attributes.get("userId");
        return userId;
    }
}
