package edu.mikedev.task_manager;

import edu.mikedev.task_manager.controller.TaskManagerController;
import edu.mikedev.task_manager.model.HibernateModel;
import edu.mikedev.task_manager.model.Model;
import edu.mikedev.task_manager.utils.HibernateDBUtils;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.jbehave.core.annotations.*;
import org.jbehave.core.steps.Steps;
import org.junit.Assert;
import org.junit.runner.RunWith;

import javax.swing.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

public class BDDSteps extends Steps {

    Model model;
    JFrame window;
    TaskManagerController controller;
    FrameFixture frame;
    HibernateDBUtils utils;


    @Given("an authenticated user with name \"$username\" and password \"$password\"")
    public void authUser(String username, String password){
        frame.textBox("tfUsername").click().enterText(username);
        frame.textBox("tfPassword").click().enterText(password);

        frame.button("btnLogin").click();
    }

    @When("it makes a new task called \"$taskTitle\" with description \"$taskDescription\" and deadline date \"$taskDeadline\"")
    public void makeNewTask(String taskTitle, String taskDescription, String taskDeadline){
        frame.button("btnNewTask").click();

        frame.textBox("tfTaskName").enterText(taskTitle);
        frame.textBox("tfTaskDescription").enterText(taskDescription);
        frame.textBox("tfTaskDeadline").enterText(taskDeadline);

        frame.button("btnSave").click();
    }

    @Then("there should be a task with name \"$taskTitle\", description \"$taskDescription\" and \"$taskDeadline\" deadline and the secondary key of \"$ownerUsername\"")
    public void assertTaskPresence(String taskTitle, String taskDescription, String taskDeadline, String ownerUsername){
        Assert.assertEquals(ownerUsername, model.getLoggedUser().getUsername());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        List<Task> userTasks = model.getUserTasks();
        System.out.print("user tasks: ");
        System.out.println(userTasks);
        Optional<Task> optionalTask = userTasks.stream().filter(t -> t.getTitle().equals(taskTitle)).findFirst();
        if(optionalTask.isPresent()){
            Task task = optionalTask.get();
            Assert.assertEquals(taskDescription, task.getDescription());
            Assert.assertEquals(simpleDateFormat.format(task.getDeadline()), taskDeadline);
            Assert.assertEquals(ownerUsername, task.getUser().getUsername());
        } else {
            Assert.fail();
        }
    }

    @When("it modifies the task with name \"$oldTaskName\" to \"$newTaskName\"")
    public void modifyTask(String oldTaskName, String newTaskName){
        Optional<Task> optionalTask = model.getUserTasks().stream().filter(t -> t.getTitle().equals(oldTaskName)).findFirst();
        Assert.assertTrue(optionalTask.isPresent());
        Task task = optionalTask.get();
        frame.panel("task" + task.getId()).click();
        frame.button("btnUpdate").click();
        frame.textBox("tfTaskName").deleteText().enterText(newTaskName);
        frame.button("btnSave").click();
    }

    @Then("the task \"$taskName\" should not exists into the DB")
    public void taskNotExistsIntoTheDB(String taskName){
        Optional<Task> optionalTask = model.getUserTasks().stream().filter(t -> t.getTitle().equals(taskName)).findFirst();
        Assert.assertFalse(optionalTask.isPresent());
    }

    @Then("the task \"$taskName\" should exists into the DB")
    public void taskExistsIntoTheDB(String taskName){
        Optional<Task> optionalTask = model.getUserTasks().stream().filter(t -> t.getTitle().equals(taskName)).findFirst();
        Assert.assertTrue(optionalTask.isPresent());
    }

    @When("it deletes a task called \"$taskTitle\"")
    public void deleteTask(String taskName){
        Optional<Task> optionalTask = model.getUserTasks().stream().filter(t -> t.getTitle().equals(taskName)).findFirst();
        Assert.assertTrue(optionalTask.isPresent());
        Task task = optionalTask.get();
        frame.panel(String.format("task%d", task.getId())).click();
        frame.button("btnDelete").click();
    }

    @AfterScenario
    public void closeSession() {
        utils.getSession().close();
        frame.cleanUp();
    }

    @BeforeScenario
    public void setUpGUI() {
        utils = new HibernateDBUtils(HibernateDBUtils.buildHBSession());

        try {
            utils.initRealTestDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        model = new HibernateModel(utils.getSession());
        GuiActionRunner.execute(() ->{
            controller = new TaskManagerController(model);
            return controller.getWindow();
        });
        window = controller.getWindow();
        frame = new FrameFixture(window);
        frame.show();
    }

}
