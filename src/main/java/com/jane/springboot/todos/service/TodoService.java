package com.jane.springboot.todos.service;

import com.jane.springboot.todos.request.TodoRequest;
import com.jane.springboot.todos.response.TodoResponse;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface TodoService{
    List<TodoResponse> getAllTodos() throws AccessDeniedException;
    TodoResponse createTodo(TodoRequest todoRequest) throws AccessDeniedException;

    TodoResponse toggleIsComplete(Long id) throws AccessDeniedException;
    void deleteTodo(long id) throws AccessDeniedException;


}
