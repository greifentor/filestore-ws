cd ..
mvn clean install -Pproduction -Dmaven.test.skip
cd gui-web/
source start.sh
