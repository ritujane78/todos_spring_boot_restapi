package com.jane.springboot.todos.controller;

import com.jane.springboot.todos.request.TodoRequest;
import com.jane.springboot.todos.response.TodoResponse;
import com.jane.springboot.todos.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Tag(name = "Todo REST API Endpoints", description = "Operations for managing user todos")
@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @Operation(summary = "Create todo for user", description = "Create todo for the signed-in user.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TodoResponse createTodo(@Valid @RequestBody TodoRequest todoRequest) throws AccessDeniedException {
        return todoService.createTodo(todoRequest);
    }


    @Operation(summary = "Get all todos for user", description = "Fetch all todos for the signed in user")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<TodoResponse> getAllTodos() throws AccessDeniedException {
        return todoService.getAllTodos();
    }

    @Operation(summary = "Update todo for user", description = "Update todo for the signed-in user.")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public TodoResponse toggleTodoComplete(@PathVariable @Min(1) long id) throws AccessDeniedException {
        return todoService.toggleIsComplete(id);
    }

    @Operation(summary = "Delete todo for user", description = "Delete todo for the signed-in user.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void deleteTodo(@PathVariable @Min(1) long id) throws AccessDeniedException {
        todoService.deleteTodo(id);
    }

}
