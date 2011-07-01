.phony: all docs

all:
	javac -d ./bin -cp ./jars/json.jar:./jars/joda-time-1.6.2.jar:./jars/twitter4j-core-2.2.3-SNAPSHOT.jar -sourcepath ./src  ./src/ca/uwaterloo/cs/cs349/mikrocalendar/events/*java ./src/ca/uwaterloo/cs/cs349/mikrocalendar/events/*/*java ./src/ca/uwaterloo/cs/cs349/mikrocalendar/ui/*java 
#Run MikroCalendar
	java -cp ./bin:./jars/json.jar:./jars/joda-time-1.6.2.jar:./jars/twitter4j-core-2.2.3-SNAPSHOT.jar ca.uwaterloo.cs.cs349.mikrocalendar.ui.Main

docs:
	javadoc -d ./javadocs/ -classpath ./jars/json.jar:./jars/joda-time-1.6.2.jar:./jars/twitter4j-core-2.2.3-SNAPSHOT.jar -sourcepath ./src  ./src/ca/uwaterloo/cs/cs349/mikrocalendar/events/*java ./src/ca/uwaterloo/cs/cs349/mikrocalendar/events/*/*java ./src/ca/uwaterloo/cs/cs349/mikrocalendar/ui/*java 
