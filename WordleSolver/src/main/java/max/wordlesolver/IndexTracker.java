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
public class IndexTracker {
    //Fields
    private int wordLength;
    private ArrayList<Character>[] indexs; 
    
    //Constructors
    public IndexTracker(int length) {
        this.wordLength = length;
        
        this.indexs = new ArrayList[length];
        
        this.setUp();
    }

    //Methods
    public void removeLetterFromIndex(char character, int index) {
        this.indexs[index].remove((Character)character);
    }
    
    public void removeFromAllArrays(char character) {
        for(int i = 0; i < indexs.length; i++) {
            this.removeLetterFromIndex((Character)character, i);
        }
    }
    
    public void clearIndex(int index, char character) {
        this.indexs[index].clear();
        this.indexs[index].add(character);
    }
    
    public ArrayList<Character> getIndex(int index) {
        return this.indexs[index];
    }
    
    private void setUp() {
        for(int i = 0; i < wordLength; i++) {
            this.indexs[i] = new ArrayList<>(26);
            
            //97 to 122
            for(int j = 97; j <= 122; j++) {
                char character = (char)j;
                this.indexs[i].add(character);
            } 
        }
    }

    @Override
    public String toString() {
        String tempString = "";
        
        int index = 0;
        for(ArrayList<Character> list : this.indexs) {
            tempString += "[" + index++ + "] : " + list.toString() + "\n";
        }
        return tempString;
    }
    
    
    //Main Method
    public static void main(String[] args) {
        IndexTracker tracker = new IndexTracker(5);
        System.out.println(tracker.toString());
    }
}
