package com.jane.springboot.todos.controller;

import com.jane.springboot.todos.request.TodoRequest;
import com.jane.springboot.todos.response.TodoResponse;
import com.jane.springboot.todos.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

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
}
