package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class RepairShop extends JFrame {
    private JPanel UserPanel;
    private JPanel MainPanel;
    private JLabel loginLabel;
    private JLabel usernameLabel;
    private JTextField userField;
    private JPasswordField passField;
    private JButton loginBtn;
    private JButton registerButton;

    public RepairShop(String title, ArrayList<CarData> carList, Driver dbDriver) {
       super(title);
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       this.setContentPane(MainPanel);
       this.pack();

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = passField.getText();
                String email;
                String phone;
                boolean admin = false;
                //Search for user in file
                try{
                    File file = new File("users.txt");
                    Scanner scanner = new Scanner(file);
                    boolean userFound = false;
                    while(scanner.hasNext()){
                        String[] attributes = scanner.nextLine().split(" ");
                        if(username.equals(attributes[0]) && password.equals(attributes[1]))
                        {
                            email = attributes[2];
                            phone = attributes[3];
                            if(attributes[4].equals("1"))
                                admin = true;
                            userFound = true;

                            //Open next panel
                            RepairShop.this.setVisible(false);
                            RefreshDatabase.populate(carList,false, dbDriver);

                            if(admin)
                            {
                                JFrame adminFrame = new AdminMenu("Administrator Menu",carList,dbDriver);
                                adminFrame.setLocationRelativeTo(null);
                                adminFrame.setBounds(100,100,400,400);
                                adminFrame.setVisible(true);
                            }
                            else
                            {
                                UserMenu userFrame = new UserMenu("User Menu",carList,dbDriver);
                                userFrame.setUsername(username);
                                userFrame.setLocationRelativeTo(null);
                                userFrame.setBounds(100,100,400,400);
                                userFrame.setVisible(true);
                            }


                            break;
                        }
                    }
                    //if user not found
                    if(!userFound)
                    {
                        JOptionPane.showMessageDialog(MainPanel,"Invalid username or password");
                    }
                } catch (FileNotFoundException e1)
                {
                    e1.printStackTrace();
                }

            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new RegisterMenu(dbDriver);
                frame.setLocationRelativeTo(null);
                frame.setBounds(100,100,400,400);
                frame.setVisible(true);

            }
        });
    }

    /*
   public static void main(String[] args)
   {
       ArrayList<CarData> carList = new ArrayList<CarData>();
       populateCarData.populate(carList);
       JFrame frame = new RepairShop("MyRepairShop", carList);
       frame.setLocationRelativeTo(null);
       frame.setBounds(100,100,400,400);
       frame.setVisible(true);

   }*/


    public String getUsername()
    {
        return userField.getText();
    }


}
