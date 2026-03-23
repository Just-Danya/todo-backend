package pal.comp.todobackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pal.comp.todobackend.entity.Priority;
import pal.comp.todobackend.entity.Todo;
import pal.comp.todobackend.entity.User;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByCompleted(boolean completed);

    List<Todo> findByPriority(Priority priority);

    List<Todo> findByCreatedBy(User user);

    List<Todo> findByAssignedTo(User user);

    @Modifying
    @Transactional
    @Query("UPDATE Todo t SET t.completed = true WHERE t.id = :id")
    int markAsCompleted(@Param("id") Long id);  // ← ДОБАВЬТЕ ЭТОТ МЕТОД
}