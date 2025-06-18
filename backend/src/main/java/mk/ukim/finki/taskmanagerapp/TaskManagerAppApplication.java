package mk.ukim.finki.taskmanagerapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "mk.ukim.finki.taskmanagerapp.Repository")
@EnableJpaRepositories(basePackages = {})
public class TaskManagerAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskManagerAppApplication.class, args);
    }
}
