# Kafka Program for Producer & Consumer [![LICENSE](https://img.shields.io/github/license/mashape/apistatus.svg)](https://github.com/anish-shekhawat/bigdata-kafka#license)

Program for a Kafka Producer to publish log messages into a stream and a Kafka Consumer that subscribes for those log messages and the messages are stored to a file if an absolute value of a threshold is exceeded. The motivation is to build an alerting system for a set of IoT devices.

Log data is generated in the Kafka Producer in the following format:

`pump_id, time_stamp, vibration_delta`

1. `pump_id` is an integer provided as a command-line argument.
2. `time_stamp` is provided by `java.util.Data.getTime()`
3. `vibration_delta` is sampled from `org.apache.commons.math3.distribution.NormalDistribution` with `mean=0` and `standard deviation=0.2`

Producer runs in an infinite loop generating one log message per second, writing to a Kafka topic specified at the command line.

Consumer too runs in an infinite loop reading messages from a Kafka topic specified at command line and determines if the absolute value of `vibration_delta` is greater than a threshold specified at command line. If so, it then writes the entire log message to a file specified at command line.


## Installation

Download & configure MapR Sandbox

1. Download the MapRSandbox from [MapR](https://www.mapr.com/products/mapr-sandbox-hadoop/download)
2. Follow the instructions from [here](http://maprdocs.mapr.com/home/SandboxHadoop/c_sandbox_overview.html) to install and configure the sandbox.

Install maven

1. Login to Sandbox as root user
```bash
ssh -p 2222 root@localhost
``` 

2. Download and install maven
```bash
wget http://repos.fedorapeople.org/repos/dchen/apache-maven/epel-apache-maven.repo -O /etc/yum.repos.d/epel-apache-maven.repo
yum install apache-maven
```

## Usage

1. Login to Sandbox as user01 user.
```bash
ssh -p 2222 user01@localhost
```
2. Download and extract Apache Kafka
```bash
wget http://apache.claz.org/kafka/0.10.0.0/kafka_2.11-0.10.0.0.tgz
tar -xzf kafka_2.11-0.10.0.0.tgz
```
3. Start the zookeeper service
```bash
cd kafka_2.11-0.10.0.0
bin/zookeeper-server-start.sh config/zookeeper.properties > /tmp/zk.out 2>&1 &
```
4. Start the Kafka Sevice
```bash
bin/kafka-server-start.sh config/server.properties > /tmp/kafka.out 2>&1 &
```
5. Create a Topic
```bash
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic <topic-name>
```
6. Build the project
```bash
cd ~/bigdata-kafka
mvn clean install
```
7. Run the Producer
```bash
java -cp target/lab2-1.0-SNAPSHOT-jar-with-dependencies.jar edu.sjsu.cs185.Producer <topic-name> <pump-id>
```
8. Open a second terminal window to the Sandbox
```bash
ssh -p 2222 user01@localhost
```
9. Start a Consumer
```bash
cd ~/bigdata-kakfka
java -cp target/lab2-1.0-SNAPSHOT-jar-with-dependencies.jar \ edu.sjsu.cs185.Consumer <topic-name> <threshold-value> <file-name>
```
10. Open a third terminal window and get running tail of output file in third window.
```bash
tail -f <file-name>
```
    
## Contributing

#### Bug Reports & Feature Requests

Please use the [issue tracker](https://github.com/anish-shekhawat/bigdata-kafka/issues) to report any bugs or file feature requests.

#### Developing

1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request :D

## License

>You can check out the full license [here](https://github.com/anish-shekhawat/bigdata-kafka/blob/master/LICENSE)

This project is licensed under the terms of the **MIT** license.
