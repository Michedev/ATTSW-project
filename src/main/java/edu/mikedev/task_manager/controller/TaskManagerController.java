package edu.mikedev.task_manager.controller;

import edu.mikedev.task_manager.model.Model;
import edu.mikedev.task_manager.ui.LoginPage;

import javax.swing.*;

public class TaskManagerController {

    private final JFrame window;
    private final Model model;
    private JPanel currentPanel;
    private TaskPageController pageController;

    public TaskManagerController(Model model, LoginPage loginPage){
        this.window = new JFrame();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException|InstantiationException|IllegalAccessException|UnsupportedLookAndFeelException e) {
            // Leave default theme
        }

        this.window.setTitle("Login page");
        this.window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.model = model;
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
