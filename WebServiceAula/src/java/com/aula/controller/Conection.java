package com.aula.controller;

import com.aula.utils.Utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author claytontolotti@gmail.com, danielmatheuskuhn@gmail.com
*/
public class Conection {
    private static final String USER = "root";
    private static final String PASSWORD = "211458";
    
    private static Connection conn;
    
    public static Connection getConnection(){
        
        try {
            Class.forName(Utils.DRIVER_DB);
            conn = DriverManager.getConnection(Utils.URL,USER,PASSWORD);
            return conn;
        }catch(ClassNotFoundException clEx) {
            System.out.println("EXCEPTION ClassNotFoundException " + clEx.getMessage());
            return null;
        } catch(SQLException sqlEx) {
            System.out.println("EXCEPTION SQLException " + sqlEx.getMessage());
            return null;
        }
    }
    
    public static void closeConnection () {
        try {
            conn.close();
        } catch(SQLException sqlEx) {
            System.out.println("EXCEPTION SQLException " + sqlEx.getMessage());
        }
    }
}
