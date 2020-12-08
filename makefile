NAME = "Snake"

all:
	# (a bit of a hack to compile everything each time ...)
	@echo "Compiling..."
	javac *.java model/*.java view/*.java

run: all
	@echo "Running..."
	java $(NAME)

clean:
	rm -rf *.class
	rm -rf view/*.class
	rm -rf model/*.class