start:
	make run

run: 
	mvn exec:java -Dexec.mainClass="com.pewpewdungeons.Main"

mac:
	java -XstartOnFirstThread \
	  -Djava.library.path=/opt/homebrew/lib \
	  -cp "target/classes:target/dependency/*" \
	  com.pewpewdungeons.Main
