/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package max.wordlesolver;

import java.io.BufferedReader;
import java.io.File;
import java.util.Scanner;

/**
 *
 * @author max
 */
public class TreeBuilder {
    //Fields
    private File file;
    private int wordLength;
    
    
    //Constructors
    public TreeBuilder(String pathToFile, int wordLength) {
        this.file = new File(pathToFile);
        this.wordLength = wordLength;
    }
    
    
    //Methods
    public WordTree buildTree() {
        WordTree tree = new WordTree(this.wordLength);
        
        try {
            Scanner reader = new Scanner(this.file);
            
            while(reader.hasNextLine()) {
                String word = reader.nextLine();

                tree.addWord(word);
            }
            
            reader.close();
        } catch(Exception e) {
            System.out.println(e);
            System.exit(1);
        }
        
        return tree;
    }
    
    
    //Main Method
    public static void main(String[] args) {
        //WORD SET
        //https://www-cs-faculty.stanford.edu/~knuth/sgb-words.txt
        TreeBuilder builder = new TreeBuilder("./words/FullWordSet", 5);
        WordTree tree = builder.buildTree();
        
        System.out.println(tree.getSize());
    }
    
}
