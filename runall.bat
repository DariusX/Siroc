REM OFF
color 4f
echo ON
call mvn clean package
pause
echo ON
docker build -t tst . 
pause
echo Container for data
docker run -d --name data -v /staging ubuntu:14.04

echo Container for Writer
docker run -d --name w1 --volumes-from data -e MY_ROUTE_NAME=XXX tst

echo Container for Reader
docker run -d --name r1 --volumes-from data -e MY_ROUTE_NAME=reader1 tst
docker run -d --name r2 --volumes-from data -e MY_ROUTE_NAME=reader2 tst
docker ps 
pause 
docker logs r1
docker run -it --name ub --volumes-from data ubuntu:14.04 /bin/bash
