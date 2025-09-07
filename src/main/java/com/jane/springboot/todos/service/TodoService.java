package com.jane.springboot.todos.service;

import com.jane.springboot.todos.request.TodoRequest;
import com.jane.springboot.todos.response.TodoResponse;

import java.nio.file.AccessDeniedException;

public interface TodoService {
    TodoResponse createTodo(TodoRequest todoRequest) throws AccessDeniedException;
}
