package edu.mikedev.task_manager.model;

import edu.mikedev.task_manager.Task;
import edu.mikedev.task_manager.User;
import org.hibernate.Session;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;


public class HibernateModel implements Model{

    private final static String msgErrorUserNotLogged = "You should login before calling this method";
    private User loggedUser;
    private DBLayer dbLayer;

    public HibernateModel(Session hibernateSession){
        dbLayer = new HibernateDBLayer(hibernateSession);
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
        int newId = findNewUserId();
        User newUser = new User(username, password, email);
        newUser.setId(newId);
        newUser.setTasks(new HashSet<>());
        if(userExists(newUser.getId(), newUser.getUsername())){
            throw new IllegalArgumentException("User id " + newUser.getId() + " already exists");
        }
        dbLayer.add(newUser);
        return newUser;
    }

    private int findNewUserId() {
        List<Integer> userIds = dbLayer.getUserIds();
        return findNewId(userIds);
    }

    private int findNewTaskId(){
        List<Integer> taskIds = dbLayer.getTasksId();
        return findNewId(taskIds);
    }

    private int findNewId(List<Integer> existingIds) {
        Optional<Integer> optionalMax = existingIds.stream().max(Integer::compareTo);

        int endRange = optionalMax.map(integer -> integer + 2).orElseGet(() -> 0);
        for(int i: IntStream.range(0, endRange).toArray()){
            if(!existingIds.contains(i)){
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
        if(!isUserLogged()){
            throw new IllegalAccessError(msgErrorUserNotLogged);
        }
        if(!existsTaskIdLoggedUser(task.getId())){
            throw new IllegalArgumentException("Task id must exists already in DB");
        }
        dbLayer.update(task);
    }

    @Override
    public void addNewTask(Task newTask) {
        if(!isUserLogged()){
            throw new IllegalAccessError(msgErrorUserNotLogged);
        }
        if(dbLayer.getTasksId().contains(newTask.getId())){
            throw new IllegalArgumentException("Task id must not exists already in DB");
        }
        newTask.setId(findNewTaskId());
        newTask.setUser(loggedUser);
        dbLayer.add(newTask);
    }

    @Override
    public void deleteTask(Task task) {
        if(!isUserLogged()){
            throw new IllegalAccessError(msgErrorUserNotLogged);
        }
        if(!existsTaskIdLoggedUser(task.getId())){
            throw new IllegalAccessError("You can access only to user tasks");
        }
        dbLayer.deleteTask(loggedUser, task);
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
    public List<Task> getTasks() {
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


}
