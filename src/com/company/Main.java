package com.company;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.*;


public class Main {
    private static ArrayList<CarData> carList = new ArrayList<CarData>();

    public static void main(String[] args) throws SQLException {
        Driver dbDriver = new Driver();
        try{
            populateCarData.populate(carList);
            RepairShop repairShop = new RepairShop("MyRepairShop", carList,dbDriver);
            RefreshDatabase.populate(carList,true,dbDriver);
            repairShop.setLocationRelativeTo(null);
            repairShop.setBounds(100,100,400,400);
            repairShop.setVisible(true);
            System.out.println("....................................");
            for (CarData car: carList) {
                System.out.println(car.LicensePlate + " " + car.Username);
            }
            System.out.println("....................................");
        }catch (Exception exc)
        {
            exc.printStackTrace();
        }finally {
            //close connection before app exit
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                public void run() {
                    System.out.println("In shutdown hook");
                    RefreshDatabase.populate(carList,true,dbDriver);
                    dbDriver.Finalize();
                }
            }, "Shutdown-thread"));

        }

    }


}
