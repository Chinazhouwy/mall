package com.atguigu.mall.ai.config;

import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AI客户端配置类
 */
@Configuration
public class AiClientConfig {

    // DeepSeek配置
    @Value("${spring.ai.deepseek.api-key}")
    private String deepseekApiKey;
    
    @Value("${spring.ai.deepseek.base-url}")
    private String deepseekBaseUrl;
    
    @Value("${spring.ai.deepseek.chat.model}")
    private String deepseekModel;
    
    // 阿里千问配置
    @Value("${spring.ai.qwen.api-key}")
    private String qwenApiKey;
    
    @Value("${spring.ai.qwen.base-url}")
    private String qwenBaseUrl;
    
    @Value("${spring.ai.qwen.chat.model}")
    private String qwenModel;
    
    /**
     * 创建DeepSeek客户端
     */
    @Bean("deepseekChatClient")
    public OpenAiChatClient deepseekChatClient() {
        OpenAiApi openAiApi = new OpenAiApi(deepseekBaseUrl, deepseekApiKey);
        return new OpenAiChatClient(openAiApi, deepseekModel);
    }
    
    /**
     * 创建阿里千问客户端
     */
    @Bean("qwenChatClient")
    public OpenAiChatClient qwenChatClient() {
        OpenAiApi openAiApi = new OpenAiApi(qwenBaseUrl, qwenApiKey);
        return new OpenAiChatClient(openAiApi, qwenModel);
    }
}