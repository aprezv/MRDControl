package MRDControl;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Date;

/**
 *
 * @author Armando
 */
public class Room {
    private int id;
    private int number;
    private String type;
    private boolean active;

    public int getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public boolean isActive() {
        return active;
    }

    public Room(int id, int number, String type, boolean active) {
        this.id = id;
        this.number = number;
        this.type = type;
        this.active = active;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
   
 
    public Room()
    {}




    
    
}
