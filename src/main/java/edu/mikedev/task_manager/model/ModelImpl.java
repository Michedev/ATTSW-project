package edu.mikedev.task_manager.model;

import edu.mikedev.task_manager.data.Task;
import edu.mikedev.task_manager.data.User;
import org.hibernate.SessionFactory;

import java.util.HashSet;
import java.util.List;


public class ModelImpl implements Model{

    private static final String MSG_ERROR_USER_NOT_LOGGED = "You should login before calling this method";
    private User loggedUser;
    private DBLayer dbLayer;

    public ModelImpl(DBLayer dbLayer){
        this.dbLayer = dbLayer;
    }

    @Override
    public boolean areCredentialCorrect(String username, String password) {
        List<User> users = dbLayer.getUsers(username, password);
        return !users.isEmpty();
    }

    @Override
    public boolean userExists(String username) {
        List<User> users = dbLayer.getUsers(username);
        return !users.isEmpty();
    }

    @Override
    public User registerUser(String username, String password, String email) {
        User newUser = new User(username, password, email);
        return registerUser(newUser);
    }

    @Override
    public User registerUser(User newUser) {
        newUser.setTasks(new HashSet<>());
        if(userExists(newUser.getUsername())){
            throw new IllegalArgumentException("User id " + newUser.getId() + " already exists");
        }
        dbLayer.add(newUser);
        return newUser;
    }

    @Override
    public User loginUser(String username, String password) {

        List<User> queryResult = dbLayer.getUsers(username, password);
        if(queryResult.isEmpty()){
            throw new IllegalArgumentException("User with username " + username + " and password " + password + " not found");
        }
        if(queryResult.size() > 1){
            throw new IllegalArgumentException("There can't be two or more users with the same username and password");
        }
        User result = queryResult.get(0);
        loggedUser = result;
        return result;
    }

    @Override
    public void updateTask(Task task) {
        dbLayer.update(task);
    }

    @Override
    public void addNewTask(Task newTask) {
        if(!isUserLogged()){
            throw new IllegalAccessError(MSG_ERROR_USER_NOT_LOGGED);
        }
        newTask.setUser(loggedUser);
        dbLayer.add(newTask);
    }

    @Override
    public void deleteTask(Task task) {
        if(!isUserLogged()){
            throw new IllegalAccessError(MSG_ERROR_USER_NOT_LOGGED);
        }
        if(!existsTaskIdLoggedUser(task.getId())){
            throw new IllegalAccessError("You can access only to user tasks");
        }
        dbLayer.delete(task);
        loggedUser.getTasks().remove(task);
    }


    private boolean existsTaskIdLoggedUser(int id) {
        return dbLayer.getTaskByIdWithUserId(id, loggedUser.getId()) != null;
    }

    @Override
    public Task getTaskById(int id) {
        if(!isUserLogged()){
            throw new IllegalAccessError("You should call loginUser() before calling this method");
        }
        if(!existsTaskIdLoggedUser(id)){
            throw new IllegalAccessError("You can access only to user tasks");
        }
        return dbLayer.getTaskByIdWithUserId(id, loggedUser.getId());
    }

    @Override
    public List<Task> getUserTasks() {
        if(!isUserLogged()){
            throw new IllegalAccessError("You should call loginUser() before calling this method");
        }
        return dbLayer.getTasks(loggedUser.getId());
    }


    @Override
    public void logout() {
        if(!isUserLogged()){
            throw new IllegalAccessError("You can't logout without login before");
        }
        loggedUser = null;
    }

    @Override
    public boolean isUserLogged() {
        return loggedUser != null;
    }

    public DBLayer getDBLayer() {
        return dbLayer;
    }

    @Override
    public User getLoggedUser() {
        return loggedUser;
    }
}
