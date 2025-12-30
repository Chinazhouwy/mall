# mall 项目 README

## 项目介绍

谷粒商城的仿写和后续升级项目，基于 Spring Boot 技术栈构建的电商系统。

## 技术架构

### 后端技术栈
- **Spring Boot**：核心框架
- **Spring AI**：集成 AI 对话功能
- **Java**：主要开发语言

### AI 集成
- **Qwen 阿里千问**：集成阿里云千问大模型
- **DeepSeek**：集成 DeepSeek 对话模型
- 提供 `/api/ai/qwen/chat` 等 AI 对话接口

## 项目结构

```
mall/
├── mall-ai/                 # AI 功能模块
│   └── src/main/java/com/atguigu/mall/ai/
│       └── controller/AiController.java    # AI 对话控制器
└── README.md               # 项目说明文档
```


## 功能特性

- **AI 对话服务**：集成多种大语言模型
- **RESTful API**：提供标准化接口
- **模块化设计**：便于功能扩展和维护

## 安装教程

1. **环境准备**
    - JDK 17+
    - Maven 3.6+
    - Git

2. **项目克隆**
   ```bash
   git clone <repository-url>
   ```


3. **依赖安装**
   ```bash
   cd mall
   mvn clean install
   ```


4. **配置修改**
    - 修改 `application.yml` 配置文件
    - 配置 AI 服务 API 密钥

## 使用说明

1. **启动项目**
   ```bash
   mvn spring-boot:run
   ```


2. **AI 对话接口**
    - **Qwen 对话**：`POST /api/ai/qwen/chat`
    - 请求参数：`{"message": "对话内容"}`

3. **服务验证**
    - 访问 AI 接口测试大模型对话功能

## 项目配置

### AI 服务配置
- 配置阿里千问 API 密钥
- 配置 DeepSeek API 密钥
- 验证 API 端点连通性

### 环境变量
- 设置必要的 API 密钥环境变量
- 配置服务端口和连接参数

## 参与贡献

1. Fork 本仓库
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request

## 开源协议

遵循项目原定开源协议，具体请参见项目根目录下的协议文件。