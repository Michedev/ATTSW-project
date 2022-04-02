package edu.mikedev.task_manager.model;

import edu.mikedev.task_manager.data.Task;
import edu.mikedev.task_manager.data.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class HibernateDBLayer implements DBLayer {

    private final SessionFactory hibernateSessionFactory;
    private Session hibernateSession;

    public HibernateDBLayer(SessionFactory sessionFactory){
        this.hibernateSessionFactory = sessionFactory;
        this.hibernateSession = hibernateSessionFactory.openSession();
        hibernateSession.beginTransaction();
    }

    public void commitTransaction(){
        hibernateSession.getTransaction().commit();
        hibernateSession = hibernateSessionFactory.openSession();
        hibernateSession.beginTransaction();
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
        hibernateSession.save(task);
        commitTransaction();
    }

    @Override
    public void add(User user){
        hibernateSession.save(user);
        commitTransaction();
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
        for(Task t: user.getTasks()){
            hibernateSession.createQuery("delete from Task where id = " + t.getId()).executeUpdate();
        }
        hibernateSession.createQuery("delete from User where id = " + user.getId()).executeUpdate();
        commitTransaction();
    }

    @Override
    public void delete(Task task){
        hibernateSession.createQuery("delete from Task where id = " + task.getId()).executeUpdate();
        commitTransaction();
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
        hibernateSession.update(task);
        commitTransaction();
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
        hibernateSession.close();
    }

    public Session getSession() {
        return hibernateSession;
    }
}
