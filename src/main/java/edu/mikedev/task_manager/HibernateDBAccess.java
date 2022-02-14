package edu.mikedev.task_manager;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateDBAccess {

    private Session hibernateSession;
    private Transaction transaction;
    private User loggedUser;

    public HibernateDBAccess(Session hibernateSession){
        this.hibernateSession = hibernateSession;
        this.transaction = hibernateSession.beginTransaction();
    }


    public getUserByID(int id){

    }
}
