## Docker Compose 一些常用指令

# 创建 MySQL 挂载目录（如果之前没创建，可执行，Compose 不会自动创建宿主机目录）
mkdir -p data/mydata/mysql/log data/mydata/mysql/data data/mydata/mysql/conf

# 创建 Redis 挂载目录和配置文件
mkdir -p data/mydata/redis/conf
touch data/mydata/redis/conf/redis.conf



# 启动所有服务（mysql01 和 redis）
docker compose up -d

# 如果是旧版本的 Docker Compose（比如安装的是 docker-compose 二进制文件），需用：
# docker-compose up -d


# 查看服务状态
docker compose ps

# 查看容器日志（比如查看 mysql 日志）
docker compose logs mysql01

# 进入 Redis 客户端（和原有命令一致，也可以用 Compose 命令）
docker exec -it redis redis-cli
# 或用 Compose 命令（更推荐，不用记容器名）
docker compose exec redis redis-cli

# 停止服务（不删除容器）
docker compose stop

# 停止并删除容器、网络（保留数据卷，即宿主机 /mydata 目录的数据）
docker compose down

# 重启服务
docker compose restart