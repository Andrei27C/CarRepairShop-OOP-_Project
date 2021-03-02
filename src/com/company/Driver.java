package com.company;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Driver extends Thread{
    private final String dbUrl = "jdbc:mysql://remotemysql.com:3306/Zx2Ie81O9l?useSSL=false";
    private final String dbUser = "Zx2Ie81O9l";
    private final String dbPass = "iW6CSaHg05";
    private final String dbName = "Zx2Ie81O9l";
    Connection myConn = DriverManager.getConnection(dbUrl,dbUser,dbPass);
    PreparedStatement myPStmt = myConn.prepareStatement("");

    public static void main(String[] args) throws ParseException, SQLException {
        Driver asd = new Driver();
        Date date = new Date();

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat dateInString = new SimpleDateFormat(pattern);

        //asd.addPerson("JOHN1", "0721626348", false,"asd","asd","asd@gmail.com");
        //asd.addCar("AlphaTauri","HD-96-ZZZ", date,"SILVER","SPORTS CAR",1,"user1");
        //System.out.println("asta -" + asd.getLastIndexOfTable("person"));;
    }

    public Driver() throws SQLException {
        System.out.println("Started driver for DB");
        /*
        try
        {
            // Get connection
            //Connection myConn = DriverManager.getConnection(dbUrl,dbUser,dbPass);
            // Create statement
            Statement myStmt = myConn.createStatement();
            // Execute SQL query
            //String sql = "INSERT INTO `carshop`.`cars` (`idCar`, `manufacturerId`, `stateId`, `colorId`) VALUES ('2', '1', '1', '1');\n";
            //myStmt.executeUpdate(sql);
            ResultSet myRs = myStmt.executeQuery("select * from carState");
            // Process the result set
            while(myRs.next())
            {
                System.out.println(myRs.getString("state"));
            }

            closeConnection(myConn,myStmt,myRs);
        }catch(Exception exc){
            exc.printStackTrace();
        }*/
    }

    public void addPerson(String name, String phone, boolean isAdmin, String username, String password, String email)
    {
        //Driver conn = new Driver();
        try
        {
            int id = getLastIndexOfTable("person") + 1;
            // Get connection
            //Connection myConn = DriverManager.getConnection(dbUrl,dbUser,dbPass);
            // Create statement
            //String sql = "INSERT INTO Zx2Ie81O9l.person VALUES (2,'0752345777','ASD',0)" ;

            //Prepare Statement
            //add in person
            String sql = "INSERT INTO Zx2Ie81O9l.person VALUES (?,?,?,?);";
            myPStmt= myConn.prepareStatement(sql);
            myPStmt.setInt(1,id);
            myPStmt.setString(2,phone);
            myPStmt.setString(3,name);
            myPStmt.setBoolean(4,isAdmin);
            // Execute SQL query
            myPStmt.executeUpdate();
            //add in user
            sql = "INSERT INTO Zx2Ie81O9l.user VALUES (?,?,?,?);";
            myPStmt = myConn.prepareStatement(sql);
            myPStmt.setInt(1,id);
            myPStmt.setString(2,username);
            myPStmt.setString(3,password);
            myPStmt.setString(4,email);
            // Execute SQL query
            myPStmt.executeUpdate();

            //Close connection
            //closeConnection(myConn,myPStmt);
        }catch(Exception exc){
            exc.printStackTrace();
        }
    }

    public void addCar(String manufacturer, String licensePlate, Date date, String color, String body, int nrOfDoors, String username)
    {
        try
        {
            int id = getLastIndexOfTable("cars") + 1;

            String sql = "INSERT INTO Zx2Ie81O9l.cars VALUES (?,?,?,?,?,?,?,?);";
            PreparedStatement myPStmt = myConn.prepareStatement(sql);

            //id
            myPStmt.setInt(1,id);

            //manufacturer
            int manufacturerId = addManufacturer(manufacturer);
            myPStmt.setInt(2,manufacturerId);

            //state
            myPStmt.setInt(3,0);

            //license plate
            myPStmt.setString(4,licensePlate);

            //date
            java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());
            myPStmt.setTimestamp(5,sqlDate);

            //color
            int colorId = addColor(color);
            myPStmt.setInt(6,colorId);

            //body
            int bodyId = addBody(body,nrOfDoors);
            myPStmt.setInt(7,bodyId);

            //person
            int personID = stringExistsInTable("user",username,2);
            if(personID == -1)
                throw new Exception("That username doesn't exist");
            else
                myPStmt.setInt(8,personID);

            //execute update
            myPStmt.executeUpdate();
            System.out.println("Car " + licensePlate + " added successfully");
            try {
                myPStmt.close();
            } catch (SQLException e) { /* ignored */}

        }catch(Exception exc){
            exc.printStackTrace();
        }
        //String sql1 = "INSERT INTO Zx2Ie81O9l.cars VALUES (" + id + ","+ manufacturerId + ", 0," + licensePlate + ","+ sqlDate+"," +
        // colorId + "," + bodyId + "," + personID + ");";
    }

    public int addManufacturer(String manufacturer)
    {
        int index = stringExistsInTable("manufacturer",manufacturer,2);
        if(index == -1)
        {
            try
            {
                int id = getLastIndexOfTable("manufacturer") + 1;
                // Get connection
                //Connection myConn = DriverManager.getConnection(dbUrl,dbUser,dbPass);

                String sql = "INSERT INTO Zx2Ie81O9l.manufacturer VALUES (?,?);";
                myPStmt= myConn.prepareStatement(sql);
                myPStmt.setInt(1,id);
                myPStmt.setString(2,manufacturer);
                myPStmt.executeUpdate();

                //Close connection
                //closeConnection(myConn,myPStmt);
                System.out.println(manufacturer + " added successfully");
                return id;
            }catch(Exception exc){
                exc.printStackTrace();
            }
        }
        return index;
    }

    public int addBody(String body, int nrOfDoors)
    {
        int index = stringExistsInTable("body",body,2);
        if(index == -1)
        {
            try
            {
                int id = getLastIndexOfTable("body") + 1;
                // Get connection
                //Connection myConn = DriverManager.getConnection(dbUrl,dbUser,dbPass);

                String sql = "INSERT INTO Zx2Ie81O9l.body VALUES (?,?,?);";
                myPStmt= myConn.prepareStatement(sql);
                myPStmt.setInt(1,id);
                myPStmt.setString(2,body);
                myPStmt.setInt(3,nrOfDoors);
                myPStmt.executeUpdate();

                //Close connection
                //closeConnection(myConn,myPStmt);
                System.out.println(body + " added successfully");
                return id;
            }catch(Exception exc){
                exc.printStackTrace();
            }
        }
        return index;
    }

    //UPDATE `Zx2Ie81O9l`.`cars` SET `stateID` = '1' WHERE (`ID` = '8');
    public void modifyCarState(String tableName, int carID, int newValue)
    {
        try
        {
            // Get connection
            //Connection myConn = DriverManager.getConnection(dbUrl,dbUser,dbPass);

            String sql = "UPDATE Zx2Ie81O9l." + tableName + " SET stateID = ?" + " WHERE (ID = ?);";
            myPStmt= myConn.prepareStatement(sql);
            myPStmt.setInt(1,newValue);
            myPStmt.setInt(2,carID);
            myPStmt.executeUpdate();

            //Close connection
            //closeConnection(myConn,myPStmt);
            System.out.println(carID + " modified successfully");
        }catch(Exception exc){
            exc.printStackTrace();
        }
    }

    public void removeRow(String tableName, String tableColumnName, int ID)
    {
        try
        {
            // Get connection
            //Connection myConn = DriverManager.getConnection(dbUrl,dbUser,dbPass);

            String sql = "DELETE FROM ? WHERE ? = ?;";
            myPStmt= myConn.prepareStatement(sql);
            myPStmt.setString(1,tableName);
            myPStmt.setString(2,tableColumnName);
            myPStmt.setInt(2,ID);
            myPStmt.executeUpdate();

            //Close connection
            //closeConnection(myConn,myPStmt);
            System.out.println("Row with ID = "+ ID + " deleted successfully");
        }catch(Exception exc){
            exc.printStackTrace();
        }
    }

    public void removeRow(String tableName, String tableColumnName, String stringName)
    {
        try
        {
            // Get connection
            ////Connection myConn = DriverManager.getConnection(dbUrl,dbUser,dbPass);

            String sql = "delete from ? where ?=?";
            myPStmt= myConn.prepareStatement(sql);
            myPStmt.setString(1,tableName);
            myPStmt.setString(2,tableColumnName);
            myPStmt.setString(2,stringName);
            myPStmt.executeUpdate();

            //Close connection
            //closeConnection(myConn,myPStmt);
            System.out.println("Row with string = "+ stringName + " deleted successfully");
        }catch(Exception exc){
            exc.printStackTrace();
        }
    }


    public void deleteAllDataTable(String tableName)
    {
        try
        {
            // Get connection
            //Connection myConn = DriverManager.getConnection(dbUrl,dbUser,dbPass);

            Statement stmt = myConn.createStatement();
            String query = "DELETE FROM " + tableName;
            int deletedRows=stmt.executeUpdate(query);
            if(deletedRows>0){
                System.out.println("All data from table: " + tableName + " deleted successfully");
            }else{
                System.out.println("Table already empty.");
            }

            //Close connection
            //closeConnection(myConn,stmt);

        }catch(Exception exc){
            exc.printStackTrace();
        }
    }

    public int addColor(String color)
    {

        int index = stringExistsInTable("color",color,2);
        //doesn't exist
        if(index==-1)
        {
            try
            {
                int id = getLastIndexOfTable("color") + 1;
                // Get connection
                //Connection myConn = DriverManager.getConnection(dbUrl,dbUser,dbPass);

                String sql = "INSERT INTO Zx2Ie81O9l.color VALUES (?,?);";
                myPStmt= myConn.prepareStatement(sql);
                myPStmt.setInt(1,id);
                myPStmt.setString(2,color);
                myPStmt.executeUpdate();

                //Close connection
                //closeConnection(myConn,myPStmt);
                System.out.println(color + " added successfully");
                return id;
            }catch(Exception exc){
                exc.printStackTrace();
            }
        }
        return index;
    }

    public int stringExistsInTable(String tableName, String s, int columnIndex)
    {
        try
        {
            // Get connection
            //Connection myConn = DriverManager.getConnection(dbUrl,dbUser,dbPass);
            // Create statement
            Statement myStmt = myConn.createStatement();
            String sql = "select * from " + tableName;
            ResultSet myRs = myStmt.executeQuery(sql);
            // Process the result set
            String x;
            int id;
            while(myRs.next())
            {

                x = myRs.getString(columnIndex);
                id = Integer.parseInt(myRs.getString(1));
                if(x.equals(s)) {
                    //closeConnection(myConn,myStmt,myRs);
                    return id;
                }
                //System.out.println(myRs.getString(1));
            }
            //closeConnection(myConn,myStmt,myRs);
        }catch(Exception exc){
            exc.printStackTrace();
        }
        System.out.println(s + " doesn't exist in table " + tableName);
        return -1;
    }

    public int getLastIndexOfTable(String tableName)
    {
        int index = 0;
        try
        {
            // Get connection
            //Connection myConn = DriverManager.getConnection(dbUrl,dbUser,dbPass);
            // Create statement
            Statement myStmt = myConn.createStatement();
            String sql = "select * from " + tableName;
            ResultSet myRs = myStmt.executeQuery(sql);
            // Process the result set
            while(myRs.next())
            {
                index = Integer.parseInt(myRs.getString(1));
                //System.out.println(myRs.getString(1));
            }
            //closeConnection(myConn,myStmt,myRs);
        }catch(Exception exc){
            exc.printStackTrace();
        }

        return index;
    }

    private void closeConnection(Connection conn, Statement ps, ResultSet rs)
    {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) { /* ignored */}
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) { /* ignored */}
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) { /* ignored */}
        }
    }
    private void closeConnection(Connection conn, Statement ps)
    {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) { /* ignored */}
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) { /* ignored */}
        }
    }
    private void closeConnection(Connection conn)
    {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) { /* ignored */}
        }
    }

    public void Finalize()
    {
        System.out.println("------------Database connection closed!");
        closeConnection(myConn,myPStmt);
    }
}
