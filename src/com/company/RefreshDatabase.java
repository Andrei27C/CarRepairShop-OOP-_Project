package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.io.FileWriter;

public class RefreshDatabase {
    public static void populate(ArrayList<CarData> carList, boolean refreshDB, Driver dbDriver) {
        try {

            //refresh file
            File myObj = new File("file.txt");
            myObj.delete();
            FileWriter myWriter = new FileWriter("file.txt", true);
            for (CarData car : carList) {
                myWriter.append("\n").append(car.LicensePlate).append(" ").append(car.Maker).append(" ").append(car.Body).append(" ").append(car.Color).append(" ").append(car.Owner).append(" ").append(car.PhoneNumber).
                        append(" ").append(car.Date).append(" ").append(car.State.toString()).append(" ").append(car.Username);
            }
            myWriter.close();

            //refresh db
            if(refreshDB)
            {
                //Driver dbDriver = new Driver();
                dbDriver.deleteAllDataTable("cars");
                Date date = new Date();
                int carID = 1;
                int noOfDoors = 55;
                for (CarData car : carList) {

                    int carStateID = getCarStateID(car.State);
                    dbDriver.addCar(car.Maker,car.LicensePlate,date,car.Color,car.Color,noOfDoors,car.Username);
                    dbDriver.modifyCarState("cars",carID,carStateID);

                    //System.out.println(car.LicensePlate);
                    carID++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getCarStateID(CarState carState)
    {
        if(carState == CarState.WAITING_FOR_DIAGNOSE)
            return 0;
        if(carState == CarState.DIAGNOSED)
            return 1;
        if(carState == CarState.WAITING_FOR_PARTS)
            return 2;
        if(carState == CarState.IN_REPAIR)
            return 3;
        if(carState == CarState.READY)
            return 4;
        return 0;
    }
}
