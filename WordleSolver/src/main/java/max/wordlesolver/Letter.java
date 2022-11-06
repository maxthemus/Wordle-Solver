/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package max.wordlesolver;

import java.util.ArrayList;

/**
 *
 * @author max
 */
public class Letter {
    //Fields
    private char character;
    private Status status;
    private ArrayList<Integer> indexNumbers; 
    
    
    //Constructors
    public Letter(char character) {
        this.character = character;
        this.status = Status.UNUSED;
        this.indexNumbers = new ArrayList<>(5); //Default size of array 5;
        
    }
    
    
    //Methods 
    public char getCharacter() {
        return character;
    }

    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status newStatus) {
        this.status = newStatus;
    }

    @Override
    public String toString() {
        return "("+this.character+")";
    }
    
    
}
