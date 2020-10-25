### Randomizer

* Randomizer service is running in AWS EC2 instance

```
http://ec2-35-174-165-42.compute-1.amazonaws.com/swagger-ui.html#/
```

### Docker run in local
* Execute `sh run_docker.sh`
[Note:  The above command might take a min]


### Manual Run
* The app uses `mysql`
* Create a database `randomizer`
* Go to `application.yml` in path `.src/main/resources/application.yml`
* Update the username and password
* Run `sh run_jar.sh` 
