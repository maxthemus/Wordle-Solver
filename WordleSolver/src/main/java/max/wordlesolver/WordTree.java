/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package max.wordlesolver;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author max
 */
public class WordTree {
    //Fields
    private HashMap<Character, Node> rootNodes;
    private int wordCount;
    private int wordLength;

    //Constructors
    public WordTree(int wordLength) {
        this.wordLength = wordLength;
        this.wordCount = 0;
        this.rootNodes = new HashMap<>(26);
        
        this.setupRootNodes();
    }
    
    
    //Methods
    public ArrayList<String> getPotentialWords(ArrayList<Character>[] characters) {
        ArrayList<String> potentialWords = new ArrayList<>();
        LinkedStack<Character> traversalStack = new LinkedStack<>();
        
        //Each index of characters represents a level in the tree
        for(char character : characters[0]) {
            
            Node node = this.rootNodes.get(character);
            this.treeWordSearch(traversalStack, potentialWords, characters, node, 1);
        }
        
        return potentialWords;
    }
    
    private void treeWordSearch(LinkedStack<Character> stack, ArrayList<String> potentialWords, ArrayList<Character>[] characters, Node nodePtr, int level) { 
        //System.out.println("PUSHING " + nodePtr.character + " : " + stack.getString());
        stack.push(nodePtr.character);//Pushing character onto the stack
        
        //If is leaf node
        if(level == this.wordLength) {
            String word = stack.getString();
            potentialWords.add(word);
            
        } else {
            //Loop through all potential options
            for(char letter : characters[level]) {
                if(nodePtr.nodeMap.keySet().contains(letter)) {//If there is a option traverse
                    this.treeWordSearch(stack, potentialWords, characters, nodePtr.nodeMap.get(letter), level+1);
                }
            }
        }
        
        stack.pop(); //Poping the letter off the stack
    }
    
    
    private void setupRootNodes() {
        //97 to 122
        for(int i = 97; i <= 122; i++) {
            char character = (char)i;
            this.rootNodes.put(character, new Node(character, 0));
        }   
    }
    
    public boolean addWord(String word) {
        //Make string lower case
        word = word.toLowerCase();
        
        //Checking to see if word is correc size
        if(word.length() != this.wordLength) {
            return false;
        }
        
        //Word is correct size
        boolean wordAdded = false;
        Node nodePtr = this.getRootNode(word.charAt(0));
        
        for(int i = 0; i < (this.wordLength-1); i++) { //i == Index
            char nextCharacter = word.charAt(i+1);
            
            //Check to see if next character is in hashMap of nodePtr
            //TRUE - traverse tree
            //FALSE - Create new node && traverse tree
            if(nodePtr.nodeMap.containsKey(nextCharacter)) {
                nodePtr = nodePtr.nodeMap.get(nextCharacter);
            } else {
                Node newNode = new Node(nextCharacter, i+1);
                nodePtr.nodeMap.put(nextCharacter, newNode);
                nodePtr = newNode;
                
                if(i == this.wordLength-2) {
                    wordAdded = true;
                    this.wordCount++;
                }
            }    
        }
        
        return wordAdded;
    }
    
    public boolean containsWord(String word) {
        //Make string lower case
        word = word.toLowerCase();
        
        //Checking to see if word is correc size
        if(word.length() != this.wordLength) {
            return false;
        }
        
        Node nodePtr = this.getRootNode(word.charAt(0));         
        for(int i = 0; i < (word.length()-1); i++) {
            char nextCharacter = word.charAt(i+1);
            
            if(nodePtr.nodeMap.containsKey(nextCharacter)) {
                nodePtr = nodePtr.nodeMap.get(nextCharacter);
            } else {
                return false;
            }
        }
        
        return true; //Word is found
    }
    
    private Node getRootNode(char character) {
        return this.rootNodes.get(character);
    }
    
    public int getSize() {
        return this.wordCount;
    }
    
    //Private Inner Class
    private class Node {
        //Fields
        public char character;
        public int index;
        public HashMap<Character, Node> nodeMap;
        
        //Constructors
        public Node(char character, int index) {
            this.character = character;
            this.index = index;
            this.nodeMap = new HashMap<>(26);
        }
    }
    
    
    
    //Main Method
    public static void main(String[] args) {
        WordTree tree = new WordTree(5);
        boolean ret = tree.addWord("death");
        boolean contains = tree.containsWord("death");
        boolean cont = tree.containsWord("words");
        
        System.out.println(ret);
        System.out.println(contains);
        System.out.println(cont);
    }
}
