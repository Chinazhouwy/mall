package com.wy.mall.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.Map;

/**
 * AI服务类，封装Spring AI的各种功能
 */
@Service
public class AiService {

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private OpenAiChatModel openAiChatModel;

    @Autowired
    private OpenAiApi openAiApi;

    // 会话管理，使用ConcurrentHashMap存储不同用户的会话
    private final Map<String, String> sessionMap = new ConcurrentHashMap<>();

    /**
     * 基础聊天功能
     * 
     * @param message 用户消息
     * @return AI回复
     */
    public String chat(String message) {
        return chatClient.call(message);
    }

    /**
     * 文本生成功能（可用于文案生成、摘要等）
     * 
     * @param prompt 提示词
     * @return 生成的文本
     */
    public String generateText(String prompt) {
        return chatClient.call(prompt);
    }

    /**
     * 代码生成功能
     * 
     * @param language    编程语言
     * @param description 功能描述
     * @return 生成的代码
     */
    public String generateCode(String language, String description) {
        String prompt = String.format("请生成%s代码，实现以下功能：%s\n\n要求：\n1. 代码结构清晰，注释完整\n2. 遵循最佳实践\n3. 直接返回代码，不要包含其他解释",
                language, description);
        return chatClient.call(prompt);
    }

    /**
     * 文本分类功能
     * 
     * @param text       待分类文本
     * @param categories 分类类别列表，用逗号分隔
     * @return 分类结果
     */
    public String classifyText(String text, String categories) {
        String prompt = String.format("请将以下文本分类到指定类别中：\n\n文本：%s\n\n类别：%s\n\n要求：只返回类别名称，不要包含其他解释", text, categories);
        return chatClient.call(prompt);
    }

    /**
     * 文本摘要功能
     * 
     * @param text      待摘要文本
     * @param maxLength 摘要最大长度
     * @return 摘要结果
     */
    public String summarizeText(String text, int maxLength) {
        String prompt = String.format("请对以下文本进行摘要，摘要长度不超过%d个字：\n\n%s\n\n要求：\n1. 保留核心信息\n2. 语言流畅\n3. 直接返回摘要，不要包含其他解释",
                maxLength, text);
        return chatClient.call(prompt);
    }

    /**
     * 清除会话
     * 
     * @param sessionId 会话ID
     */
    public void clearSession(String sessionId) {
        sessionMap.remove(sessionId);
    }

    /**
     * 获取会话数量
     * 
     * @return 会话数量
     */
    public int getSessionCount() {
        return sessionMap.size();
    }
}
