package com.company;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class RetriveCar extends JFrame{
    private JPanel retriveCarPanel;
    private JComboBox comboBox1;
    private JButton retriveCarBtn;
    private JLabel stateOfCar;

    public RetriveCar(ArrayList<CarData> carList, String username, Driver dbDriver) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(retriveCarPanel);
        this.pack();
        String[] array = new String[carList.size()];
        System.out.println(username);
        for(int i = 0; i < array.length; i++) {
            if (carList.get(i).Username.equals(username)) {
                comboBox1.addItem(carList.get(i).LicensePlate);
            }
        }


        comboBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int getIndex = comboBox1.getSelectedIndex();
                System.out.println(getIndex);
                stateOfCar.setText(carList.get(getIndex).State.toString());
            }
        });
        retriveCarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int getIndex = comboBox1.getSelectedIndex();
                if(carList.get(getIndex).State == CarState.READY) {
                    carList.remove(getIndex);
                    JOptionPane.showMessageDialog(retriveCarPanel, "Your car is Ready and it's waiting outside !");
                    RefreshDatabase.populate(carList,false,dbDriver);
                    RetriveCar.this.setVisible(false);
                }
                else if(carList.get(getIndex).State == CarState.WAITING_FOR_DIAGNOSE || carList.get(getIndex).State == CarState.DIAGNOSED)
                {
                    carList.remove(getIndex);
                    JOptionPane.showMessageDialog(retriveCarPanel, "Carefull ! Your car hadn't been repaired but you can still retrieve it from the workshop .");
                    RetriveCar.this.setVisible(false);
                }
                else
                {
                    JOptionPane.showMessageDialog(retriveCarPanel, "Our Mechanics are working on your car, you cannot retrive it from the shop right now");
                    RetriveCar.this.setVisible(false);
                }
            }
        });
    }

}
