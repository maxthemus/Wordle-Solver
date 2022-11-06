/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package max.wordlesolver;

import java.util.HashMap;

/**
 *
 * @author max
 */
public class StatusSetController {
    //Fields
    private HashMap<Status, LetterSet> statusMap; //Maps status to letterSet
    private HashMap<Character, Letter> characterMap; //Maps character to status
    
    //Constructors
    public StatusSetController() {
        this.statusMap = new HashMap<>();
        this.characterMap = new HashMap<>();
        
        this.setUpStatusSet(); //setting up the sets
    }
    
    
    //Methods
    private void setUpStatusSet() {
        //Setting up status map
        LetterSet green = new LetterSet(Status.GREEN);
        LetterSet yellow = new LetterSet(Status.YELLOW);
        LetterSet grey = new LetterSet(Status.GREY);
        LetterSet unused = new LetterSet(Status.UNUSED);
        
        //Adding letters to unusedSet
        //97 to 122
        for(int i = 97; i <= 122; i++) {
            Letter tempLetter = new Letter((char)i);
            this.characterMap.put((char)i, tempLetter);
            unused.add(tempLetter);
        }
        
        this.statusMap.put(Status.UNUSED, unused);
        this.statusMap.put(Status.GREEN, green);
        this.statusMap.put(Status.YELLOW, yellow);
        this.statusMap.put(Status.GREY, grey);
    }
    
    public Letter getLetter(char character) {
        return this.characterMap.get(character);
    }
    
    public LetterSet getSet(Status status) {
        return this.statusMap.get(status);
    }
    
    public void moveLetter(Letter letter, Status newStatus) {
        Status status = letter.getStatus();
        
        if(newStatus != Status.GREEN) { //IF not green then we want to remove from old set
            LetterSet originalSet = this.statusMap.get(status);
            originalSet.remove(letter);            
        } else { //IF IT IS GREEN THEN WE WANT TO MOVE TO YELLOW ISWELL FOR DUPLICATES
            if(status != Status.YELLOW) { //IF it wasn't in yellow move to yellow
                LetterSet yellowSet = this.statusMap.get(Status.YELLOW);
                yellowSet.add(letter);
            }
        }
        LetterSet newSet = this.statusMap.get(newStatus);
        newSet.add(letter);
    }
    
    public HashMap<Character, Letter> getCharacterMap() {
        return this.characterMap;
    }

    @Override
    public String toString() {
        String tempString = "";
        
        for(LetterSet s : this.statusMap.values()) {
            tempString += s.toString() + "\n";
        }
        
        return tempString;
    }
    
    
    
    
    //Main Method
    public static void main(String[] args) {
        StatusSetController controller = new StatusSetController();
        Letter letter = controller.getLetter('a');
        
        controller.moveLetter(letter, Status.GREY);
        
        System.out.println(controller.toString());
        
    }
}
