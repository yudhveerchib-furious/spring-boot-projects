package com.example.Todo_01_app.service;


import com.example.Todo_01_app.Repository.TodoRepo;
import com.example.Todo_01_app.entity.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

  @Autowired
  TodoRepo todoRepo;

 public void saveTodo(Todo todo) {
    todoRepo.save(todo);
 }

    public List<Todo> getAll() {
     List<Todo> st = todoRepo.getAll();
     return st;
    }

    public void updateTodo(Long id, Todo todo) {
        Optional<Todo> res = todoRepo.findById(id);
        if(res.isPresent()) {
            Todo oldTodo = res.get();
            oldTodo.setTaskContent(todo.getTaskContent());
            oldTodo.setCompletionStatus(todo.getCompletionStatus());
            todoRepo.updateTodo(oldTodo);
        }

 }

    public void deleteTodo(Long id) {
       todoRepo.deleteById(id);
    }
}
