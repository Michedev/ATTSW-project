package edu.mikedev.task_manager.controller;

import edu.mikedev.task_manager.model.HibernateDBLayer;
import edu.mikedev.task_manager.model.HibernateModel;
import edu.mikedev.task_manager.model.Model;
import edu.mikedev.task_manager.ui.LoginPage;

import javax.swing.*;
import java.awt.event.ContainerAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TaskManagerController {

    private JFrame window;
    private final Model model;
    private JPanel currentPanel;

    public TaskManagerController(Model model){
        this.model = model;
        initView();
    }

    private void initView() {
        LoginPage loginPage = new LoginPage();
        window = new JFrame();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException|InstantiationException|IllegalAccessException|UnsupportedLookAndFeelException e) {
            // Leave default theme
        }

        window.setTitle("Login page");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(model instanceof HibernateModel){
                    ((HibernateModel) model).getDBLayer().closeConnection();
                }
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

    public JPanel getCurrentPanel() {
        return currentPanel;
    }

    private void setContentPane(JPanel panel){
        window.setContentPane(panel);
        window.pack();
        this.currentPanel = panel;
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
