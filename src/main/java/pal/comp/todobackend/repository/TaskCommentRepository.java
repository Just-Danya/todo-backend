package pal.comp.todobackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pal.comp.todobackend.entity.TaskComment;
import pal.comp.todobackend.entity.Todo;

import java.util.List;

@Repository
public interface TaskCommentRepository extends JpaRepository<TaskComment, Long> {

    List<TaskComment> findByTodoOrderByCreatedAtDesc(Todo todo);

    List<TaskComment> findByUserId(Long userId);
}