/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package max.wordlesolver;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is an implementation of a Trie data structure. Which stores characters in each node.
 * Each Node in the tree holds a HashMap which maps Characters to Nodes. Each link made between node 
 * represents that the characters belong together in-order from top of tree to leaf nodes.
 * 
 * Fields:
 * int wordLength : represents the max depth of the tree or the length of the words to be stored in the tree.
 * 
 * How the Trie works? The rootNodes is a HashMap of nodes which is initialized in the method called in the constructor.
 * Each node in the rootNode HashMap represents a letter in the alphabet.
 * When inserting a character you start at the root node and you find the rootNode representing the character at index 0 in the insert word.
 * Then you check the hashMap of that node and see if it contains the character at index 1 if it does then we traverse to that node and continue the cycle till,
 * we are at depth length-1, then we insert the node.
 * 
 * Example: words in Trie = {mat, may}
 * M -> A -> Y
 *        -> T
 * The A points to both Y and T
 * 
 * Example: words in Trie = {mat, may, eat}
 * M -> A -> Y
 *        -> T
 * E -> A -> T
 * The A from Mat, and May points to both Y and T BUT the A from EAT doesn't point to the T from Mat because the A from eat comes from E not M
 * 
 * @author max
 */
public class WordTree {
    //Fields
    private HashMap<Character, Node> rootNodes; //Holds first character of words in Trie
    private int wordCount; //Count of all words in Tree
    private final int wordLength; //Length that all words have to be in-order to be inserted into Trie

    //Constructors
    /**
     * Initializes a Trie data structure. initializes root nodes 
     * @param wordLength Length of words in Trie.
     */
    public WordTree(int wordLength) {
        this.wordLength = wordLength;
        this.wordCount = 0;
        this.rootNodes = new HashMap<>(26);
        
        this.setupRootNodes();
    }
    
    
    //Methods
    /**
     * Initializes the root nodes of the tree so that there are 26 root nodes,
     * each representing a different letter in the alphabet
     */
    private void setupRootNodes() {
        //97 to 122
        for(int i = 97; i <= 122; i++) {
            char character = (char)i;
            this.rootNodes.put(character, new Node(character, 0));
        }   
    }
    
    /**
     * Method gets all potential words. Takes in a 2D array of characters which represents,
     * what characters can be used at each index.
     * 
     * @param characters 2D array of characters which represents [INDEX][CHARACTERS_AVALIABLE]
     * @return ArrayList of Strings found that satisfies the @param characters
     */
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
    
    /**
     * Recursive method that traverses the tree searching for words that satisfy the @param characters 2D array.
     * Will check to see if the Trie can be traversed further EG if there is a link to the next character. 
     * IF gets to leaf node EG depth == height of tree. Then we have found a word and we then reverse the stack and that gives us the word.
     * we then add that string to the @param potentialWords.
     * 
     * @param stack traversal stack for keeping track of how the tree is being traversed.
     * @param potentialWords ArrayList of Strings found that satisfies the @param characters
     * @param characters 2D array of characters which represents [INDEX][CHARACTERS_AVALIABLE]
     * @param nodePtr is the Node that we are currently looking at in the traversal
     * @param level the current level in the traversal
     */
    private void treeWordSearch(LinkedStack<Character> stack, ArrayList<String> potentialWords, ArrayList<Character>[] characters, Node nodePtr, int level) { 
        stack.push(nodePtr.character);//Pushing character onto the stack
        
        //If is leaf node
        if(level == this.wordLength) {
            String word = stack.getString();
            potentialWords.add(word); //Adding word to the ArrayList
            
        } else {
            //Loop through all potential options on this level
            for(char letter : characters[level]) {
                if(nodePtr.nodeMap.keySet().contains(letter)) {//If there is a option traverse
                    this.treeWordSearch(stack, potentialWords, characters, nodePtr.nodeMap.get(letter), level+1); //Traver the tree to the next level
                }
            }
        }
        
        stack.pop(); //Poping the letter off the stack (Traversing back up the stack 1 level to node that this one came from)
    }
    
    /**
     * Adds a word to the Trie. Traverses down the Trie adding nodes where needed.
     * Doesn't add words that don't meet the size requirement of the Trie.
     * 
     * @param word word to be added to the Trie
     * @return Boolean True if added else False if error or not added
     */
    public boolean addWord(String word) {
        word = word.toLowerCase();//Make string lower case

        //Checking to see if word is correc size
        if(word.length() != this.wordLength) {
            return false; //Word not right length
        }
        
        boolean wordAdded = false;
        Node nodePtr = this.getRootNode(word.charAt(0));//Pointer to node we are looking at in traversal of tree
        
        for(int i = 0; i < (this.wordLength-1); i++) { //i == Index
            char nextCharacter = word.charAt(i+1); //Getting the next character in the @param word
            
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
    
    
    /**
     * Traverses tree to check if word is in Trie.
     * 
     * @param word word to search for
     * @return Boolean True if word is found else False word wasn't found
     */
    public boolean containsWord(String word) {
        //Make string lower case
        word = word.toLowerCase();
        
        //Checking to see if word is correc size
        if(word.length() != this.wordLength) {
            return false;
        }
        
        Node nodePtr = this.getRootNode(word.charAt(0));//Pointer to node we are looking at in traversal of tree    
        for(int i = 0; i < (word.length()-1); i++) { //Looping through @param word
            char nextCharacter = word.charAt(i+1); //Getting next character in the word
            
            if(nodePtr.nodeMap.containsKey(nextCharacter)) { //Checking to see if the nextCharacter is a key in the nodePtr HashMap
                nodePtr = nodePtr.nodeMap.get(nextCharacter);//Traverse to the node that represents the next character in this substring
            } else {
                return false; //No path found so word doesn't exist in Trie
            }
        }
        //If control flow gets to this point then word has to be in Trie
        return true; //Word is found
    }
    
    //Returns root node of specific character
    private Node getRootNode(char character) {
        return this.rootNodes.get(character);
    }
    
    //Returns count of words in Trie
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
//    public static void main(String[] args) {
//        //Quick demonstration of the WordTree class
//        WordTree tree = new WordTree(5);
//        boolean ret = tree.addWord("death");
//        boolean contains = tree.containsWord("death");
//        boolean cont = tree.containsWord("words");
//        
//        System.out.println(ret);
//        System.out.println(contains);
//        System.out.println(cont);
//    }
}
