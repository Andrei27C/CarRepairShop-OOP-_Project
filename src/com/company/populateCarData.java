package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class populateCarData {
    public static void populate(ArrayList<CarData> carList) {
        try {
            File myObj = new File("file.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                CarData carData = new CarData();

                String licensePlate = myReader.next();
                String maker = myReader.next();
                String body = myReader.next();
                String color = myReader.next();
                String owner = myReader.next();
                String phoneNumber = myReader.next();
                String date = myReader.next(); //(Day/Month/Year)
                String StateString = myReader.next(); //get state
                String username = myReader.next();


                carData.LicensePlate = licensePlate;
                carData.Maker = maker;
                carData.Body = body;
                carData.Color = color;
                carData.Owner = owner;
                carData.PhoneNumber = phoneNumber;
                carData.Date = date;
                switch(StateString) {
                    case "WAITING_FOR_DIAGNOSE":
                        carData.State = CarState.WAITING_FOR_DIAGNOSE;
                        break;

                    case "DIAGNOSED":
                        carData.State = CarState.DIAGNOSED;
                        break;

                    case "WAITING_FOR_PARTS":
                        carData.State = CarState.WAITING_FOR_PARTS;
                        break;

                    case "IN_REPAIR":
                        carData.State = CarState.IN_REPAIR;
                        break;

                    case "READY":
                        carData.State = CarState.READY;
                        break;
                }
                carData.Username = username;

                carList.add(carData);
            }
            myReader.close();
        }catch(FileNotFoundException e)
        {
            System.out.println("Error");
            e.printStackTrace();
        }
    }
}
