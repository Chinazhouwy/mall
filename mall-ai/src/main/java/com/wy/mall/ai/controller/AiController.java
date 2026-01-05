package com.wy.mall.ai.controller;

import com.wy.mall.ai.service.AiService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AI控制器，提供REST API接口
 */
@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    /**
     * 基础聊天接口
     * 
     * @param request 包含message字段的请求体
     * @return AI回复
     */
    @PostMapping("/chat")
    public ResponseEntity<Map<String, Object>> chat(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        if (message == null || message.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "message不能为空");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        String response = aiService.chat(message);
        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("success", true);
        successResponse.put("response", response);
        return ResponseEntity.ok(successResponse);
    }

    /**
     * 使用提示模板生成回复
     * 
     * @param request 包含template和variables字段的请求体
     * @return AI回复
     */
    @PostMapping("/chat/template")
    public ResponseEntity<Map<String, Object>> chatWithTemplate(@RequestBody Map<String, Object> request) {
        String template = (String) request.get("template");
        @SuppressWarnings("unchecked")
        Map<String, Object> variables = (Map<String, Object>) request.get("variables");

        if (template == null || template.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "template不能为空");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        if (variables == null) {
            variables = new HashMap<>();
        }

        String response = aiService.chatWithTemplate(template, variables);
        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("success", true);
        successResponse.put("response", response);
        return ResponseEntity.ok(successResponse);
    }

    /**
     * 带有会话管理的聊天接口
     * 
     * @param sessionId 会话ID
     * @param request   包含message字段的请求体
     * @return AI回复
     */
    @PostMapping("/chat/session/{sessionId}")
    public ResponseEntity<Map<String, Object>> chatWithSession(@PathVariable String sessionId,
            @RequestBody Map<String, String> request) {
        String message = request.get("message");
        if (message == null || message.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "message不能为空");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        String response = aiService.chatWithSession(sessionId, message);
        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("success", true);
        successResponse.put("response", response);
        return ResponseEntity.ok(successResponse);
    }

    /**
     * 流式聊天接口（简化实现）
     * 
     * @param request 包含message字段的请求体
     * @return AI回复
     */
    @PostMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<String> streamChat(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        if (message == null || message.isEmpty()) {
            return ResponseEntity.badRequest().body("error: message不能为空");
        }

        // 简化实现：返回非流式响应
        String response = aiService.chat(message);
        return ResponseEntity.ok(response);
    }

    /**
     * 文本生成接口
     * 
     * @param request 包含prompt字段的请求体
     * @return 生成的文本
     */
    @PostMapping("/generate/text")
    public ResponseEntity<Map<String, Object>> generateText(@RequestBody Map<String, String> request) {
        String prompt = request.get("prompt");
        if (prompt == null || prompt.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "prompt不能为空");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        String response = aiService.generateText(prompt);
        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("success", true);
        successResponse.put("result", response);
        return ResponseEntity.ok(successResponse);
    }

    /**
     * 批量生成文本接口
     * 
     * @param request 包含prompts字段的请求体
     * @return 生成的文本列表
     */
    @PostMapping("/generate/batch")
    public ResponseEntity<Map<String, Object>> batchGenerate(@RequestBody Map<String, List<String>> request) {
        List<String> prompts = request.get("prompts");
        if (prompts == null || prompts.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "prompts不能为空");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        List<String> results = aiService.batchGenerate(prompts);
        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("success", true);
        successResponse.put("results", results);
        return ResponseEntity.ok(successResponse);
    }

    /**
     * 代码生成接口
     * 
     * @param request 包含language和description字段的请求体
     * @return 生成的代码
     */
    @PostMapping("/generate/code")
    public ResponseEntity<Map<String, Object>> generateCode(@RequestBody Map<String, String> request) {
        String language = request.get("language");
        String description = request.get("description");

        if (language == null || language.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "language不能为空");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        if (description == null || description.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "description不能为空");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        String code = aiService.generateCode(language, description);
        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("success", true);
        successResponse.put("language", language);
        successResponse.put("code", code);
        return ResponseEntity.ok(successResponse);
    }

    /**
     * 文本分类功能
     * 
     * @param request 包含text和categories字段的请求体
     * @return 分类结果
     */
    @PostMapping("/classify/text")
    public ResponseEntity<Map<String, Object>> classifyText(@RequestBody Map<String, String> request) {
        String text = request.get("text");
        String categories = request.get("categories");

        if (text == null || text.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "text不能为空");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        if (categories == null || categories.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "categories不能为空");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        String result = aiService.classifyText(text, categories);
        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("success", true);
        successResponse.put("category", result.trim());
        return ResponseEntity.ok(successResponse);
    }

    /**
     * 文本摘要功能
     * 
     * @param request 包含text和maxLength字段的请求体
     * @return 摘要结果
     */
    @PostMapping("/summarize/text")
    public ResponseEntity<Map<String, Object>> summarizeText(@RequestBody Map<String, Object> request) {
        String text = (String) request.get("text");
        Integer maxLength = (Integer) request.getOrDefault("maxLength", 100);

        if (text == null || text.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "text不能为空");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        String summary = aiService.summarizeText(text, maxLength);
        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("success", true);
        successResponse.put("summary", summary);
        return ResponseEntity.ok(successResponse);
    }

    /**
     * 清除会话接口
     * 
     * @param sessionId 会话ID
     * @return 操作结果
     */
    @DeleteMapping("/session/{sessionId}")
    public ResponseEntity<Map<String, Object>> clearSession(@PathVariable String sessionId) {
        aiService.clearSession(sessionId);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "会话已清除");
        return ResponseEntity.ok(response);
    }

    /**
     * 异步聊天功能
     * 
     * @param request 包含message字段的请求体
     * @return AI回复
     */
    @PostMapping("/chat/async")
    public ResponseEntity<Map<String, Object>> asyncChat(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        if (message == null || message.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "message不能为空");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        // 异步调用并返回结果
        java.util.concurrent.CompletableFuture<String> future = aiService.asyncChat(message);
        String response = future.join(); // 这里简化处理，实际项目中应该返回CompletableFuture

        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("success", true);
        successResponse.put("response", response);
        return ResponseEntity.ok(successResponse);
    }

    /**
     * 带有上下文的聊天功能
     * 
     * @param request 包含message和context字段的请求体
     * @return AI回复
     */
    @PostMapping("/chat/context")
    public ResponseEntity<Map<String, Object>> chatWithContext(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        String context = request.get("context");

        if (message == null || message.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "message不能为空");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        String response = aiService.chatWithContext(message, context);
        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("success", true);
        successResponse.put("response", response);
        return ResponseEntity.ok(successResponse);
    }

    /**
     * 生成结构化响应
     * 
     * @param request 包含message和format字段的请求体
     * @return 结构化的AI回复
     */
    @PostMapping("/generate/structured")
    public ResponseEntity<Map<String, Object>> generateStructuredResponse(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        String format = request.getOrDefault("format", "JSON");

        if (message == null || message.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "message不能为空");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        String response = aiService.generateStructuredResponse(message, format);
        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("success", true);
        successResponse.put("format", format);
        successResponse.put("response", response);
        return ResponseEntity.ok(successResponse);
    }

    /**
     * 提示词优化功能
     * 
     * @param request 包含originalPrompt字段的请求体
     * @return 优化后的提示词
     */
    @PostMapping("/prompt/optimize")
    public ResponseEntity<Map<String, Object>> optimizePrompt(@RequestBody Map<String, String> request) {
        String originalPrompt = request.get("originalPrompt");
        if (originalPrompt == null || originalPrompt.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "originalPrompt不能为空");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        String optimizedPrompt = aiService.optimizePrompt(originalPrompt);
        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("success", true);
        successResponse.put("originalPrompt", originalPrompt);
        successResponse.put("optimizedPrompt", optimizedPrompt);
        return ResponseEntity.ok(successResponse);
    }

    /**
     * 多轮对话中的角色切换示例
     * 
     * @param request 包含messages字段的请求体
     * @return AI回复
     */
    @PostMapping("/chat/multi-role")
    public ResponseEntity<Map<String, Object>> multiRoleChat(@RequestBody Map<String, Object> request) {
        @SuppressWarnings("unchecked")
        List<Map<String, String>> messageMaps = (List<Map<String, String>>) request.get("messages");
        if (messageMaps == null || messageMaps.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "messages不能为空");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        // 转换为Message对象列表
        List<org.springframework.ai.chat.messages.Message> messages = new ArrayList<>();
        for (Map<String, String> messageMap : messageMaps) {
            String role = messageMap.getOrDefault("role", "user");
            String content = messageMap.getOrDefault("content", "");
            if (!content.isEmpty()) {
                switch (role.toLowerCase()) {
                    case "system":
                        messages.add(new org.springframework.ai.chat.messages.SystemMessage(content));
                        break;
                    case "assistant":
                        messages.add(new org.springframework.ai.chat.messages.AssistantMessage(content));
                        break;
                    case "function":
                        // 创建空的函数调用结果映射
                        Map<String, Object> functionResult = new HashMap<>();
                        functionResult.put("content", content);
                        messages.add(new org.springframework.ai.chat.messages.FunctionMessage(
                                messageMap.getOrDefault("name", ""), functionResult));
                        break;
                    default:
                        messages.add(new org.springframework.ai.chat.messages.UserMessage(content));
                }
            }
        }

        String response = aiService.multiRoleChat(messages);
        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("success", true);
        successResponse.put("response", response);
        return ResponseEntity.ok(successResponse);
    }

    /**
     * 清除所有会话接口
     * 
     * @return 操作结果
     */
    @DeleteMapping("/session/all")
    public ResponseEntity<Map<String, Object>> clearAllSessions() {
        aiService.clearAllSessions();
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "所有会话已清除");
        return ResponseEntity.ok(response);
    }

    /**
     * 获取会话数量接口
     * 
     * @return 会话数量
     */
    @GetMapping("/session/count")
    public ResponseEntity<Map<String, Object>> getSessionCount() {
        int count = aiService.getSessionCount();
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("count", count);
        return ResponseEntity.ok(response);
    }

    /**
     * 健康检查接口
     * 
     * @return 服务状态
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("status", "UP");
        response.put("service", "mall-ai");
        return ResponseEntity.ok(response);
    }
}