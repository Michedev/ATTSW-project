package edu.mikedev.task_manager;

import edu.mikedev.task_manager.controller.TaskManagerController;
import edu.mikedev.task_manager.data.Task;
import edu.mikedev.task_manager.model.DBLayer;
import edu.mikedev.task_manager.model.HibernateDBLayer;
import edu.mikedev.task_manager.model.ModelImpl;
import edu.mikedev.task_manager.model.Model;
import edu.mikedev.task_manager.utils.HibernateDBUtils;
import org.assertj.swing.core.matcher.DialogMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.jbehave.core.annotations.*;
import org.jbehave.core.steps.Steps;
import org.junit.Assert;

import javax.swing.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Optional;

public class BDDSteps extends Steps {

    Model model;
    DBLayer dbLayer;
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

    @Given("an unauthenticated user")
    public void unauthUser(){

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

        Task task = findExistingTaskByName(taskTitle);
        Assert.assertEquals(taskDescription, task.getDescription());
        Assert.assertEquals(simpleDateFormat.format(task.getDeadline()), taskDeadline);
        Assert.assertEquals(ownerUsername, task.getUser().getUsername());

        Task taskDB = utils.getTaskById(task.getId());
        Assert.assertEquals(model.getLoggedUser().getId(), taskDB.getUser().getId());
    }

    @When("it modifies the task with name \"$oldTaskName\" to \"$newTaskName\"")
    public void modifyTask(String oldTaskName, String newTaskName){
        Task task = findExistingTaskByName(oldTaskName);
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

        Task dbTask = utils.getTaskById(optionalTask.get().getId());
        Assert.assertNotNull(dbTask);
    }

    @When("it deletes a task called \"$taskTitle\"")
    public void deleteTask(String taskName){
        Task task = findExistingTaskByName(taskName);
        frame.panel(String.format("task%d", task.getId())).click();
        frame.button("btnDelete").click();
    }

    private Task findExistingTaskByName(String taskName) {
        Optional<Task> optionalTask = model.getUserTasks().stream().filter(t -> t.getTitle().equals(taskName)).findFirst();
        Assert.assertTrue(optionalTask.isPresent());
        return optionalTask.get();
    }

    @When("it completes a task called \"$taskTitle\"")
    public void setCheckbox(String taskTitle){
        Task task = findExistingTaskByName(taskTitle);
        frame.panel(String.format("task%d", task.getId())).click();
        frame.checkBox("cbDone").check();
    }

    @When("it register with username \"$username\", password \"$password\" and email \"$email\"")
    public void register(String username, String password, String email){
        frame.button("btnRegister").click();
        frame.textBox("tfUsername").enterText(username);
        frame.textBox("tfPassword").enterText(password);
        frame.textBox("tfEmail").enterText(email);

        frame.button("btnConfirmRegister").click();

        frame.dialog(DialogMatcher.withTitle("Registration completed")).close();
    }

    @When("it login with username \"$username\" and password \"$password\"")
    public void loginUser(String username, String password){
        frame.textBox("tfUsername").click().enterText(username);
        frame.textBox("tfPassword").click().enterText(password);

        frame.button("btnLogin").click();
    }
    @Then("the task \"$taskTitle\" should be done")
    public void isTaskChecked(String taskTitle){
        Task task = findExistingTaskByName(taskTitle);
        Assert.assertTrue(task.isDone());

        Task dbTask = utils.getTaskById(task.getId());
        Assert.assertTrue(dbTask.isDone());
    }

    @AfterScenario
    public void closeSession() {
        dbLayer.closeConnection();
        frame.cleanUp();
    }

    @BeforeScenario
    public void setUpGUI() {
        utils = new HibernateDBUtils();

        try {
            utils.initDBTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ModelImpl hbModel = new ModelImpl(new HibernateDBLayer(utils.getSessionFactory()));
        dbLayer = hbModel.getDBLayer();
        model = hbModel;
        GuiActionRunner.execute(() ->{
            controller = new TaskManagerController(model);
            return controller.getWindow();
        });
        window = controller.getWindow();
        frame = new FrameFixture(window);
        frame.show();
    }

}
