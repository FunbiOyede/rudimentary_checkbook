/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkboook;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.System.exit; //The scanner class imported to take inputs from the user 
import java.util.ArrayList;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;




/**
 * @author Habeeb Taiwo Grillo
 * @author Funbi Oyede
 * @author Jacob Franklyn
 * @author Joshua Ozoya 
 */

public class Checkboook implements CheckBookInterface{
    
    
 Scanner sc = new Scanner(System.in); //instantiating the scanner class imported above to get data from the console in both the withdraw and deposit methods
 static ArrayList transactions = new ArrayList();
    public static double balance = 0; //a balance variable for the final amount deposited and withdrawn  
 
    public double deposit_amount, withdraw_amount; // a deposit_amount and withdraw_amount variables for taking the initial value of the amount deposited and withdrawn

    public static double main_bal;  
 
    public static double ArrayMainBal;
    
    
    
      @Override //An abstract method from the CheckBookInterface that perform the depsoit transaction
    public void Deposit(){
          System.out.println("Enter Amount");
    deposit_amount = sc.nextDouble(); //Accepting a deposit value from the console

    balance = deposit_amount + balance; //updating balance

    double depositBalanceForArray = balance;
    transactions.add(depositBalanceForArray);
    System.out.println("Balance: " + balance); //printing the final value to the console

    
    
     check(); //A call back function asking the user for options on either withdrawal or deposit

   
    }
      
     
     @Override //   An abstract method that from the CheckBookInterface that perform the Withdrawal transaction
    public void Withdraw(){
         System.out.println("Enter Amount");
       withdraw_amount = sc.nextDouble();    //Accepting a withdrawal value from the console
       balance = balance - withdraw_amount;  //updating balance
      
       
       if (withdraw_amount > balance){
           System.out.println("Insufficient funds");
           check();
       
        }else if(balance != 0.0){ //checking if the final balance in the account is not empty, if it is not empty , it prints the balance remaining to the console

            System.out.println("Balance: " + balance);
            double withdrawalBalance = balance;
            transactions.add(withdrawalBalance);
        
       }
       else{ //if it is empty it prints account empty to the console and exits the application
            
            System.out.println("Account Empty");
            exit(0);
        }
       check();
    }

    
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

          check(); //       asking the user agent for options on either withdrawal or deposit
//        balance += main_bal;
//        System.out.println("main balance" + main_bal);

    }
    
    public static void check() { //The method respnsible for asking the user agent for options on either withdrawal or deposit

    Scanner sc = new Scanner(System.in); //A new instantiation of the scanner class because check(); is a static method

    Checkboook objCheck = new Checkboook(); //An instantiation of the present class i.e. Checkboook to be able to access the deposit and withdrawal method to perform transactions

    System.out.println("Enter 'Deposit' or 'Withdraw' \nEnter 'Quit' to leave the program");
    
       String choice = sc.nextLine();
       
       
      
         if(choice.matches("[0-9]+"))  {
             System.out.println("String expected");
             System.out.println("Try again");
             check();
         }
       
       
    
        if(choice.equalsIgnoreCase("deposit")){
           objCheck.Deposit(); 
           check();

        }else if(choice.equalsIgnoreCase("withdraw")){
           objCheck.Withdraw();
           check();
        } else if(choice.equalsIgnoreCase("quit")){
           objCheck.Quit(transactions);

        }else {
            System.out.println("Invalid selection");
            System.out.println("Try again");
            check();
        }
    }
    
      public void Quit(ArrayList obj) //The quit method to stop or terminate the program
     {
           System.out.println("the value in the array is");
     try {        
         saveToFile(obj,"checkbook.txt");  //saveToFile saves all transactions for both deposit and withdrawal
     } catch (FileNotFoundException ex) {
         Logger.getLogger(Checkboook.class.getName()).log(Level.SEVERE, null, ex);
     }
           

     try {
         Thread.sleep(2000); //           stop for 2 seconds then terminates
     } catch (InterruptedException ex) {
         Logger.getLogger(Checkboook.class.getName()).log(Level.SEVERE, null, ex);
     }
     exit(0);
        
         
     }
      //saveToFile saves all transactions for both deposit and withdrawal
      public static void saveToFile(ArrayList<Double> transactionsValues, String fileName) throws FileNotFoundException {
          //writing to file using the printWriter class
          PrintWriter writer = new PrintWriter(fileName);
          for(Double Line : transactionsValues)
          {
             writer.println( "Balance is " + Line);
          }
          writer.close();
          
     try {
         readFromFile();
     } catch (IOException ex) {
         Logger.getLogger(Checkboook.class.getName()).log(Level.SEVERE, null, ex);
     }
      }
      
      //method responsible for readd transaction details to file
      public static void readFromFile() throws IOException{
      Path filePath = Paths.get("checkbook.txt");
        try(BufferedReader reader = Files.newBufferedReader(filePath,Charset.defaultCharset())){
            String Line = null;
            while((Line = reader.readLine()) != null){
                System.out.println(Line);
            }
      
      }
      
    }
}

    

     



