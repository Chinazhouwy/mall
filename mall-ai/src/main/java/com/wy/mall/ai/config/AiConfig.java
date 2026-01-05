package com.wy.mall.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring AI配置类
 */
@Configuration
@RefreshScope
public class AiConfig {

    // DeepSeek配置
    @Value("${spring.ai.deepseek.api-key}")
    private String deepseekApiKey;

    @Value("${spring.ai.deepseek.base-url}")
    private String deepseekBaseUrl;

    @Value("${spring.ai.deepseek.chat.model}")
    private String deepseekModel;

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
     * 配置OpenAI API客户端
     */
    @Bean
    public OpenAiApi openAiApi() {
        return new OpenAiApi(deepseekApiKey, deepseekBaseUrl);
    }

    /**
     * 配置OpenAI聊天模型
     */
    @Bean
    public OpenAiChatModel openAiChatModel(OpenAiApi openAiApi) {
        return new OpenAiChatModel(openAiApi);
    }

    /**
     * 配置聊天客户端
     */
    @Bean
    public ChatClient chatClient(OpenAiChatModel openAiChatModel) {
        return ChatClient.builder(openAiChatModel)
                .defaultSystem("你是一个智能助手，帮助用户解决各种问题")
                .build();
    }
}