package edu.mikedev.task_manager;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HibernateDBLayer implements DBLayer {

    private Session hibernateSession;
    private Transaction transaction;
    private User loggedUser;

    public HibernateDBLayer(Session hibernateSession){
        this.hibernateSession = hibernateSession;
        this.transaction = hibernateSession.beginTransaction();
    }

    private void closeTransaction(){
    }

    @Override
    public User getUserByID(int id){
        return hibernateSession.createQuery(String.format("SELECT a from User a where a.id = %d", id), User.class).getResultList().get(0);
    }

    @Override
    public Task getTaskByID(int id){
        List<Task> resultList = hibernateSession.createQuery(String.format("SELECT a from Task a where a.id = %d", id), Task.class).getResultList();
        closeTransaction();
        if(resultList.size() == 0){
            return null;
        }
        return resultList.get(0);
    }
    
    @Override
    public void add(Task task){
        hibernateSession.persist(task);
        closeTransaction();
    }

    @Override
    public void add(User user){
        hibernateSession.persist(user);
        closeTransaction();
    }

    @Override
    public List<Task> getTasks() {
        List<Task> tasks = hibernateSession.createQuery("SELECT a FROM Task a", Task.class).getResultList();
        closeTransaction();
        return tasks;
    }

    @Override
    public List<Task> getTasks(int userId){
        List<Task> result = hibernateSession.createQuery(String.format("SELECT a FROM Task a where id_user = %d", userId), Task.class).getResultList();
        closeTransaction();
        return result;
    }

    @Override
    public void delete(User user){
        hibernateSession.remove(user);
        closeTransaction();
    }

    @Override
    public void delete(Task task){
        hibernateSession.remove(task);
        closeTransaction();
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
    public void update(Task task) {
        hibernateSession.evict(task);
        hibernateSession.update(task);
        closeTransaction();
    }

    @Override
    public Task getTaskByIdWithUserId(int id, int id_user) {
        List<Task> resultList = hibernateSession.createQuery(String.format("SELECT a from Task a where id_user = %d and id = %d", id_user, id), Task.class).getResultList();
        closeTransaction();
        if (resultList.size() == 0) {
            return null;
        }
        return resultList.get(0);
    }


    @Override
    public List<Integer> getTasksId() {
        List<Integer> result = hibernateSession.createQuery("SELECT id from Task", Integer.class).getResultList();
        closeTransaction();
        return result;
    }

}
