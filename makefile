.PHONY: all run clean

MVN=mvn

JAVA = java -jar Projet/target/*.jar

all:
	cd Projet && mvn clean compile assembly:single

run : all
	$(JAVA)
