package edu.mikedev.task_manager.ui;

import edu.mikedev.task_manager.data.Task;

import java.awt.*;
import java.time.Instant;
import java.util.Date;

public class AppColors {
    public static final Color RED = new Color(255, 107, 108);
    public static final Color ORANGE = new Color(249,212,35);
    public static final Color GREEN = new Color(115, 231, 118);

    private AppColors(){}

    public static  Color getColorBackground(Task task) {
        if(task.isDone()){
            return AppColors.GREEN;
        } else {
            if(task.getDeadline().after(today())){
                return AppColors.ORANGE;
            } else {
                return AppColors.RED;
            }
        }
    }

    public static java.util.Date today() {
        return Date.from(Instant.now());
    }

}
