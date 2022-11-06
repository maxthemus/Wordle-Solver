/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package max.wordlesolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author max
 */
public class WordleAssistor {
    //Fields
    private static final int MAX_ATTEMPTS = 6;
    private static final int WORD_SIZE = 5;
    private int attemptNum;
    private String[] words;
    private Letter[][] letters;
    private IndexTracker tracker;
    private StatusSetController statusController;
    private WordGenerator generator;
    private boolean foundWord;
    private HashMap<Character, Letter> characterMap;
    

    //Constructors
    public WordleAssistor() {
        this.attemptNum = 0;
        this.words = new String[WORD_SIZE];
        this.letters = new Letter[MAX_ATTEMPTS][WORD_SIZE];
        this.tracker = new IndexTracker(WORD_SIZE);
        this.statusController = new StatusSetController();
        this.generator = new WordGenerator(WORD_SIZE, tracker, statusController);
        
        this.characterMap = this.statusController.getCharacterMap();
    }
    
    
    //Methods
    public ArrayList<String> insertWord(String word, Status[] status) {
        if(word.length() == WORD_SIZE && status.length == WORD_SIZE) {
            this.words[this.attemptNum] = word;
            
            //Adding characters to letters
            for(int i = 0; i < WORD_SIZE; i++) {
                Letter letter = this.characterMap.get(word.charAt(i));
                letter.setStatus(status[i]);
                
                if(letter.getStatus() == Status.GREEN) {
                    this.statusController.moveLetter(letter, Status.GREEN);
                    this.tracker.clearIndex(i, letter.getCharacter());
                } else if(letter.getStatus() == Status.YELLOW) {
                    this.statusController.moveLetter(letter, Status.YELLOW);
                    this.tracker.removeLetterFromIndex(letter.getCharacter(), i);
                } else {
                    this.statusController.moveLetter(letter, Status.GREY);
                    this.tracker.removeFromAllArrays(letter.getCharacter());
                }
                
                this.letters[this.attemptNum][i] = letter;
            }
            
            ArrayList<String> potentialStrings = this.generator.getPotentialWords();
            
            this.attemptNum++;
            return potentialStrings;
        }
        return null;
    }
    
    @Override
    public String toString() {
        String tempString = "";
        
        for(int i = 0; i < MAX_ATTEMPTS; i++) {
            for(int j = 0; j < WORD_SIZE; j++) {
                if(this.letters[i][j] != null) {
                    tempString += "( " + this.letters[i][j].getCharacter() + " : " + this.letters[i][j].getStatus() + " )";
                } else {
                    tempString += "( )";
                }
                
                if(j < WORD_SIZE-1) {
                    tempString += ", ";
                }
            }
            tempString += "\n";
        }
        
        return tempString;
    }
    
    public Status[] stringToStatus(String status) {
        String[] splits = status.split(",");
        Status[] stats = new Status[splits.length];
        
        for(int i = 0; i < splits.length; i++) {
            if(splits[i].compareTo("G") == 0) {
                stats[i] = Status.GREEN;
            } else if(splits[i].compareTo("Y") == 0) {
                stats[i] = Status.YELLOW;
            } else if(splits[i].compareTo("g") == 0) {
                stats[i] = Status.GREY;
            }
        }
        
        return stats;
    }
    
    public void playGame() {
        Scanner sc = new Scanner(System.in);
        
        for(int i = 0; i < MAX_ATTEMPTS; i++) {
            System.out.println("Input attempt Number : " + (i+1));
            System.out.println("Input word -> ");
            String input = sc.nextLine();
            
            //"dG,eG,aG,tG,hG" input format
            if(input.length() != ((WORD_SIZE * 3)-1)) {
                i--;
                continue;
            } else {
                //VALID INPUT now we need to break down the input
                String[] splits = input.split(",");
                Status[] status = new Status[WORD_SIZE];
                String word ="";
                
                //Creating the status array
                int count = 0;
                for(String s : splits) {
                    word += s.charAt(0);
                    if(s.charAt(1) == 'G') {
                        status[count] = Status.GREEN;
                    } else if (s.charAt(1) == 'Y') {
                        status[count] = Status.YELLOW;
                    } else if (s.charAt(1) == 'g') {
                        status[count] = Status.GREY;
                    }
                    count++;
                }
                
                ArrayList<String> outcomes = this.insertWord(word, status);
                System.out.println("There are now " + outcomes.size() + " possible words left");
                System.out.println("Show ?");
                String show = sc.nextLine();
                if(show.compareTo("YES") == 0) {
                    System.out.println(outcomes.toString());
                }
                System.out.println("+-------------+");
            }
        }
    }
    
    
    //Main Method
    public static void main(String[] args) {
        WordleAssistor main = new WordleAssistor();
        
//        main.stringToStatus("g,g,g,g,g");
//        ArrayList<String> outcomes = main.insertWord("death", main.stringToStatus("g,Y,G,Y,g"));
//        outcomes = main.insertWord("crown", main.stringToStatus("g,g,g,g,g"));
//        outcomes = main.insertWord("skimp", main.stringToStatus("G,g,g,g,g"));
//        outcomes = main.insertWord("bulgy", main.stringToStatus("g,g,Y,g,g"));
//        outcomes = main.insertWord("slave", main.stringToStatus("G,Y,G,g,G"));
//        
//        System.out.println(main.toString());
//        System.out.println(outcomes.toString());
//        System.out.println(outcomes.size());

        main.playGame();
    }
}
