package com.example.Todo_01_app.Repository;

import com.example.Todo_01_app.entity.Todo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class TodoRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(Todo todo) {
        entityManager.persist(todo);
    }


    public List<Todo> getAll() {
       TypedQuery<Todo> typedQuery =  entityManager.createQuery("select t from Todo t",Todo.class);
       return typedQuery.getResultList();
    }

    public Optional<Todo> findById(Long id) {
        Todo todo =  entityManager.find(Todo.class,id);
        return Optional.ofNullable(todo);

    }

    @Transactional
    public void updateTodo(Todo newTodo) {
       entityManager.merge(newTodo);
    }

    @Transactional
    public void deleteById(Long id) {
       Todo todo =  entityManager.find(Todo.class,id);
       if(todo != null) {
           entityManager.remove(todo);
       }
    }
}
