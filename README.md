# Metric Service
This web application allows user to create and manage multiple metrics and request statistics.

## APIs
This project has 5 APIs to perform various operations
````
GET Metrics - Returns all the metrics from the db 
localhost:8080/metrics

Space Complexity : O(n) - space for n records
Time Complexity : O(n) - visiting n records
````


````
GET Metrics by metric-name - Returns all the metrics from the db for given metric-name
localhost:8080/metrics/{metric}

Space Complexity : O(n) - space for (n-x ~n) records where n is total records and x is records for metric-name
Time Complexity : O(n) - visiting (n-x ~n) records


````

````
POST Metrics - Create Metrics with given request containing metric name and decimal values
localhost:8080/metrics
{
        "metric": "Facebook",
        "values": [1.1, 1, 1.3, 3.6, -3.2]
}

Space Complexity : O(n) - space for n records
Time Complexity : O(n) - visiting n records
````

````
POST Metrics - Add a value to Metric. Creates a new metric if the metric does not exist already.
localhost:8080/metrics/{metric}/{value}

Space Complexity : O(1) - space for only one record
Time Complexity : O(1) - visiting only one record

````

````
GET Statistics - Returns summary of statistics including min, max, mean and median for given metric-name
localhost:8080/metrics/statistics/{metric}

Space Complexity : O(n) - space for n records
Time Complexity : O(nLogn) - sorting (nlogn) followed by visiting n records(Min : O(1), Max O(1), Mean O(n), Median O(1)).

````

## Build and Execution
This is a standard SpringBoot-maven application and can be built via simple [maven](https://maven.apache.org/install.html) commands. 
```
mvn clean
mvn test
mvn clean install
```
*The project SCM url can be further used to deploy to jenkins via freestyle or pipeline projects.*

Execute from command line
```
cd {PROJECT/TARGET}
java -jar metrics-service-0.0.1-SNAPSHOT.jar 
```

Execute from eclipse/ STS IDE
```
Right click on the project -> Run as Spring Boot app
```
## Snapshots

![image](https://user-images.githubusercontent.com/18723463/53688385-2706b480-3d11-11e9-9dc2-f2ba7ea0ce74.png)
```
```
![image](https://user-images.githubusercontent.com/18723463/53688401-5ddcca80-3d11-11e9-9c98-cab53e817bb4.png)

```
Running via STS IDE
```

![image](https://user-images.githubusercontent.com/18723463/53688430-c5931580-3d11-11e9-926d-cdda263fec61.png)


```
Running via command line
```
![image](https://user-images.githubusercontent.com/18723463/53688454-3a664f80-3d12-11e9-8482-244ac002a95d.png)


```
Test Cases and Code coverage
```
![image](https://user-images.githubusercontent.com/18723463/53688479-8addad00-3d12-11e9-9338-67a5bdb9840e.png)


```
Swagger UI integration
http://localhost:8080/swagger-ui.html#/metric-controller
```
![image](https://user-images.githubusercontent.com/18723463/53688493-b8c2f180-3d12-11e9-8711-22320c5127cd.png)


```
Postman APIs
https://www.getpostman.com/collections/58be6dc6e04d49d8e2f1
```
![image](https://user-images.githubusercontent.com/18723463/53688505-08092200-3d13-11e9-9a3e-4c307df6fa2c.png)


```
```
