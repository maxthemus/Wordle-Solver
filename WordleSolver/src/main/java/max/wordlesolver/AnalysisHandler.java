/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package max.wordlesolver;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 *
 * @author max
 */
public class AnalysisHandler {
    //Fields
    private int sum;
    private int wordCount;
    private double average;
    private int min;
    private int max;
    
    private WordleSolver solver;
    private StatHandler stats;
    
    private File file;
    private String pathToOuput;
    
    
    //Constructors
    public AnalysisHandler(String pathToFileInput, String pathToFileOutput) {
        //Setting up file
        try {
            this.file = new File(pathToFileInput);
            this.file.createNewFile();
        } catch(Exception e) {
            System.out.println(e);
        }
        
        this.sum = 0;
        this.wordCount = 0;
        this.average = 0.0;  
        this.max = Integer.MIN_VALUE;
        this.min = Integer.MAX_VALUE;
        
        this.pathToOuput = pathToFileOutput;
    }
    
    
    //Methods
    public void bruteForcer() {
        try {
            Scanner reader = new Scanner(this.file);
            
            String word = "";
            while(reader.hasNextLine()) {
                word = reader.nextLine();
                
//                System.out.println(word);
                
                this.solver = new WordleSolver(word, "./");
                this.solver.insertWord("death");
                this.solver.insertWord("skimp");
                this.solver.insertWord("crown");
                this.solver.insertWord("bulgy");
                
                
                this.stats = this.solver.getStatHandler();
                
                this.sum += this.stats.getPossibleWords();
                this.wordCount++;
                
                
                if(this.stats.getPossibleWords() < this.min) {
                    this.min = this.stats.getPossibleWords();
                } else if(this.stats.getPossibleWords() > this.max) {
                    System.out.println("UPDATED MAX");
                    this.max = this.stats.getPossibleWords();
                }
            }
            
            this.average = this.sum / this.wordCount;
            
            //Now we print the output
            File outputFile = new File(pathToOuput);
            FileWriter writer = new FileWriter(outputFile, true);

            
            writer.write("Possible Outcomes after \ndeath\nskimp\ncrown\nbulgy\n");
            writer.write("Average == " + this.average + "\n");
            writer.write("MIN ==" + this.min + "\n");
            writer.write("MAX == " + this.max + "\n");
            writer.write("+------------------------+"+"\n");
            
            writer.close();
        } catch(Exception e) {
            System.out.println(e);
        }
    }
    

    //Main Method
    public static void main(String[] args) {
        AnalysisHandler analysis = new AnalysisHandler("./words/FullWordSet", "./output/output.txt");
        
        analysis.bruteForcer();
    }
}

