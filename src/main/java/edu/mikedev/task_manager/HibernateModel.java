package edu.mikedev.task_manager;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

public class HibernateModel implements Model{

    private User loggedUser;
    private DBLayer dbLayer;

    public HibernateModel(Session hibernateSession){
        dbLayer = new HibernateDBLayer(hibernateSession);
    }

    @Override
    public boolean areCredentialCorrect(String username, String password) {
        List<User> users = dbLayer.getUsers(username, password);
        if (users.isEmpty()){
            return false;
        }
        if (users.size() > 1){
            throw new RuntimeException(String.format("Users with username %s and password %s are %d", username, password, users.size()));
        }
        return true;
    }

    @Override
    public boolean userExists(String username) {
        List<User> users = dbLayer.getUsers(username);
        if(users.isEmpty()){
            return false;
        }
        if (users.size() > 1){
            throw new RuntimeException(String.format("Users with username %s are %d", username, users.size()));
        }
        return true;
    }

    @Override
    public User registerUser(String username, String password, String email) {
        int newId = findNewId();
        User newUser = new User(username, password, email);
        newUser.setId(newId);
        newUser.setTasks(new HashSet<>());
        if(userExists(newUser.getId(), newUser.getUsername())){
            throw new IllegalArgumentException("User id " + newUser.getId() + " already exists");
        }
        dbLayer.add(newUser);
        return newUser;
    }

    private int findNewId() {
        List<Integer> userIds = dbLayer.getUserIds();
        int endRange = userIds.stream().max(Integer::compareTo).get() + 1;
        for(int i: IntStream.range(0, endRange).toArray()){
            if(!userIds.contains(i)){
                return i;
            }
        }
        return -1;
    }


    private boolean userExists(int id, String username) {
        return dbLayer.getUserIds().contains(id) ||
                dbLayer.getUsernames().contains(username);
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
        if(loggedUser == null){
            throw new IllegalAccessError("You should login by calling this method");
        }
        if(!existsTaskIdLoggedUser(task.getId())){
            throw new IllegalArgumentException("Task id must exists already in DB");
        }
        dbLayer.update(task);
    }

    @Override
    public void addNewTask(Task newTask) {
        if(loggedUser == null){
            throw new IllegalAccessError("You should login by calling this method");
        }
        if(dbLayer.getTasksId().contains(newTask.getId())){
            throw new IllegalArgumentException("Task id must not exists already in DB");
        }
        newTask.setUser(loggedUser);
        dbLayer.add(newTask);
    }

    @Override
    public void deleteTask(Task task) {
        if(loggedUser == null){
            throw new IllegalAccessError("You should login by calling this method");
        }
        if(!existsTaskIdLoggedUser(task.getId())){
            throw new IllegalAccessError("You can access only to user tasks");
        }
        dbLayer.delete(task);
    }


    private boolean existsTaskIdLoggedUser(int id) {
        return dbLayer.getTaskByIdWithUserId(id, loggedUser.getId()) != null;
    }

    @Override
    public Task getTaskById(int id) {
        if(loggedUser == null){
            throw new IllegalAccessError("You should call loginUser() before calling this method");
        }
        if(!existsTaskIdLoggedUser(id)){
            throw new IllegalAccessError("You can access only to user tasks");
        }
        return dbLayer.getTaskByIdWithUserId(id, loggedUser.getId());
    }

    @Override
    public List<Task> getTasks() {
        return dbLayer.getTasks(loggedUser.getId());
    }


    @Override
    public void logout() {
        if(loggedUser == null){
            throw new IllegalAccessError("You can't logout without login before");
        }
        loggedUser = null;
    }

    public DBLayer getDBLayer() {
        return dbLayer;
    }


}
