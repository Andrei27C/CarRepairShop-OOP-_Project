package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserMenu extends JFrame{
    private JPanel userPanel;
    private JPanel userSelction;
    private JComboBox whatOperationWouldYouComboBox;
    private JButton userNext;
    private JPanel addCar;
    private String username;

    public UserMenu(String title, ArrayList<CarData> carList,Driver dbDriver) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(userPanel);
        this.pack();
        userNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int getIndex = whatOperationWouldYouComboBox.getSelectedIndex();
                System.out.println(getIndex);

                if(getIndex == 1) {
                    if(carList.size()>=12)
                    {
                        JOptionPane.showMessageDialog(userPanel,"Repair Shop is full!\n Come back later");
                    }
                    else {
                        EnterCar enterCar = new EnterCar("Enter car form", carList,dbDriver);
                        enterCar.setUsername(username);
                        enterCar.setLocationRelativeTo(null);
                        enterCar.setBounds(100, 100, 400, 400);
                        enterCar.setVisible(true);
                    }
            }
                if(getIndex == 2)
                {
                        RetriveCar retriveCar = new RetriveCar(carList, username,dbDriver);
                        retriveCar.setLocationRelativeTo(null);
                        retriveCar.setBounds(100, 100, 400, 400);
                        retriveCar.setVisible(true);
                }
        }
    });
    }

    public static void main(String[] args) throws SQLException {
        Driver dbDriver = new Driver();
        ArrayList <CarData> carList = new ArrayList<>();
        JFrame frame = new UserMenu("UserMenu", carList,dbDriver);
        frame.setLocationRelativeTo(null);
        frame.setBounds(100,100,400,400);
        frame.setVisible(true);

    }

    public void setUsername(String user)
    {
        username = user;
    }
}
