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