/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package max.wordlesolver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author max
 */
public class StatHandler {
    //Fields
    private String wordToFind;
    private ArrayList<String>[] possibleOutComes;
    private final int TOTAL_POSSIBLE_WORDS = 5757;
    private int[] numOfPossibleWords;
    private String[] words; 
    private int possibleWords;
    
    private final File file;
    
    
    //Constructors
    public StatHandler(String wordToFind, String pathToFile) {
        this.wordToFind = wordToFind;
        this.possibleOutComes = new ArrayList[6];
        this.numOfPossibleWords = new int[6];
        this.words = new String[6];
        this.possibleWords = 0;
        
        this.file = new File(pathToFile);
        
        try {
            this.file.createNewFile();
        } catch(Exception e) {
            System.out.println(e);
        }
    }
    
    
    
    //Methods
    public void addInfo(int attemptNum, String inputedWord, ArrayList<String> possibleWords) {
        this.possibleOutComes[attemptNum] = possibleWords;
        this.numOfPossibleWords[attemptNum] = possibleWords.size();
        this.words[attemptNum] = inputedWord;
        this.possibleWords = possibleWords.size();
    }
    
    public int getPossibleWords() {
        return this.possibleWords;
    }
    
    public void writeToFile() {
        try {
            FileWriter writer = new FileWriter(this.file, true);
        
            writer.write("Word to find == " + this.wordToFind + "\n");
            writer.write("Total possible Words == " + this.TOTAL_POSSIBLE_WORDS + "\n");

            for(int i = 0; i < 6; i++) {
                writer.write("["+i+"] : Inputted == " + this.words[i] + " : Possible Words == " + this.numOfPossibleWords[i] + "\n");
            }
            
            writer.write("Possible Words == " + this.possibleWords + "\n");
            writer.write("+------------------------+"+"\n");

            writer.close();
        } catch(Exception e) {
            System.out.println(e);
        }
    }    
}
