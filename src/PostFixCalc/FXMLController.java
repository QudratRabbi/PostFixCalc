/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PostFixCalc;

import java.net.URL;
//import java.util.ResourceBundle;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * 
 */

//
// QUDRAT E RABBI
// PROF : JOHN BAUGH
// CIS 296
// PROGRAMMING ASSIGNMENT THREE - JAVAFX POST FIX CALCULATOR
//

public class FXMLController implements Initializable {

    @FXML
    private Label top_label; // To display info    
    
    private String ex_to_eval = ""; // Expression that will be passed in to check for validity and if valid. and evaluation
    
    //private Stack<Integer> staq;// Will be used to calculate the result    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      top_label.setText("_");      
    }
    
    @FXML
    private void num_one_action(ActionEvent event) { // Every button action appends a character into the string 
      ex_to_eval += "1";
      setDisplay();                                  // Calls set display function to setup display based on    
    }
    
    @FXML
    private void num_two_action(ActionEvent event) {
        ex_to_eval += "2";
      setDisplay();
    }
    
    @FXML
    private void num_three_action(ActionEvent event) {
        ex_to_eval += "3";
      setDisplay();
    }
    
    @FXML
    private void num_four_action(ActionEvent event) {
       ex_to_eval += "4";
      setDisplay();
    }
    
    @FXML
    private void num_five_action(ActionEvent event) {
        ex_to_eval += "5";
      setDisplay();
    }
    @FXML
    private void num_six_action(ActionEvent event) {
       ex_to_eval += "6";
      setDisplay();
    }
    
    @FXML
    private void num_seven_action(ActionEvent event) {
       ex_to_eval += "7";
      setDisplay();
    }
    
    @FXML
    private void num_eight_action(ActionEvent event) {
       ex_to_eval += "8";
      setDisplay(); 
    }
    
    @FXML
    private void num_nine_action(ActionEvent event) {
       ex_to_eval += "9";
      setDisplay(); 
    }
    
    @FXML
    private void num_zero_action(ActionEvent event) {
       ex_to_eval += "0";
      setDisplay();
    }
    
    @FXML
    private void cee_nothin_action(ActionEvent event) {// Resets the string and the display due to setDiplay()
        ex_to_eval = "";// Clears the string
      setDisplay();
    }
    
    @FXML
    private void oper_plus_action(ActionEvent event) {
        ex_to_eval += "+";
      setDisplay();
    }
    
    @FXML
    private void oper_minus_action(ActionEvent event) {
       ex_to_eval += "-";
      setDisplay();
    }
    
    @FXML
    private void oper_multi_action(ActionEvent event) {
        ex_to_eval += "*";
      setDisplay();
    }
    
    @FXML
    private void oper_div_action(ActionEvent event) {
       ex_to_eval += "/";
      setDisplay(); 
    }
    
    @FXML
    private void oper_space_action(ActionEvent event) {
       ex_to_eval += " ";
      setDisplay();
    }
    
    @FXML
    private void oper_calc_action(ActionEvent event) {
        String temp = ex_to_eval;
        if (validCheck(ex_to_eval)){
            ex_to_eval = "";
            if(postCalc(temp) == null ){
                ex_to_eval += "Invalid Zero division";
                setDisplay();
            }else
            {                
                ex_to_eval += Integer.toString(postCalc(temp));
                setDisplay();  
            }
        }else {
            top_label.setText("Invalid expression");
        }       
    }
    
    //Pre-Condition : Called after every button action to display the current expression
    //Post-Condition: Displays the current expression or result of an expression
    
    public void setDisplay(){
        if(ex_to_eval.length() == 0){ // When C is pressed, the calculator goes back to default state
            top_label.setText("_");
        }
        else{
            top_label.setText(ex_to_eval + "_"); // Displays the current expression(or evaluation) and a _ to know where the current operand or operation will be placed
        }
    }
    
    //Pre-Condition : When an postfix expression is validated, it is caulcated through this function
    //Post-Condition: Appends the result of the expression to an empty string to be displayed later
    
    public static java.lang.Integer postCalc(String expression){ // Takes the current expression and evaluates it (Doesnt work without valid postfix expression)
                                                                 // Only works if the expression is valid
                                                                 // java.lang.Integer allows null as return to signify zero division
        Stack<Integer> staq = new Stack<>();
        Scanner eval_u_ate = new Scanner(expression);
        
        while(eval_u_ate.hasNext()){ // While the expression still has next element
            if(eval_u_ate.hasNextInt()){ // if its integer
                staq.push(eval_u_ate.nextInt()); // pushes the next integer into stack 
            }
            else{
                
                int numero_uno = staq.pop(); // intergers are initiated and poped from stack 
                int numero_dos = staq.pop(); // Will be used to perform opertions 
                String operation = eval_u_ate.next();
                
                switch (operation) {
                    case "+":
                        staq.push(numero_dos + numero_uno); // result from operations is pushed into stack for further calculation
                        break;
                    case "-":
                        staq.push(numero_dos - numero_uno);
                        break;
                    case "*":
                        staq.push(numero_dos * numero_uno);
                        break;
                    case "/":
                        // when the dividend is zero
                        if(numero_uno == 0)          // its detected, and error is printed later
                        {
                            return null;             // Signifies zero division
                        }
                        else
                        {
                            staq.push(numero_dos / numero_uno);
                        }   break;
                    default:
                        break;
                }
                
            }
        }
        return staq.pop(); // Returns the top element, in this case the result 
    }
    
    //Pre-Condition : String passed in to check if valid postFix expression, crucial for postCalc to function
    //Post-Condition: 
    
    public boolean validCheck(String expression){    // Validates the postfix expression to be evaluated    
                
        char a;                   // for checking every element       
        boolean start = false;    // starting as false, used to keep track of previous element (whether digit or whitespace)   
        int counter = 0; // calculates number of elements on stack if evluated.
                         // Has to be 1, because the number of operands can exceed number of 
                         // operators only one        
        for(int i = 0; i < expression.length(); i++){
            a = expression.charAt(i);
            
            if( start == true && a == ' '){ // this whould be true when theres a whitespace after an operand
                                            
                start = false;
                
            } else if(start == false && a == ' '){ // True when whitespace is detected instead of operand
                                                   // Therefore invalid
                return false;
                
            } else if ( a == '-' || a == '+' || a == '*' || a == '/' ) // if not whitespace or digit it has to be an operator
            {
                
                if(counter < 2 || start){ // if there hasnt been two operands already, its invalid
                    
                    return false;    
                    
                }                
                counter--;// decrementing for operator
                
                start = true;                
            }
                else if(!Character.isDigit(a)) // If the starting character is not a digit, its invalid 
                    
                {  
                    
                    return false;
                
                }
                
                else if ( !start && Character.isDigit(a ))// every detected digits mean an operand, which increments counter
                {
                    counter++;
                    start = true; // 
                
            }           
        }
        return(counter == 1);// If counter isn't one, expression isn't valid        
    }   
}