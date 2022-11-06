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
public class WordGenerator {
    //Fields
    private int wordSize;
    private IndexTracker indexTracker;
    private WordTree wordTree;
    private StatusSetController statusController;
    
    
    
    //Constructors
    public WordGenerator(int wordSize, IndexTracker tracker, StatusSetController status) {
        this.wordSize = wordSize;
        this.indexTracker = tracker;
        this.statusController = status;
        
        //Setting up tree
        TreeBuilder builder = new TreeBuilder("./words/FullWordSet", wordSize);
        this.wordTree = builder.buildTree();
    }
    
    
    //Methods
    public ArrayList<String> getPotentialWords() {        
        ArrayList<Character>[] potentialCharacters = new ArrayList[this.wordSize];
        
        for(int i = 0; i < this.wordSize; i++) {
            potentialCharacters[i] = this.indexTracker.getIndex(i);
        }
        
        ArrayList<String> potentialStrings = this.wordTree.getPotentialWords(potentialCharacters);
       
        return potentialStrings;
    } 
    
    
    //Main Method
    public static void main(String[] args) {
        int wordSize = 5;
        IndexTracker tracker = new IndexTracker(wordSize);
        StatusSetController status = new StatusSetController();
        
        WordGenerator generator = new WordGenerator(5,tracker, status);
        
        ArrayList<String> words = generator.getPotentialWords();
    }
}
