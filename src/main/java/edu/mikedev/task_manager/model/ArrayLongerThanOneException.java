package edu.mikedev.task_manager.model;

public class ArrayLongerThanOneException extends RuntimeException{

    public ArrayLongerThanOneException(){
        super();
    }

    public ArrayLongerThanOneException(String message){
        super(message);
    }

}
