package ch.noseryoung.app.app;

import java.time.LocalDate;
import java.util.Scanner;

import ch.noseryoung.app.exceptions.*;

public class TriangleApp {

  private String company;
  private String handler;

  private boolean isRunning;

  private String sideAInput;
  private String sideBInput;
  private String sideCInput;

  private double sideA;
  private double sideB;
  private double sideC;

  private String code;

  public TriangleApp(String company, String handler) {
    this.company = company;
    this.handler = handler;
    this.isRunning = true;
  }

  public void start() {
    printHeader();
    while (isRunning) {
      System.out.println("\nTEST CASES TRIANGLE\n");

      sideAInput = promptSide("Side A");
      sideBInput = promptSide("Side B");
      sideCInput = promptSide("Side C");

      try {
        validateInput();
        code = determineTriangleType();
      } catch (TriangleException e) {
        code = e.getMessage();
      } finally {
        printResult();
        promptAction();
      }
    }
  }

  private void printHeader() {
    System.out.println("*******************************");
    System.out.println("*     Triangle Calculator     *");
    System.out.println("* " + company + " - " + handler + " *");
    System.out.println("*           " + LocalDate.now().getYear() + "           *");
    System.out.println("*******************************");
  }

  private void printResult() {
    System.out.println("RESULT: " + code);
    
    String interpretation = "";
    
    switch (code) {
      case "TRI84TF":
        interpretation = "Isosceles Triangle (Gleichschenkliges Dreieck)";
        break;
      case "TRI66TF":
        interpretation = "Equilateral Triangle (Gleichseitiges Dreieck)";
        break;
      case "TRI60TF":
        interpretation = "Scalene Triangle (Gewöhnliches Dreieck)";
        break;
      case "TRI72TF":
        interpretation = "Right-angled Triangle (Rechtwinkliges Dreieck)";
        break;
      case "ERR36TF":
        interpretation = "Impossible Triangle (Unmögliches Dreieck)";
        break;
      case "ERR96TF":
        interpretation = "Invalid Input - Letters or Special Characters";
        break;
      case "ERR12TF":
        interpretation = "Invalid Input - Negative Numbers";
        break;
      case "ERR16TF":
        interpretation = "Invalid Input - Zero Length Side";
        break;
      case "ERR56TF":
        interpretation = "Invalid Input - Triangle Forms a Line";
        break;
      default:
        interpretation = "Unknown Code";
        break;
    }
    
    System.out.println("=> " + interpretation);
  }

  private String promptSide(String side) {
    Scanner scanner = new Scanner(System.in);
    System.out.print(side + ": ");
    return scanner.nextLine().trim();
  }

  private void promptAction() {
    String action = "";
    Scanner scanner = new Scanner(System.in);
    System.out.println("\nPress 'c' to continue or 'q' to quit");
    while (!action.equals("q") && !action.equals("c")) {
      System.out.print("> ");
      action = scanner.nextLine().toLowerCase().trim();
    }
    if (action.equals("q")) {
      isRunning = false;
      System.out.println("Thank you for using Triangle Calculator!");
    }
    
    System.out.println("******************************");
  }

  private void validateInput() throws TriangleException {
    try {
      sideA = Double.parseDouble(sideAInput);
      sideB = Double.parseDouble(sideBInput);
      sideC = Double.parseDouble(sideCInput);
      
    } catch (NumberFormatException nfe) {
      throw new IllegalTriangleSideException();
    }

    if (sideA == 0 || sideB == 0 || sideC == 0) {
      throw new ZeroTriangleSideException();
    }
    if (sideA < 0 || sideB < 0 || sideC < 0) {
      throw new NegativeTriangleSideException();
    }
    
    if (sideA + sideB <= sideC || sideA + sideC <= sideB || sideB + sideC <= sideA) {
      throw new ImpossibleTriangleException();
    }
    
    if (sideA + sideB == sideC || sideA + sideC == sideB || sideB + sideC == sideA) {
      throw new TriangleIsLineException();
    }
  }

  private String determineTriangleType() {
    final double EPSILON = 0.0001; // Für Gleitkomma-Vergleiche
    
    // sind alle seiten gleich?
    if (Math.abs(sideA - sideB) < EPSILON && Math.abs(sideB - sideC) < EPSILON) {
      return "TRI66TF"; // Gleichseitiges Dreieck
    }
    
    // Rechtwinkliges Dreieck?
    boolean isRightAngled = 
        Math.abs(Math.pow(sideA, 2) + Math.pow(sideB, 2) - Math.pow(sideC, 2)) < EPSILON ||
        Math.abs(Math.pow(sideA, 2) + Math.pow(sideC, 2) - Math.pow(sideB, 2)) < EPSILON ||
        Math.abs(Math.pow(sideB, 2) + Math.pow(sideC, 2) - Math.pow(sideA, 2)) < EPSILON;
    
    if (isRightAngled) {
      return "TRI72TF"; // Rechtwinkliges Dreieck
    }
    
    // zwei gleiche seiten?
    if (Math.abs(sideA - sideB) < EPSILON || Math.abs(sideB - sideC) < EPSILON || Math.abs(sideA - sideC) < EPSILON) {
      return "TRI84TF"; // Gleichschenkliges Dreieck
    }
    
    // keine seiten gleich
    return "TRI60TF"; // Gewöhnliches Dreieck
  }
}