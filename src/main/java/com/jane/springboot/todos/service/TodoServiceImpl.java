package com.jane.springboot.todos.service;

import com.jane.springboot.todos.entity.Todo;
import com.jane.springboot.todos.entity.User;
import com.jane.springboot.todos.repository.TodoRepository;
import com.jane.springboot.todos.request.TodoRequest;
import com.jane.springboot.todos.response.TodoResponse;
import com.jane.springboot.todos.util.FindAuthenticatedUser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService{

    private final TodoRepository todoRepository;

    private final FindAuthenticatedUser findAuthenticatedUser;

    public TodoServiceImpl(TodoRepository todoRepository, FindAuthenticatedUser findAuthenticatedUser) {
        this.todoRepository = todoRepository;
        this.findAuthenticatedUser = findAuthenticatedUser;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoResponse> getAllTodos() throws AccessDeniedException {
        User user = findAuthenticatedUser.getAuthenticatedUser();

        return (todoRepository.findByOwner(user)
                .stream()
                .map(this::convertToTodoResponse)
                .toList()
                );
    }

    @Override
    @Transactional
    public TodoResponse createTodo(TodoRequest todoRequest) throws AccessDeniedException {
        User currentUser = findAuthenticatedUser.getAuthenticatedUser();

        Todo savedTodo = new Todo(
                todoRequest.getTitle(),
                todoRequest.getDescription(),
                todoRequest.getPriority(),
                false,
                currentUser
        );
        todoRepository.save(savedTodo);

        return convertToTodoResponse(savedTodo);
    }

    @Override
    @Transactional
    public TodoResponse toggleIsComplete(Long id) throws AccessDeniedException {
        User user = findAuthenticatedUser.getAuthenticatedUser();
        Optional<Todo> todo = todoRepository.findByIdAndOwner(id, user);

        if(todo.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found.");
        }
        todo.get().setComplete(!todo.get().isComplete());
        Todo savedTodo = todoRepository.save(todo.get());

        return convertToTodoResponse(savedTodo);
    }

    @Override
    @Transactional
    public void deleteTodo(long id) throws AccessDeniedException {
        User user = findAuthenticatedUser.getAuthenticatedUser();
        Optional<Todo> todo = todoRepository.findByIdAndOwner(id, user);

        if(todo.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found.");
        }
        todo.get().setComplete(!todo.get().isComplete());

        todoRepository.delete(todo.get());

    }

    private TodoResponse convertToTodoResponse(Todo todo){
        return new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.getPriority(),
                todo.isComplete()
        );
    }
}
