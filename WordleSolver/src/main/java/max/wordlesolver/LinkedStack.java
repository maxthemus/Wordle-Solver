/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package max.wordlesolver;

/**
 *
 * @author max
 */
public class LinkedStack<E> {
    //Fields
    private Node<E> topNode;
    private int size;

    
    //Constructors
    public LinkedStack() {
        this.size = 0;
        this.topNode = null;
    }
    
    
    //Methods
    public int size() {
        return this.size;
    }
    
    public E pop() {
        if(this.topNode == null) {
            return null;
        }
        
        Node<E> tempNode = this.topNode;
        this.topNode = this.topNode.nextNode;
        this.size--;
        return tempNode.element;
    }
    
    public void push(E element) {
        Node<E> newNode = new Node<>(element);
        
        if(this.topNode == null) {
            this.topNode = newNode;
            this.size++;
        } else {
            newNode.nextNode = topNode;
            this.topNode = newNode;
            this.size++;
        }
    }
    
    public String getString() {
        String tempString = "";
        
        Node<E> nodePtr = this.topNode;
        for(int i = 0; i < this.size; i++) {
            tempString = nodePtr.element.toString() + tempString;
            nodePtr = nodePtr.nextNode;
        }
        
        return tempString;
    }
    
    
    //Private Inner Class
    private class Node<E> {
        //Fields
        public E element;
        public Node<E> nextNode;
        
        //Constructors
        public Node(E element) {
            this.element = element;
            this.nextNode = null;
        }
        
        //Methods
    }
    
    //Main Method
    public static void main(String[] args) {
        LinkedStack<Integer> stack = new LinkedStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        
        System.out.println(stack.getString());
        System.out.println(stack.size());
    }
}
