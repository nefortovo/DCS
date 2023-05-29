#!/usr/bin/env bash

mvn clean package

echo 'Copy files...'

scp -i C:/id_rsa.pub \
  target/liftcom-0.0.1-SNAPSHOT.jar \
  root@185.177.216.236:/root/

echo 'Restart server...'

ssh -i C:/id_rsa.pub root@192.168.0.107 << EOF

pgrep java | xargs kill -9
nohup java -jar liftcom-0.0.1-SNAPSHOT.jar > log.txt &

EOF

echo 'BB'
