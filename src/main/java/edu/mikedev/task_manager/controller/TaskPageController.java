package edu.mikedev.task_manager.controller;

import edu.mikedev.task_manager.model.Model;

import javax.swing.*;

public abstract class TaskPageController<T extends JPanel> {

    protected T panel;
    protected Model model;
    protected TaskManagerController controller;

    public TaskPageController(T panel, TaskManagerController controller){

        this.panel = panel;
        this.controller = controller;
        this.model = controller.getModel();
    }

    public abstract void setGUIBindings();

}
