package edu.mikedev.task_manager;

import edu.mikedev.task_manager.model.HibernateModel;
import edu.mikedev.task_manager.model.Model;
import edu.mikedev.task_manager.utils.HibernateDBUtils;
import org.jbehave.core.annotations.Given;

public class E2EStory {

    Model model;

    @Given("an authenticated user with name \"$username\" and password \"$password\"")
    public void authUser(String username, String password){
        HibernateDBUtils utils = new HibernateDBUtils();
        model = new HibernateModel(utils.getSession());
        model.loginUser(username, password);
    }
}
