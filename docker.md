docker -it 交互式容器



exit 停止容器

ctrl+P+Q 暂时退出容器 不停止退出

docker attach [Container ID]  重新进入

docker exec -t [Container ID]    命令内容  ls -l /tmp  不进入容器，ctrl方式退出后，在外部执行命令。

docker start [Container ID] 启动容器

docker run 新建并启动容器



docker inspect 查看容器内部进程。



从容器内拷贝文件到主机上（将容器内的数据持久化)

docker cp 容器ID：容器内文件路径

