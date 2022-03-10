package edu.mikedev.task_manager.model;

import edu.mikedev.task_manager.Task;
import edu.mikedev.task_manager.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HibernateDBLayer implements DBLayer {

    private Session hibernateSession;
    private Transaction transaction;

    public HibernateDBLayer(Session hibernateSession){
        this.hibernateSession = hibernateSession;
        this.transaction = hibernateSession.beginTransaction();
    }

    @Override
    public User getUserById(int id){
        return hibernateSession.createQuery(String.format("SELECT a from User a where a.id = %d", id), User.class).getResultList().get(0);
    }

    @Override
    public Task getTaskById(int id){
        return hibernateSession.createQuery(String.format("SELECT a from Task a where a.id = %d", id), Task.class).getResultList().get(0);
    }
    
    @Override
    public void add(Task task){
        hibernateSession.persist(task);
    }

    @Override
    public void add(User user){
        hibernateSession.persist(user);
    }

    @Override
    public List<Task> getTasks() {
        return hibernateSession.createQuery("SELECT a FROM Task a", Task.class).getResultList();
    }

    @Override
    public List<Task> getTasks(int userId){
        return hibernateSession.createQuery(String.format("SELECT a FROM Task a where id_user = %d", userId), Task.class).getResultList();
    }

    @Override
    public void delete(User user){
        hibernateSession.delete(user);
        transaction.commit();
    }

    @Override
    public void deleteTask(User taskOwner, Task task){
        taskOwner.getTasks().remove(task);
        hibernateSession.delete(task);
        transaction.commit();
    }

    @Override
    public List<String> getUsernames() {
        return hibernateSession.createQuery("SELECT username from User", String.class).getResultList();
    }

    @Override
    public List<Integer> getUserIds() {
        return hibernateSession.createQuery("SELECT id from User", Integer.class).getResultList();
    }


    @Override
    public List<User> getUsers(String username, String password) {
        return hibernateSession.createQuery(String.format("SELECT a from User a where a.username = '%s' and a.password = '%s'", username, password), User.class).getResultList();
    }

    @Override
    public List<User> getUsers(String username) {
        return hibernateSession.createQuery(String.format("SELECT a from User a where a.username = '%s'", username), User.class).getResultList();
    }

    @Override
    public List<User> getUsers(){
        return hibernateSession.createQuery("SELECT a from User a", User.class).getResultList();

    }

    @Override
    public void update(Task task) {
    }

    @Override
    public Task getTaskByIdWithUserId(int id, int userId) {
        List<Task> resultList = hibernateSession.createQuery(String.format("SELECT a from Task a where id_user = %d and id = %d", userId, id), Task.class).getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }


    @Override
    public List<Integer> getTasksId() {
        return hibernateSession.createQuery("SELECT id from Task", Integer.class).getResultList();
    }

    @Override
    public void closeConnection() {
        transaction.commit();
        hibernateSession.close();
    }

    public Session getSession() {
        return hibernateSession;
    }
}
