# network_project

여러 유저가 동시에 게임을 진행할 수 있는 영어 단어 배틀 게임

Instruction for server and client

for server
(In short, 0. update configure.txt 1.start rmiregistry, 
2. java ServerHandler, 3. java ChatServer)
0. Before starting server, you should change the configure.txt
to update serverIP and port for both ChatServer and SSLServer
1. open cmd and change the directory to the server directory
1.1 start rmiregistry
2. execute ServerHandler (java ServerHandler)
2.1 wait till you get the message "HelloServer bound in registry"
3. open server with executing ChatServer (java ChatServer)
port will be stored in "configure.txt"

for client
(in short, 1. java ClientHandler, 2. java ChatClient)
1. open cmd and change the directory to the client directory
2. execute ClientHandler (java ClientHandler)
2-1. you will get a message containing 'PW1' 'PW2' )
3. execute ChatClient (java ChatClient)
3.1. you will get a message "input pw1 pw2 \nex1235,4567)"
3.2 then you gotta insert pw1, pw2 by the commend
3.3 make sure if you conform the commend ( use ',' between pw1 and pw2)

-to change the host IP
update the file named "configure.txt"
change the following host IP after serverIP: to a new server IP

-to change the port
update the file named "configure.txt"
change the following port after port: to a new port

*Server must be on before client get connected
*ServerHandler compiles before you start on the server (javac *.java)
*ServerHandler type the security arguments for the ease of using
*if you start to run ServerHandler, the port is binded and will be binded
till you turn off your computer, unless you kill the process of CMD
which is started by ServerHandler.

*ClientHandler type the 
Made by Kim Min-ki : 자체구현 RSA알고리즘을 사용하여 게임 서버 접속 보안 강화
Lee Ji-ho :RMI & SSL 통합한 수정 소켓팩토리를 통해 소켓 생성
from Soongsil University.



