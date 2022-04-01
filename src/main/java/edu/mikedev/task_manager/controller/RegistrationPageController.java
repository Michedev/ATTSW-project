package edu.mikedev.task_manager.controller;

import edu.mikedev.task_manager.data.User;
import edu.mikedev.task_manager.ui.LoginPage;
import edu.mikedev.task_manager.ui.RegistrationPage;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RegistrationPageController extends TaskPageController<RegistrationPage> {

    public RegistrationPageController(RegistrationPage registrationPage, TaskManagerController controller) {
        super(registrationPage, controller);
    }

    @Override
    public void setGUIBindings() {
        panel.getBtnRegister().addActionListener(this::registerEvents);
        panel.getBtnCancel().addActionListener(this::goBackLoginPage);
    }

    private void goBackLoginPage(ActionEvent e) {
        LoginPageController pageController = new LoginPageController(new LoginPage(), controller);
        controller.setContentPane(pageController, "Login page");
    }

    private User parseUserIfCorrect() {
        boolean correct = true;
        JLabel lblErrorMessageUsername = panel.getLblErrorMessageUsername();
        if(panel.getTfUsername().getText().isEmpty()){
            lblErrorMessageUsername.setVisible(true);
            lblErrorMessageUsername.setText("Missing username");
            correct = false;
        } else {
            if(model.userExists(panel.getTfUsername().getText())){
                lblErrorMessageUsername.setVisible(true);
                lblErrorMessageUsername.setText("Username exists");
                correct = false;
            }
        }
        char[] password = panel.getTfPassword().getPassword();
        JLabel lblErrorMessagePassword = panel.getLblErrorMessagePassword();
        if(password.length == 0){
            lblErrorMessagePassword.setVisible(true);
            lblErrorMessagePassword.setText("Missing password");
            correct = false;
        }
        JLabel lblErrorMessageEmail = panel.getLblErrorMessageEmail();
        if(panel.getTfEmail().getText().isEmpty()){
            lblErrorMessageEmail.setVisible(true);
            lblErrorMessageEmail.setText("Missing e-mail");
            correct = false;
        } else {
            String regexEmail = "[\\w]+@[\\w]+\\.[\\w]{1,5}";
            if(!panel.getTfEmail().getText().matches(regexEmail)){
                lblErrorMessageEmail.setVisible(true);
                lblErrorMessageEmail.setText("The input prompted above is not an e-mail");
                correct = false;
            }
        }
        if(!correct){
            controller.getWindow().pack();
            return null;
        }
        return new User(panel.getTfUsername().getText(), new String(password), panel.getTfEmail().getText());
    }

    public void registerEvents(ActionEvent e) {
        User newUser = parseUserIfCorrect();

        if(newUser != null){
            model.registerUser(newUser);
            JOptionPane.showMessageDialog(controller.getWindow(),
                    String.format("Username %s successfully registered", newUser.getUsername()),
                    "Registration completed", 1);
            LoginPage loginWindow = new LoginPage();
            LoginPageController pageController = new LoginPageController(loginWindow, controller);
            controller.setContentPane(pageController, "Login page");

        }

    }
}
