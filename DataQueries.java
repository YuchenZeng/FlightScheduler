/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightscheduleryuchenzengykz5087;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
/**
 *
 * @author YuchenZeng
 */
public class DataQueries {
    private static final String URL = "jdbc:derby://localhost:1527/FlightSchedule";
    private ArrayList<String> names;
    private ArrayList<String> dates;
    private ArrayList<String> date;
    
    private Connection connection;
    private PreparedStatement selectAllPeople;
    private PreparedStatement selectPeopleByName;
    private PreparedStatement selectWaitByDate;
    private PreparedStatement insertNewPerson;
    private PreparedStatement updateNewBookByName;
    private Customer currentEntry;
    
    
    public DataQueries(){
        try{
            connection = DriverManager.getConnection(URL);
            selectAllPeople = connection.prepareStatement("SELECT*FROM UNTITLED ORDER BY Date ASC");
            selectPeopleByName = connection.prepareStatement("SELECT * FROM UNTITLED WHERE NAME = ? ORDER BY Date ASC");
            selectWaitByDate = connection.prepareStatement("SELECT * FROM UNTITLED WHERE DATE = ? ORDER BY Date ASC");
            insertNewPerson = connection.prepareStatement("INSERT INTO UNTITLED "+ "(NAME)"+"VALUES(?)");
            updateNewBookByName = connection.prepareStatement("UPDATE UNTITLED SET Date = ?,"+"flight = ?,"+ "status = ?" + "WHERE Name = ?");
         
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
            System.exit(1);
        }
    }
    
    public List<Customer>getAllPeople(){
        List<Customer>results = null;
        ResultSet resultSet = null;
        try{
            resultSet=selectAllPeople.executeQuery();
            results = new ArrayList<Customer>();
            while(resultSet.next()){
                results.add(new Customer(
                resultSet.getString("Name"),
                resultSet.getString("Date"),
                resultSet.getString("Status"),
                resultSet.getString("Flight")
                ));
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        finally 
        {
            try{
                resultSet.close();
            }
            catch(SQLException sqlException){
                sqlException.printStackTrace();
                close();
            }
        }
        return results;
    }
    
    public ArrayList<String> getName(){
        List<Customer>results = null;
        ResultSet resultSet = null;     
        try{
            resultSet=selectAllPeople.executeQuery();
            results = new ArrayList<Customer>();
            names = new ArrayList<String>();
            while(resultSet.next()){
                names.add(resultSet.getString("Name"));             
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        finally 
        {
            try{
                resultSet.close();
            }
            catch(SQLException sqlException){
                sqlException.printStackTrace();
                close();
            }
        }
        return names;
    }
    
    public ArrayList<String>getDate(){
        List<Customer>results = null;
        ResultSet resultSet = null;     
        String lastString = "";
        try{
            resultSet=selectAllPeople.executeQuery();
            results = new ArrayList<Customer>();
            dates = new ArrayList<String>();
            while(resultSet.next()){               
                if(lastString == null ? resultSet.getString("Date") != null : !lastString.equals(resultSet.getString("Date")))
                dates.add(resultSet.getString("Date"));      
                lastString = resultSet.getString("Date");
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        finally 
        {
            try{
                resultSet.close();
            }
            catch(SQLException sqlException){
                sqlException.printStackTrace();
                close();
            }
        }
        
        return dates;
    }
    
    public List<Customer>getPeopleByName(String name){
        List<Customer>results = null;
        ResultSet resultSet = null;
        try{
            results = new ArrayList<Customer>();
            selectPeopleByName.setString(1,name);
            resultSet = selectPeopleByName.executeQuery();
            while(resultSet.next()){
                results.add(new Customer(resultSet.getString("Name"),
                        resultSet.getString("Date"),
                        resultSet.getString("Status"),
                        resultSet.getString("Flight")));
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        finally{
            try{
                resultSet.close();
            }
            catch(SQLException sqlException){
                sqlException.printStackTrace();
                close();
            }
        }
        return results;
    }
    
    public List<Customer>getWaitByDate(String date){
        List<Customer>results = null;
        ResultSet resultSet = null;
        try{
            results = new ArrayList<Customer>();
            selectWaitByDate.setString(1,date);
            resultSet = selectWaitByDate.executeQuery();
            while(resultSet.next()){
                  if(!"Booked".equals(resultSet.getString("Status"))){
                results.add(new Customer(resultSet.getString("Name"),
                        resultSet.getString("Date"),
                        resultSet.getString("Status"),
                        resultSet.getString("Flight")));
                  }
            }
            /*
            while(resultSet.next()){
              
                results.add(new Customer(resultSet.getString("Name"),resultSet.getString("Date"),resultSet.getString("Status"),resultSet.getString("Flight")));
            }
                    */
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        finally{
            try{
                resultSet.close();
            }
            catch(SQLException sqlException){
                sqlException.printStackTrace();
                close();
            }
        }
        return results;
    }
        
    public int addCustomer(String name){
        
        int result = 0;
        try{
            insertNewPerson.setString(1,name);
            result = insertNewPerson.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
            close();
        }
        return result;
    }
    
    public int customerBook(String name,String date,String flight){
        List<Customer>results = getAllPeople();
        String status;
        //currentEntry = new Customer();
        int count = 0;
        for (int i = 0; i<results.size(); i++){
            currentEntry = results.get(i);
            if(currentEntry.getDate() == null ? date == null : currentEntry.getDate().equals(date)&&(currentEntry.getFlight() == null ? flight == null : currentEntry.getFlight().equals(flight)))
                count++;
        }
        if(count<2)
            status = "Booked";
        else
            status = "wait,"+" "+Integer.toString(count-2)+" Person ahead";
        
        int result = 0;
        try{
            updateNewBookByName.setString(1,date);
            updateNewBookByName.setString(2,flight);
            updateNewBookByName.setString(3,status);
            updateNewBookByName.setString(4,name);
            result = updateNewBookByName.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
            close();
        }
        return result;
    }
    
    public void close(){
        try{
            connection.close();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
}
