package edu.mikedev.task_manager.controller;

import edu.mikedev.task_manager.model.HibernateModel;
import edu.mikedev.task_manager.model.Model;
import edu.mikedev.task_manager.ui.LoginWindow;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TaskManagerController {

    public TaskManagerController(){
        Model model = new HibernateModel(buildHBSession());
        LoginWindow window = new LoginWindow(model);

        window.pack();
        window.setVisible(true);
    }

    public static Session buildHBSession(){
        Path testResourceDirectory = Paths.get("src", "main", "resources");
        File hibernateConfigFile = new File(testResourceDirectory.resolve("hibernate.cfg.xml").toAbsolutePath().toString());

        Configuration cfg = new Configuration();
        SessionFactory factory = cfg.configure(hibernateConfigFile).buildSessionFactory();

        return factory.openSession();
    }

}
