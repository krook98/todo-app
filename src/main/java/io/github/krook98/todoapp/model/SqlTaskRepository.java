package io.github.krook98.todoapp.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SqlTaskRepository extends TaskRepository, JpaRepository<Task, Integer> {
}
