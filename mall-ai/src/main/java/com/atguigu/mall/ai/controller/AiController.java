package com.atguigu.mall.ai.controller;

import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * AI接口控制器
 */
@RestController
@RequestMapping("/api/ai")
public class AiController {

    @Autowired
    @Qualifier("deepseekChatClient")
    private OpenAiChatClient deepseekChatClient;
    
    @Autowired
    @Qualifier("qwenChatClient")
    private OpenAiChatClient qwenChatClient;
    
    /**
     * 使用DeepSeek进行对话
     */
    @PostMapping("/deepseek/chat")
    public String deepseekChat(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        if (message == null || message.isEmpty()) {
            return "请提供对话内容";
        }
        return deepseekChatClient.call(message);
    }
    
    /**
     * 使用阿里千问进行对话
     */
    @PostMapping("/qwen/chat")
    public String qwenChat(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        if (message == null || message.isEmpty()) {
            return "请提供对话内容";
        }
        return qwenChatClient.call(message);
    }
}