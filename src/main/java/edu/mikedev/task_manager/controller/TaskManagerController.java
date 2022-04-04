package edu.mikedev.task_manager.controller;

import edu.mikedev.task_manager.model.ModelImpl;
import edu.mikedev.task_manager.model.Model;
import edu.mikedev.task_manager.ui.LoginPage;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TaskManagerController {

    private JFrame window;
    private final Model model;

    public TaskManagerController(Model model){
        this.model = model;
        initView();
    }

    private void initView() {
        LoginPage loginPage = new LoginPage();
        window = new JFrame();
        window.setTitle("Login page");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ((ModelImpl) model).getDBLayer().closeConnection();
            }
        });

        LoginPageController pageController = new LoginPageController(loginPage, this);
        setContentPane(pageController, "Login page");
    }

    public Model getModel() {
        return model;
    }

    public JFrame getWindow() {
        return window;
    }

    private void setContentPane(JPanel panel){
        window.setContentPane(panel);
        window.pack();
    }

    private void setContentPane(JPanel panel, String title){
        setContentPane(panel);
        window.setTitle(title);
    }

    public <T extends JPanel> void setContentPane(TaskPageController<T> pageController, String title){
        setContentPane(pageController.panel, title);
        pageController.setGUIBindings();
    }


}
