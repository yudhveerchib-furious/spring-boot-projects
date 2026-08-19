package com.example.Todo_01_app.controller;

import com.example.Todo_01_app.entity.Todo;
import com.example.Todo_01_app.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TodoController {

    @Autowired
    TodoService todoService;

    @PostMapping("/addtodo")
    public String createTodo(@RequestParam String taskContent) {

        Todo todo = new Todo();
        todo.setTaskContent(taskContent);

        todoService.saveTodo(todo);

        return "redirect:/";
    }
    @PostMapping("/updateTodo/{id}")
    public String updateTodo(@PathVariable("id") Long id,@ModelAttribute Todo todo){
       todoService.updateTodo(id,todo);
       return "redirect:/";
    }

    @GetMapping("/deleteTodo/{id}")
    public String deleteTodo(@PathVariable("id") Long id){
        todoService.deleteTodo(id);
        return "redirect:/";
    }

    @GetMapping("/")
    public String getAllTodo(Model model){

       List<Todo> st =  todoService.getAll();
       model.addAttribute("todoList",st);
       return "task";
    }
}
