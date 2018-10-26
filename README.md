# Log Parser

Loads a log file to a database and searches according to supplied params

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites
For Running:

Java 8 (JRE/JDK)

For Building:
Maven 3+




### Installing

A step by step series of examples that tell you how to get a development env running

To Run an already build jar, to build the database

```
java -cp "parser.jar" com.ef.Parser --accesslog=/path/to/file --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100 
```

To query the database that has already been built

```
java -cp "parser.jar" com.ef.Parser --startDate=2017-01-01.15:00:00 --duration=hourly --threshold=200
```
Sample output

```
2018-10-26 18:16:32.279  INFO 12788 --- [           main] com.ef.services.LogService               : loading line: Log [date=Sun Jan 01 23:59:02 CAT 2017, ip=192.168.245.128, request=GET / HTTP/1.1, status=200, userAgent=Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0; EIE10;ENUSWOL)].
2018-10-26 18:16:32.279  INFO 12788 --- [           main] com.ef.services.LogService               : loading line: Log [date=Sun Jan 01 23:59:02 CAT 2017, ip=192.168.159.115, request=GET / HTTP/1.1, status=200, userAgent=Mozilla/5.0 (Linux; Android 6.0.1; Nexus 5 Build/MRA58N) AppleWebKit/537.36(KHTML, like Gecko) Chrome/59.0.3033.0 Mobile Safari/537.36].
2018-10-26 18:16:32.279  INFO 12788 --- [           main] com.ef.services.LogService               : loading line: Log [date=Sun Jan 01 23:59:03 CAT 2017, ip=192.168.244.37, request=GET / HTTP/1.1, status=200, userAgent=Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.91 Mobile Safari/537.36].
2018-10-26 18:16:32.280  INFO 12788 --- [           main] com.ef.services.LogService               : loading line: Log [date=Sun Jan 01 23:59:03 CAT 2017, ip=192.168.25.118, request=GET / HTTP/1.1, status=200, userAgent=Mozilla/5.0 (iPad; CPU OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1].

```

## Building from scratch


o cd to root of project

```
mvn clean install
```
then run the generated jar as normal



## Built With

* [Spring Boot](https://spring.io/projects/spring-boot) - Spring framework
* [Maven](https://maven.apache.org/) - Dependency Management
* [Hibernate](http://hibernate.org/) - Object Relational Mapping framework



## Authors

* **Phumzile Saleni** - *Initial work* - [thasaleni](https://github.com/thasaleni)



## License

N/A




