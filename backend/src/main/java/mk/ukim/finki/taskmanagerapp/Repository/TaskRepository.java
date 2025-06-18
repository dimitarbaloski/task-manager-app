package mk.ukim.finki.taskmanagerapp.Repository;

import mk.ukim.finki.taskmanagerapp.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends MongoRepository<Task, String> {
    Optional<Task> findByTitle(String title);
}
