all:
	@mvn clean compile exec:java -q

test:
	@echo "Running tests"
	@mvn clean test -q
	@echo "Tests completed."

suite-alltests:
	@echo "Running suite AllTests"
	@mvn clean -Dtest=AllTests test -q
	@echo "Tests completed."

install-deps:
	@echo "Installing dependencies"
	@echo "Installing JDK and Maven"
	@sudo apt update -qq
	@sudo apt install -q openjdk-17-jdk maven -y
