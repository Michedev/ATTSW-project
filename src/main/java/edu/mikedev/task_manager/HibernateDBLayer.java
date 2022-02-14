package edu.mikedev.task_manager;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateDBLayer {

    private Session hibernateSession;
    private Transaction transaction;
    private User loggedUser;

    public HibernateDBLayer(Session hibernateSession){
        this.hibernateSession = hibernateSession;
        this.transaction = hibernateSession.beginTransaction();
    }
    
    public User getUserByID(int id){
        return hibernateSession.createQuery(String.format("SELECT a from User a where a.id = %d", id), User.class).getResultList().get(0);
    }

    public Task getTaskByID(int id){
        return hibernateSession.createQuery(String.format("SELECT a from Task a where a.id = %d", id), Task.class).getResultList().get(0);
    }
    
    public void addTask(Task task){
        hibernateSession.persist(task);
    }

    public void addUser(User user){
        hibernateSession.persist(user);
    }


}
