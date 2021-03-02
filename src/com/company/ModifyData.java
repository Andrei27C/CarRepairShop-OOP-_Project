package com.company;

import com.company.CarData;
import com.company.RegisterMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ModifyData extends JFrame {
    private JPanel panel1;
    private JTextField licensePlateText;
    private JTextField ownerText;
    private JTextField dateText;
    private JTextField emailText;
    private JTextField phoneNrText;
    private JButton modifyDataBtn;
    private JButton saveDataBtn;

    public ModifyData(ArrayList <CarData> carList, CarData carData, Driver dbDriver) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();
        licensePlateText.setText(carData.LicensePlate);
        ownerText.setText(carData.Owner);
        dateText.setText(carData.Date);
        phoneNrText.setText(carData.PhoneNumber);
        modifyDataBtn.setEnabled(false);

        modifyDataBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RefreshDatabase.populate(carList,false,dbDriver);
                JOptionPane.showMessageDialog(panel1, "Data had been updated");
                ModifyData.this.setVisible(false);
            }
        });
        saveDataBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carData.LicensePlate = licensePlateText.getText();
                carData.Owner = ownerText.getText();
                carData.Date = dateText.getText();
                carData.PhoneNumber = phoneNrText.getText();
                modifyDataBtn.setEnabled(true);



            }
        });
    }


}
