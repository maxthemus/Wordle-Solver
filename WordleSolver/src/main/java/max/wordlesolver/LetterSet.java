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
public class LetterSet {
    //Fields
    private Status statusOfLetters;
    private ArrayList<Letter> letters;
    private int letterCount;
    
    //Constructors
    public LetterSet(Status setStatus) {
        this.statusOfLetters = setStatus;
        this.letters = new ArrayList<>();
        this.letterCount = 0;
    }
    
    
    //Methods
    //For when charcter is given
    public boolean contains(char character) {
        for(int i = 0; i < this.letters.size(); i++) {
            if(this.letters.get(i).getCharacter() == character) {
                return true;
            }
        }
        
        return false;
    }
    
    public boolean contains(Letter letter) {
        return this.letters.contains(letter);
    }
    
    //Remove methods
    public boolean remove(char character) {
        for(int i = 0; i < this.letters.size(); i++) {
            if(this.letters.get(i).getCharacter() == character) {
                this.letters.remove(i);
                this.letterCount--;
                return true;
            }
        }
        return false;
    }
    
    public boolean remove(Letter letter) {
        boolean removed = this.letters.remove(letter);
        
        if(removed) {
            this.letterCount--;
        }
        return removed;
    }
    
    //Add Methods
    public void add(char character) {
        Letter letter = new Letter(character);
        letter.setStatus(this.statusOfLetters);
        
        //Check if contains already
        if(!this.contains(character)) {
            this.letters.add(letter);
            this.letterCount++;
        }
    }
    
    public void add(Letter letter) {
        letter.setStatus(this.statusOfLetters);
        
        //Check if contains already
        if(!this.contains(letter.getCharacter())) {
            this.letters.add(letter);
            this.letterCount++;
        }
    }

    @Override
    public String toString() {
        return this.statusOfLetters + " : " + this.letters.toString();
    }
    
    
}
