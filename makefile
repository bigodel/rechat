CC=javac -d out -cp out/
JAVA=java

all: server client

clean:
	rm out/*
	rm -d out/

run-server: server
	@clear
	@${JAVA} -classpath out Server

run-client: client
	@clear
	@${JAVA} -classpath out Client

protocol: src/protocol/Chat.java src/protocol/Protocol.java
	@mkdir -p out/
	${CC} $?

server: src/server/Server.java protocol
	${CC} $<

client: src/client/Client.java protocol
	${CC} $<
