package com.wy.mall.ai.example;

import com.wy.mall.ai.service.AiService;
import org.springframework.ai.chat.messages.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Spring AI使用示例类，展示如何使用Spring AI框架的各种功能
 */
@Component
public class AiUsageExample {

    @Autowired
    private AiService aiService;

    /**
     * 示例：基础聊天功能
     */
    public void chatExample() {
        System.out.println("=== 基础聊天功能示例 ===");
        String message = "你好，介绍一下自己吧";
        String response = aiService.chat(message);
        System.out.println("用户：" + message);
        System.out.println("AI：" + response);
        System.out.println();
    }

    /**
     * 示例：文本生成功能
     */
    public void textGenerationExample() {
        System.out.println("=== 文本生成功能示例 ===");
        String prompt = "写一首关于春天的短诗，要求押韵，不超过200字";
        String response = aiService.generateText(prompt);
        System.out.println("提示词：" + prompt);
        System.out.println("生成结果：");
        System.out.println(response);
        System.out.println();
    }

    /**
     * 示例：代码生成功能
     */
    public void codeGenerationExample() {
        System.out.println("=== 代码生成功能示例 ===");
        String language = "Java";
        String description = "实现一个单例模式，要求线程安全，使用双重检查锁定";
        String code = aiService.generateCode(language, description);
        System.out.println("语言：" + language);
        System.out.println("功能描述：" + description);
        System.out.println("生成代码：");
        System.out.println(code);
        System.out.println();
    }

    /**
     * 示例：文本分类功能
     */
    public void textClassificationExample() {
        System.out.println("=== 文本分类功能示例 ===");
        String text = "今天天气真好，阳光明媚，适合外出游玩";
        String categories = "积极,消极,中性";
        String category = aiService.classifyText(text, categories);
        System.out.println("待分类文本：" + text);
        System.out.println("分类类别：" + categories);
        System.out.println("分类结果：" + category);
        System.out.println();
    }

    /**
     * 示例：文本摘要功能
     */
    public void textSummarizationExample() {
        System.out.println("=== 文本摘要功能示例 ===");
        String text = "人工智能（AI）是计算机科学的一个分支，它旨在创建能够模拟人类智能的机器。人工智能的研究领域包括机器学习、自然语言处理、计算机视觉、机器人技术、专家系统等。机器学习是AI的一个重要分支，它使计算机能够从数据中学习，而不需要显式编程。自然语言处理则专注于使计算机能够理解和生成人类语言。计算机视觉使计算机能够理解和解释图像和视频。人工智能已经在许多领域得到应用，如医疗、金融、交通、教育等。随着技术的不断发展，人工智能的应用前景将更加广阔。";
        int maxLength = 100;
        String summary = aiService.summarizeText(text, maxLength);
        System.out.println("原始文本：" + text);
        System.out.println("摘要最大长度：" + maxLength + "字");
        System.out.println("摘要结果：");
        System.out.println(summary);
        System.out.println();
    }

    /**
     * 示例：带有上下文的聊天
     */
    public void contextChatExample() {
        System.out.println("=== 带有上下文的聊天示例 ===");

        // 上下文信息
        String context = "Spring Boot 3.5.0 是Spring Boot的最新版本，发布于2025年12月，" +
                "它引入了许多新特性，包括对Spring AI的原生支持、增强的云原生能力、" +
                "改进的性能和安全性，以及更好的开发体验。";

        String message = "Spring Boot 3.5.0 有哪些关于Spring AI的新特性？";
        String response = aiService.chatWithContext(message, context);

        System.out.println("上下文：" + context);
        System.out.println("用户：" + message);
        System.out.println("AI：" + response);
        System.out.println();
    }

    /**
     * 示例：生成结构化响应
     */
    public void structuredResponseExample() {
        System.out.println("=== 生成结构化响应示例 ===");
        String message = "请列出太阳系中的行星及其特点";

        // 生成JSON格式的响应
        String jsonResponse = aiService.generateStructuredResponse(message, "JSON");
        System.out.println("用户：" + message);
        System.out.println("JSON格式响应：");
        System.out.println(jsonResponse);

        // 生成XML格式的响应
        String xmlResponse = aiService.generateStructuredResponse(message, "XML");
        System.out.println("XML格式响应：");
        System.out.println(xmlResponse);
        System.out.println();
    }

    /**
     * 示例：提示词优化
     */
    public void promptOptimizationExample() {
        System.out.println("=== 提示词优化示例 ===");
        String originalPrompt = "写一篇关于环保的文章";

        String optimizedPrompt = aiService.optimizePrompt(originalPrompt);
        System.out.println("原始提示词：" + originalPrompt);
        System.out.println("优化后提示词：" + optimizedPrompt);

        // 使用优化后的提示词生成内容
        String response = aiService.generateText(optimizedPrompt);
        System.out.println("使用优化后提示词生成的内容：");
        System.out.println(response);
        System.out.println();
    }

    /**
     * 示例：异步聊天
     */
    public void asyncChatExample() {
        System.out.println("=== 异步聊天示例 ===");
        String message = "写一个简单的Java方法，实现斐波那契数列计算";

        System.out.println("异步请求已发送：" + message);
        System.out.println("等待响应...");

        // 异步调用
        java.util.concurrent.CompletableFuture<String> future = aiService.asyncChat(message);

        // 模拟其他工作
        System.out.println("执行其他任务...");

        // 获取结果
        future.thenAccept(response -> {
            System.out.println("AI异步回复：");
            System.out.println(response);
            System.out.println();
        });

        // 等待异步完成
        future.join();
    }

    /**
     * 示例：多轮对话中的角色切换
     */
    public void multiRoleChatExample() {
        System.out.println("=== 多轮对话中的角色切换示例 ===");

        // 构建多轮对话消息，包含不同角色
        java.util.List<org.springframework.ai.chat.messages.Message> messages = new java.util.ArrayList<>();
        messages.add(new org.springframework.ai.chat.messages.SystemMessage("你是一个专业的软件开发顾问"));
        messages.add(new org.springframework.ai.chat.messages.UserMessage("我想学习Spring框架，应该从哪里开始？"));
        messages.add(new org.springframework.ai.chat.messages.AssistantMessage(
                "你可以从Spring Boot开始学习，它是Spring生态的入门框架，简化了Spring应用的开发和部署。"));
        messages.add(new org.springframework.ai.chat.messages.UserMessage("Spring Boot和Spring MVC有什么关系？"));

        String response = aiService.multiRoleChat(messages);

        System.out.println("对话历史：");
        messages.forEach(msg -> {
            // 根据消息类型获取角色
            String role = "user";
            if (msg instanceof SystemMessage) {
                role = "system";
            } else if (msg instanceof AssistantMessage) {
                role = "assistant";
            } else if (msg instanceof FunctionMessage) {
                role = "function";
            }
            System.out.println(role + ": " + msg.getContent());
        });
        System.out.println("AI最新回复：" + response);
        System.out.println();
    }

    /**
     * 运行所有示例
     */
    public void runAllExamples() {
        System.out.println("开始运行Spring AI示例...\n");

        // 基础示例
        chatExample();
        textGenerationExample();
        codeGenerationExample();
        textClassificationExample();
        textSummarizationExample();

        // 高级示例
        contextChatExample();
        structuredResponseExample();
        promptOptimizationExample();
        asyncChatExample();
        multiRoleChatExample();

        System.out.println("所有示例运行完成！");
    }
}
