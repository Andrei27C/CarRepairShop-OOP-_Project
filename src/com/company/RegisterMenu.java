package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class RegisterMenu extends JFrame{
    private JPasswordField passwordField;
    private JTextField usernameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JButton registerButton;
    private JPanel registerPanel;
    private JTextField nameTextField;

    public RegisterMenu(Driver dbDriver){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(registerPanel);
        this.pack();

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                String email = emailField.getText();
                String phone = phoneField.getText();
                String personName = nameTextField.getText();

                //Constraints
                if(password.length() < 7)
                {
                    JOptionPane.showMessageDialog(registerPanel,"Password must have at least 7 characters");
                }
                else if(phone.length() != 10)
                {
                    JOptionPane.showMessageDialog(registerPanel,"Invalid phone number");
                }
                else if(!(email.indexOf("@")>0 && (email+email.indexOf("@")).indexOf(".") > 0))
                {
                    JOptionPane.showMessageDialog(registerPanel,"Invalid email");
                }
                else
                {
                    //if all good, try to register
                    try{
                        System.out.print(username + " " + password + " " + email + " " + phone + "\n");

                        FileWriter fileWriter = new FileWriter("users.txt", true);
                        fileWriter.write(username + " " + password + " " + email + " " + phone + " 0\n");
                        fileWriter.close();

                        //Driver dbDrive = new Driver();
                        dbDriver.addPerson(personName,phone,false,username,password,email);


                        JOptionPane.showMessageDialog(registerPanel,"Successfully registered");
                        RegisterMenu.this.setVisible(false);
                        processEvent(new WindowEvent(RegisterMenu.this, WindowEvent.WINDOW_CLOSING));
                    } catch (IOException e1){
                        e1.printStackTrace();
                    }


                }

            }

        });
    }

    public static void main(String[] args) throws SQLException {
        Driver dbDriver = new Driver();
        JFrame frame = new RegisterMenu(dbDriver);
        frame.setLocationRelativeTo(null);
        frame.setBounds(100,100,400,400);
        frame.setVisible(true);

    }

}
