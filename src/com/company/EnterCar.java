package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.company.CarState.WAITING_FOR_DIAGNOSE;


public class EnterCar extends JFrame{
    private JPanel carEnter;
    private JLabel licenseLabel;
    private JLabel ownerLabel;
    private JLabel phoneLabel;
    private JTextField pleaseEnterLicensePlateTextField;
    private JTextField ownerNameTextField;
    private JTextField phoneNumberTextField;
    private JButton addCar;
    private JTextField makerTextField;
    private JTextField bodyTextField;
    private JTextField colorTextField;
    private String username;

    public EnterCar(String title, ArrayList<CarData> carList, Driver dbDriver) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(carEnter);
        this.setLocationRelativeTo(null);
        this.setBounds(100,100,400,400);
        this.pack();

        addCar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(pleaseEnterLicensePlateTextField.getText().length() == 9 || pleaseEnterLicensePlateTextField.getText().length()== 10 ))
                {
                    JOptionPane.showMessageDialog(carEnter, "License plate format incorrect! (Please use: AA-11-BBB / B-11-AAA");
                }
                else if(phoneNumberTextField.getText().length() != 10)
                {
                    JOptionPane.showMessageDialog(carEnter, "Incorrect phone number");
                }
                else {
                    JOptionPane.showMessageDialog(carEnter, pleaseEnterLicensePlateTextField.getText() + " has been added");
                    System.out.println(pleaseEnterLicensePlateTextField.getText() + " has been added");
                    EnterCar.this.setVisible(false);
                    CarData carData = new CarData();
                    carData.Maker = makerTextField.getText();
                    carData.LicensePlate = pleaseEnterLicensePlateTextField.getText();
                    String pattern = "dd/MM/yyyy";
                    String dateInString = new SimpleDateFormat(pattern).format(new Date());
                    carData.Date = dateInString;
                    carData.PhoneNumber = phoneNumberTextField.getText();
                    carData.Owner = ownerNameTextField.getText();
                    carData.State = WAITING_FOR_DIAGNOSE;
                    carData.Username = username;
                    carData.Body = bodyTextField.getText();
                    carData.Color = colorTextField.getText();

                    //Driver dbDriver = new Driver();
                    Date date = new Date();
                    int noOfDoors = 4;
                    dbDriver.addCar(makerTextField.getText(),pleaseEnterLicensePlateTextField.getText(),date,
                            colorTextField.getText(),bodyTextField.getText(), noOfDoors,username);

                    carList.add(carData);
                    RefreshDatabase.populate(carList,false, dbDriver);
                }

            }
        });
    }

    public static void main(String[] args)
    {
            /*JFrame frame = new enterCar("Enter Car", carList);
            frame.setLocationRelativeTo(null);
            frame.setBounds(100,100,400,400);
            frame.setVisible(true);*/

    }

    public void setUsername(String user)
    {
        username = user;
    }


}
