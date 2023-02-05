cd ..
mvn clean install -Pproduction -Dmaven.test.skip
cd gui-web/
java -jar target/filestore-ws-gui-0.0.1.jar
