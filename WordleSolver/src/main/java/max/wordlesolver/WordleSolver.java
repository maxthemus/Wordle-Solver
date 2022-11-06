/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package max.wordlesolver;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author max
 */
public class WordleSolver {
    //Fields
    private static final int MAX_ATTEMPS = 6;
    
    private final String wordToSolve;
    
    private int wordSize;
    private int attemptNum;
    private String[] words;
    private Letter[][] letters;
    private IndexTracker tracker;
    private StatusSetController statusController;
    private WordGenerator generator;
    private boolean foundWord;
    private HashMap<Character, Letter> characterMap;
    
    private StatHandler statHandler;
    
    //For colouring
    private HashMap<Character, ArrayList<Integer>> characterIndexMap;
    
    //Constructors
    public WordleSolver(String wordToSolve, String pathToFile) {
        this.attemptNum = 0;
        this.wordSize = wordToSolve.length();
        this.words = new String[wordSize];
        this.letters = new Letter[6][wordSize];
        this.foundWord = false;
        
        this.tracker = new IndexTracker(wordSize);
        this.statusController = new StatusSetController();
        this.generator = new WordGenerator(wordSize, tracker, statusController);
        
        this.wordToSolve = wordToSolve.toLowerCase();
        
        this.characterIndexMap = new HashMap<>(this.wordSize);
        this.setUpCharacterMap();
        
        this.characterMap = this.statusController.getCharacterMap();
        
        this.statHandler = new StatHandler(wordToSolve, pathToFile);
    }
    
    
    //Methods
    private void setUpCharacterMap() {
        for(int i = 0; i < this.wordSize; i++) {
            char character = this.wordToSolve.charAt(i);
            
            if(this.characterIndexMap.containsKey(character)) {
                ArrayList<Integer> indexArray = this.characterIndexMap.get(character);
                indexArray.add(i);
                this.characterIndexMap.put(this.wordToSolve.charAt(i), indexArray);
            } else {
                ArrayList<Integer> indexArray = new ArrayList<>(this.wordSize);
                indexArray.add(i);
                this.characterIndexMap.put(this.wordToSolve.charAt(i), indexArray);
            }
        }
    }
    
    public boolean insertWord(String word) {
        if(word.length() == this.wordSize) {
            if(!foundWord) {
                if(this.attemptNum < MAX_ATTEMPS) {
                    this.words[this.attemptNum] = word; //Adding word to array

                    //Adding characters to letters
                    for(int i = 0; i < word.length(); i++) {
                        this.letters[this.attemptNum][i] = this.characterMap.get(word.charAt(i));
                    }
                    
                    //Colour coding the array
                    this.colourInsertedWord();
                    
                    ArrayList<String> potentialStrings = this.generator.getPotentialWords();
                    this.statHandler.addInfo(attemptNum, word, potentialStrings);
                    
                    this.attemptNum++;
                }
            }
        }
        return foundWord;
    }
    
    //THIS COULD BE IMPROVED AND 
    private void colourInsertedWord() {
        for(int i = 0; i < this.wordSize; i++) {
            char letter = this.letters[this.attemptNum][i].getCharacter();
            Letter character = this.characterMap.get(letter);
            
            if(character.getStatus() != Status.GREEN) { //SKIPS OVER IF GREEN BECAUSE ALREADY FOUND
                if (this.characterIndexMap.containsKey(letter)) {
                    ArrayList<Integer> indexOfCharacter = this.characterIndexMap.get(letter);

                    if (indexOfCharacter.contains(i)) {
                        //Then index is GREEN
                        this.statusController.moveLetter(character, Status.GREEN); //Moving to GREE
                        this.tracker.clearIndex(i, letter);
                    } else {
                        //Then index is YELLOW
                        this.statusController.moveLetter(character, Status.YELLOW);
                        this.tracker.removeLetterFromIndex(letter, i);
                    }
                } else {
                    //Letter doesn't exist in the word
                    this.statusController.moveLetter(character, Status.GREY);
                    this.tracker.removeFromAllArrays(letter);
                }
            }  
        }
    }
    
    public void outputStats() { 
        this.statHandler.writeToFile();
    }
    
    public StatHandler getStatHandler() {
        return this.statHandler;
    }

    @Override
    public String toString() {
        String tempString = "";
        
        for(int i = 0; i < MAX_ATTEMPS; i++) {
            for(int j = 0; j < this.wordSize; j++) {
                if(this.letters[i][j] != null) {
                    tempString += "( " + this.letters[i][j].getCharacter() + " : " + this.letters[i][j].getStatus() + " )";
                } else {
                    tempString += "( )";
                }
                
                if(j < this.wordSize-1) {
                    tempString += ", ";
                }
            }
            tempString += "\n";
        }
        
        tempString += "FINAL WORD == " + this.wordToSolve;
        return tempString;
    }
    
    
    
    
    //Main Method
    public static void main(String[] args) {
        WordleSolver solver = new WordleSolver("great", "./output/output.txt");
        solver.insertWord("death");
        solver.insertWord("skimp");
        solver.insertWord("crown");
        solver.insertWord("bulgy");

        
        System.out.println(solver.toString());
        solver.outputStats();
    }
}
