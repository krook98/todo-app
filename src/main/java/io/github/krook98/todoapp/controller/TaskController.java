package io.github.krook98.todoapp.controller;

import io.github.krook98.todoapp.model.Task;
import io.github.krook98.todoapp.model.TaskRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
class TaskController {
    private final static Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository repository;

    TaskController(final TaskRepository repository) {
        this.repository = repository;
    }


    @RequestMapping(value = "/tasks", params = {"!sort", "!page", "!size"}, method = RequestMethod.GET)
    ResponseEntity<List<Task>> readAllTasks() {
        logger.warn("Exposing all the tasks!");
        return ResponseEntity.ok(repository.findAll());
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    ResponseEntity<List<Task>> readAllTasks(Pageable page) {
        logger.warn("Custom pager");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.GET)
    ResponseEntity<?> readOneTask(@PathVariable int id, @RequestBody Task toRead) {
        if(!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(repository.findById(id));
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.POST)
    ResponseEntity<?> postTask(@RequestBody @Valid Task toPost) {
        Task result = repository.save(toPost);
        return ResponseEntity.created(URI.create("/" + result.getId())).build();
    }
    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.PUT)
    ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody Task toUpdate) {
        if(!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        toUpdate.setId(id);
        repository.save(toUpdate);
        return ResponseEntity.noContent().build();
    }
}
