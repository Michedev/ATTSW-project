package edu.mikedev.task_manager;

import org.hibernate.Session;

import java.util.List;

public class HibernateModel implements Model{

    private Session hibernateSession;

    public HibernateModel(Session hibernateSession){

        this.hibernateSession = hibernateSession;
    }

    @Override
    public boolean areCredentialCorrect(String username, String password) {
        return false;
    }

    @Override
    public boolean userExists(String username) {
        return false;
    }

    @Override
    public User registerUser(String username, String password, String email) {
        return null;
    }

    @Override
    public User getUser(String username, String password) {
        return null;
    }

    @Override
    public void updateTask(Task task) {
        if(!existsTaskId(task.getId())){
            throw new IllegalArgumentException("Task id must exists already in DB");
        }
        hibernateSession.update(task);

    }

    @Override
    public void addNewTask(Task newTask) {
        if(existsTaskId(newTask.getId())){
            throw new IllegalArgumentException("Task id must not exists already in DB");
        }
        hibernateSession.persist(newTask);
    }

    @Override
    public void deleteTask(Task task) {
        if(!existsTaskId(task.getId())){
            throw new IllegalArgumentException("Task id must exists already in DB");
        }
        hibernateSession.delete(task);

    }

    @Override
    public boolean existsTaskId(int id) {
        return hibernateSession.createQuery("SELECT id from Task", Integer.class).getResultList().contains(id);
    }

    @Override
    public Task getTaskById(int id) {
        return null;
    }

    @Override
    public List<Task> getTasks() {
        return null;
    }
}
