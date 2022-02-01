package edu.mikedev.task_manager.ui;

import edu.mikedev.task_manager.Task;

import java.awt.*;
import java.sql.Date;
import java.time.Instant;

public class AppColors {
    public static final Color RED = new Color(255, 107, 108);
    public static final Color ORANGE = new Color(249,212,35);
    public static final Color GREEN = new Color(225,245,196);

    public static  Color getColorBackground(Task task) {
        if(task.isDone()){
            return AppColors.GREEN;
        } else {
            if(task.getDeadline().after(Date.from(Instant.now()))){
                return AppColors.ORANGE;
            } else {
                return AppColors.RED;
            }
        }
    }

}
