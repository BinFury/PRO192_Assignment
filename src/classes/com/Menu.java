/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes.com;

/**
 *
 * @author AnhTN
 * @date 06-03-2025
 * @description
 */
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    private int response;
    private Scanner sc = new Scanner(System.in);

    //For print the menu list
    public int int_getChoice(ArrayList<String> options) {
        System.out.println("----------------------------------------------------------");
        System.out.println("|                   CAR MANAGEMENT                       |");
        System.out.println("----------------------------------------------------------");
        String format = "| %-" + 55 + "s|\n";
        for (String i : options) {
            System.out.printf(format, i);
        }
        System.out.println("----------------------------------------------------------");
        while (true) {
            try {
                System.out.printf("Please choose an option 1..%d: ", options.size());
                String input = sc.nextLine().trim();
                return response = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }

    //Get user choice as an integer
    public int int_getChoice(BrandList brand) {
        int n = brand.size();
        for (int i = 0; i < n; i++) {
            System.out.println("" + (i + 1) + ". " + brand.get(i));
        }
        while (true) {
            try {
                System.out.printf("Please choose an option 1..%d: ", n);
                String input = sc.nextLine().trim();
                return response = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }

    //Get user choice as an object in the list
    public Brand ref_getChoice(BrandList options) {
        int n = options.size();
        System.out.println("Brand ID List:");
        do {
            response = int_getChoice(options);
        } while ((response < 0) || (response > n));
        return options.get(response - 1);
    }
}
