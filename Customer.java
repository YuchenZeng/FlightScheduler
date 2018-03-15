/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightscheduleryuchenzengykz5087;

/**
 *
 * @author YuchenZeng
 */
public class Customer {
    private String Name;
    private String date;
    private String status;
    private String flight;
    
    public Customer(){
    }
    
    public Customer(String Name,String date,String Status,String flight){
        setName(Name);
        setDate(date);
        setStatus(Status);
        setFlight(flight);
    }
    
    public void setName(String Name){
        this.Name = Name;
    }
    
    public String getName(){
        return Name;
    }
    
    public void setDate(String date){
        this.date = date;
    }
    
    public String getDate(){
        return date;
    }
    
    public void setStatus(String status){
        this.status = status;
    }
    
    public String getStatus(){
        return status;
    }
    
    public void setFlight(String flight){
        this.flight = flight;
    }
    
    public String getFlight(){
        return flight;
    }
}
