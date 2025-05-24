start:
	make compile run

run: 
	mvn exec:java -Dexec.mainClass="com.pewpewdungeons.Main"

compile:
	mvn compile

mac:
	java -XstartOnFirstThread \
	  -Djava.library.path=/opt/homebrew/lib \
	  -cp "target/classes:target/dependency/*" \
	  com.pewpewdungeons.Main
