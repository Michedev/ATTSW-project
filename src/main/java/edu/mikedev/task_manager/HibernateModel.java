package edu.mikedev.task_manager;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

public class HibernateModel implements Model{

    private Session hibernateSession;
    private Transaction transaction;
    private User loggedUser;

    public HibernateModel(Session hibernateSession){
        this.hibernateSession = hibernateSession;
        this.transaction = hibernateSession.beginTransaction();
    }

    public Transaction getTransaction(){
        return transaction;
    }

    @Override
    public boolean areCredentialCorrect(String username, String password) {
        List<User> users = hibernateSession.createQuery(String.format("SELECT a from User a where a.username = '%s' and a.password = '%s'", username, password), User.class).getResultList();
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
        List<User> users = hibernateSession.createQuery(String.format("SELECT a from User a where a.username = '%s'", username), User.class).getResultList();
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
        addUser(newUser);
        return newUser;
    }

    private int findNewId() {
        List<Integer> userIds = getUserIds();
        int endRange = userIds.stream().max(Integer::compareTo).get() + 1;
        for(int i: IntStream.range(0, endRange).toArray()){
            if(!userIds.contains(i)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void addUser(User user) {
        if(userExists(user.getId(), user.getUsername())){
            throw new IllegalArgumentException("User id " + user.getId() + " already exists");
        }
        hibernateSession.persist(user);

    }

    private boolean userExists(int id, String username) {
        return getUserIds().contains(id) ||
                getUsernames().contains(username);
    }

    private List<String> getUsernames() {
        return hibernateSession.createQuery("SELECT username from User", String.class).getResultList();
    }

    private List<Integer> getUserIds() {
        return hibernateSession.createQuery("SELECT id from User", Integer.class).getResultList();
    }

    @Override
    public User loginUser(String username, String password) {

        List<User> queryResult = hibernateSession.createQuery(String.format("SELECT a from User a where a.username = '%s' and a.password = '%s'", username, password), User.class).getResultList();
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
    public boolean existsUsername(String username) {
        return hibernateSession.createQuery("SELECT username from User").getResultList().contains(username);

    }

    @Override
    public void updateTask(Task task) {
        if(loggedUser == null){
            throw new IllegalAccessError("You should login by calling this method");
        }
        if(!existsTaskIdLoggedUser(task.getId())){
            throw new IllegalArgumentException("Task id must exists already in DB");
        }
        hibernateSession.evict(task);
        hibernateSession.update(task);
        transaction.commit();
    }

    @Override
    public void addNewTask(Task newTask) {
        if(loggedUser == null){
            throw new IllegalAccessError("You should login by calling this method");
        }
        if(existsTaskId(newTask.getId())){
            throw new IllegalArgumentException("Task id must not exists already in DB");
        }

        hibernateSession.persist(newTask);
        transaction.commit();
    }

    @Override
    public void deleteTask(Task task) {
        if(loggedUser == null){
            throw new IllegalAccessError("You should login by calling this method");
        }
        if(!existsTaskIdLoggedUser(task.getId())){
            throw new IllegalAccessError("You can access only to user tasks");
        }
        hibernateSession.remove(task);
        transaction.commit();
    }

    @Override
    public boolean existsTaskId(int id) {
        return hibernateSession.createQuery("SELECT id from Task", Integer.class).getResultList().contains(id);
    }

    public boolean existsTaskIdLoggedUser(int id){
        String queryCmd = String.format("SELECT id from Task where id_user = %d", loggedUser.getId());
        System.out.println(queryCmd);
        return hibernateSession.createQuery(queryCmd, Integer.class).getResultList().contains(id);
    }

    @Override
    public Task getTaskById(int id) {
        if(loggedUser == null){
            throw new IllegalAccessError("You should call loginUser() before calling this method");
        }
        if(!existsTaskIdLoggedUser(id)){
            throw new IllegalAccessError("You can access only to user tasks");
        }
        return hibernateSession.createQuery(String.format("SELECT a from Task a where id_user = %d and id = %d", loggedUser.getId(), id), Task.class).getResultList().get(0);
    }

    @Override
    public List<Task> getTasks() {
        return hibernateSession.createQuery("SELECT a FROM Task a where id_user = " + loggedUser.getId(), Task.class).getResultList();
    }

    @Override
    public void logout() {
        if(loggedUser == null){
            throw new IllegalAccessError("You can't logout without login before");
        }
        loggedUser = null;
    }
}
