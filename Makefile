start:
	make run

run: 
	mvn exec:java -Dexec.mainClass="com.pewpewdungeons.core.Main"
