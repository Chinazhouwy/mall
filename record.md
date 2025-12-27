已在此计算机上禁用 Sudo。若要启用它，请转到 Developer Settings page“设置”应用中的

wsl -u root

passwd 用户名

exit



mysqld: Can't read dir of '/etc/mysql/conf.d/' (Errcode: 2 - No such file or directory)

# 创建目录（若不存在）
mkdir -Force ./mydata/mysql/conf
# 创建并编辑my.cnf（可添加自定义配置，比如字符集）
New-Item ./mydata/mysql/conf/my.cnf -ItemType File


renrenfastVue 的 node 版本，最新版本是 14.21.3

# 需要把common提前到开始，否则一直编译不过

```xml
  <modules>
        <module>renren-fast</module>
        <module>renren-generator</module>
        <module>mall-common</module>
        <module>mall-product</module>
        <module>mall-order</module>
        <module>mall-ware</module>
        <module>mall-member</module>
        <module>mall-coupon</module>
    </modules>
```


Caused by: javax.net.ssl.SSLHandshakeException: No appropriate protocol (protocol is disabled or cipher suites are inappropriate)

```
# 禁用 SSL（推荐用于开发环境）
spring.datasource.url=jdbc:mysql://localhost:3306/your_database?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC

# 或启用 SSL 并指定协议版本（推荐用于生产环境）
spring.datasource.url=jdbc:mysql://localhost:3306/your_database?useSSL=true&enabledTLSProtocols=TLSv1.2&serverTimezone=UTC
```




# `bootstrap.properties` 移除时间及原因

## 移除时间

`bootstrap.properties` 在 **Spring Boot 2.4** 版本中被移除。

## 移除原因

### 1. **配置加载机制统一**
- Spring Boot 2.4 引入了新的配置导入机制
- 使用 `spring.config.import` 替代原有的 bootstrap 机制

### 2. **配置优先级简化**
- 原有的 bootstrap 配置具有最高优先级，容易造成配置混乱
- 新机制通过明确的导入顺序来管理配置优先级

### 3. **Cloud 配置集成改进**
- Spring Cloud 配置中心通过 `spring.config.import` 实现更灵活的配置加载
- 避免了 bootstrap 阶段的复杂性

## 替代方案

### 使用 `application.yml` 配置导入
```yaml
spring:
  config:
    import:
      - nacos:mall-product.yaml
```


### 配置文件命名约定
- `application-{profile}.properties/yml`
- 通过 `spring.config.location` 指定配置位置

## 对项目的影响

- 你的项目使用了 `spring.config.import` 机制
- 不需要 `bootstrap.properties` 文件
- 配置加载更加直观和可控

### nacos 配置无法及时刷新问题
- [ ] todo

```text
2025-12-27T13:56:25.067+08:00  WARN 77916 --- [mall-order] [block.monitor.0] c.a.nacos.client.config.impl.CacheData   : [Config-fixed-public-127.0.0.1_8848] [notify-block-monitor] dataId=mall-order.yaml, group=DEFAULT_GROUP,tenant=public, md5=671b1fb9a437bddd12f249f5c63c6725, receiveConfigInfo execute over 60000 mills，thread trace block : 
	at java.base@17.0.17/java.lang.Throwable.fillInStackTrace(Native Method)
	at java.base@17.0.17/java.lang.Throwable.fillInStackTrace(Throwable.java:798)
	at java.base@17.0.17/java.lang.Throwable.<init>(Throwable.java:271)
	at java.base@17.0.17/java.lang.Exception.<init>(Exception.java:67)
	at java.base@17.0.17/java.lang.ReflectiveOperationException.<init>(ReflectiveOperationException.java:57)
	at java.base@17.0.17/java.lang.NoSuchMethodException.<init>(NoSuchMethodException.java:50)
	at ... 
```