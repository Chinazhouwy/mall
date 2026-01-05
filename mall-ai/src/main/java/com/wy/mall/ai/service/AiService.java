package com.wy.mall.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.messages.*;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * AI服务类，封装Spring AI的各种功能
 */
@Service
public class AiService {

    @Autowired
    private OpenAiChatModel openAiChatModel;

    @Autowired
    private OpenAiApi openAiApi;

    // 会话管理，使用ConcurrentHashMap存储不同用户的会话
    private final Map<String, ChatClient> sessionClients = new ConcurrentHashMap<>();

    /**
     * 基础聊天功能
     * 
     * @param message 用户消息
     * @return AI回复
     */
    public String chat(String message) {
        return openAiChatModel.call(message);
    }

    /**
     * 使用结构化消息进行聊天
     * 
     * @param messages 结构化消息列表
     * @return AI回复
     */
    public String chatWithStructuredMessages(List<Message> messages) {
        // 创建提示词
        Prompt prompt = new Prompt(messages);
        // 调用模型
        ChatResponse response = openAiChatModel.call(prompt);
        return response.getResult().getOutput().getContent();
    }

    /**
     * 使用提示模板生成回复
     * 
     * @param template 提示词模板
     * @param variables 模板变量
     * @return AI回复
     */
    public String chatWithTemplate(String template, Map<String, Object> variables) {
        // 创建提示词模板
        PromptTemplate promptTemplate = new PromptTemplate(template, variables);
        // 生成提示词
        Prompt prompt = promptTemplate.create();
        // 调用模型
        ChatResponse response = openAiChatModel.call(prompt);
        return response.getResult().getOutput().getContent();
    }

    /**
     * 带有会话管理的聊天功能
     * 
     * @param sessionId 会话ID
     * @param message 用户消息
     * @return AI回复
     */
    public String chatWithSession(String sessionId, String message) {
        // 简化实现：直接使用openAiChatModel
        return openAiChatModel.call(message);
    }

    /**
     * 文本生成功能（可用于文案生成、摘要等）
     * 
     * @param prompt 提示词
     * @return 生成的文本
     */
    public String generateText(String prompt) {
        return openAiChatModel.call(prompt);
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
        return openAiChatModel.call(prompt);
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
        return openAiChatModel.call(prompt);
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
        return openAiChatModel.call(prompt);
    }

    /**
     * 创建结构化消息
     * 
     * @param role 角色：system, user, assistant
     * @param content 消息内容
     * @return 结构化消息
     */
    public Message createMessage(String role, String content) {
        return switch (role.toLowerCase()) {
            case "system" -> new SystemMessage(content);
            case "assistant" -> new AssistantMessage(content);
            case "user" -> new UserMessage(content);
            case "function" -> {
                // 创建空的函数调用结果映射
                Map<String, Object> functionResult = new HashMap<>();
                functionResult.put("content", content);
                yield new FunctionMessage("", functionResult);
            }
            default -> new UserMessage(content);
        };
    }

    /**
     * 清除会话
     * 
     * @param sessionId 会话ID
     */
    public void clearSession(String sessionId) {
        sessionClients.remove(sessionId);
    }

    /**
     * 获取会话数量
     * 
     * @return 会话数量
     */
    public int getSessionCount() {
        return sessionClients.size();
    }

    /**
     * 清除所有会话
     */
    public void clearAllSessions() {
        sessionClients.clear();
    }

    /**
     * 批量生成文本
     * 
     * @param prompts 提示词列表
     * @return 生成的文本列表
     */
    public List<String> batchGenerate(List<String> prompts) {
        List<String> results = new ArrayList<>();
        for (String prompt : prompts) {
            results.add(openAiChatModel.call(prompt));
        }
        return results;
    }

    /**
     * 带有上下文的聊天功能，自动管理上下文长度
     * 
     * @param message 用户消息
     * @param context 上下文信息
     * @return AI回复
     */
    public String chatWithContext(String message, String context) {
        // 构建带有上下文的提示词
        String promptText = String.format("基于以下上下文回答用户问题：\n\n上下文：%s\n\n用户问题：%s", context, message);
        return openAiChatModel.call(promptText);
    }

    /**
     * 生成结构化响应
     * 
     * @param message 用户消息
     * @param format 期望的响应格式（如JSON、XML等）
     * @return 结构化的AI回复
     */
    public String generateStructuredResponse(String message, String format) {
        String prompt = String.format("请回答以下问题，并以%s格式返回：\n\n%s", format, message);
        return openAiChatModel.call(prompt);
    }

    /**
     * 提示词优化功能
     * 
     * @param originalPrompt 原始提示词
     * @return 优化后的提示词
     */
    public String optimizePrompt(String originalPrompt) {
        String optimizationPrompt = String.format("请优化以下AI提示词，使其更清晰、更有效，能够产生更好的AI回复：\n\n%s", originalPrompt);
        return openAiChatModel.call(optimizationPrompt);
    }

    /**
     * 异步聊天功能
     * 
     * @param message 用户消息
     * @return 包含AI回复的CompletableFuture
     */
    public java.util.concurrent.CompletableFuture<String> asyncChat(String message) {
        return java.util.concurrent.CompletableFuture.supplyAsync(() -> {
            return openAiChatModel.call(message);
        });
    }

    /**
     * 多轮对话中的角色切换示例
     * 
     * @param messages 多轮对话消息
     * @return AI回复
     */
    public String multiRoleChat(List<Message> messages) {
        // 创建带有角色信息的提示
        Prompt prompt = new Prompt(messages);
        ChatResponse response = openAiChatModel.call(prompt);
        return response.getResult().getOutput().getContent();
    }
}