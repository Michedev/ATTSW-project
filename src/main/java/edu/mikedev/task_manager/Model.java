package edu.mikedev.task_manager;

public interface Model {

    boolean areCredentialCorrect(String username, String password);

    boolean userExists(String username);

    User registerUser(String username, String password, String email);

    User getUser(String username, String password);
}
