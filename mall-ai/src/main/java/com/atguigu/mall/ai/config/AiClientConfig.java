package com.atguigu.mall.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AI客户端配置类
 */
@Configuration
@RefreshScope
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
    public ChatClient deepseekChatClient() {
        OpenAiApi openAiApi = new OpenAiApi(deepseekBaseUrl, deepseekApiKey);
        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .withModel(deepseekModel)
                .build();
        OpenAiChatModel chatModel = new OpenAiChatModel(openAiApi, options);
        return ChatClient.builder(chatModel).build();
    }
    
    /**
     * 创建阿里千问客户端
     */
    @Bean("qwenChatClient")
    public ChatClient qwenChatClient() {
        OpenAiApi openAiApi = new OpenAiApi(qwenBaseUrl, qwenApiKey);
        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .withModel(qwenModel)
                .build();
        OpenAiChatModel chatModel = new OpenAiChatModel(openAiApi, options);
        return ChatClient.builder(chatModel).build();
    }
}