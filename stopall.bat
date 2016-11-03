REM OFF
color 
echo ON
docker stop w1
docker rm w1
docker stop r1
docker rm r1
docker stop r2
docker rm r2
docker stop data
docker rm data
docker stop ub
docker rm ub

pause 
docker ps -a

