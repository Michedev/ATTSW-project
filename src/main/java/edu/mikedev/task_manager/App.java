package edu.mikedev.task_manager;
import edu.mikedev.task_manager.controller.TaskManagerController;
import edu.mikedev.task_manager.model.HibernateDBLayer;
import edu.mikedev.task_manager.model.ModelImpl;
import edu.mikedev.task_manager.model.Model;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class App {

    public static void main(String[] args) {
        Configuration cfg = new Configuration();
        SessionFactory factory = cfg.configure("hibernate.cfg.xml").buildSessionFactory();

        Model model = new ModelImpl(new HibernateDBLayer(factory));
        TaskManagerController controller = new TaskManagerController(model);

        controller.getWindow().pack();
        controller.getWindow().setVisible(true);
    }
}
