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
