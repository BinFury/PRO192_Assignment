/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

/**
 *
 * @author AnhTN
 * @date 07-03-2025
 * @description
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import classes.com.*;

public class CarManager {

    public static void main(String[] args) throws IOException {
        int choice;
        boolean checkSuccessful;
        String fileCarsName = "Cars.txt";
        String fileBrandsName = "Brands.txt";
        BrandList brandList = new BrandList(); //Create an empty BrandList
        CarList carList = new CarList(brandList); //Create an empty CarList
        brandList.loadFromFile(fileBrandsName); //Load brands from the file brands.txt to brandList
        carList.loadFromFile(fileCarsName); //Load cars from the file cars.txt to carList
        String bID;
        ArrayList<String> ops = new ArrayList<>(15); //Create ArrayList ops of strings containing options of the program
        Scanner sc = new Scanner(System.in);

        ops.add("1 - List all brands");
        ops.add("2 - List all brands by descending of price");
        ops.add("3 - Add a new brand");
        ops.add("4 - Search a brand based on its ID");
        ops.add("5 - Update a brand");
        ops.add("6 - Remove a brand");
        ops.add("7 - Save brands to the file, named brands.txt");
        ops.add("8 - List all cars in ascending order of brand names");
        ops.add("9 - List cars based on a part of an input brand name");
        ops.add("10 - Add a car");
        ops.add("11 - Remove a car based on its ID");
        ops.add("12 - Update a car based on its ID");
        ops.add("13 - Save cars to file, named cars.txt");
        ops.add("14 - Advanced sreaching");
        ops.add("15 - Exit program");

        Menu menu = new Menu(); //Create a menu

        do {
            //In ra menu
            choice = menu.int_getChoice(ops);
            switch (choice) {
                case 1:
                    brandList.listBrands();
                    break;
                case 2:
                    brandList.sortBrandsByPriceDescending();
                    break;
                case 3:
                    brandList.addBrand();
                    break;
                case 4:
                    System.out.print("Input brand ID: ");
                    bID = sc.nextLine();
                    if (brandList.searchID(bID) == -1) {
                        System.out.println("Brand ID not found!");
                    } else {
                        System.out.println(brandList.get(brandList.searchID(bID)).toString());
                    }
                    break;
                case 5:
                    brandList.updateBrand();
                    break;
                case 6:
                    System.out.print("Input brand ID: ");
                    bID = sc.nextLine();
                    brandList.removeBrand(bID);
                    break;
                case 7:
                    if (brandList.saveToFile(fileBrandsName)) {
                        System.out.println("Saved successfully!");
                    } else {
                        System.out.println("Save failed!");
                    }
                    break;
                case 8:
                    carList.listCars();
                    break;
                case 9:
                    carList.printBasedBrandName();
                    break;
                case 10:
                    carList.addCar();
                    break;
                case 11:
                    checkSuccessful = carList.removeCar();
                    if (checkSuccessful) {
                        System.out.println("Car removed successfully!");
                    } else {
                        System.out.println("Not found!");
                    }
                    break;
                case 12:
                    carList.updateCar();
                    break;
                case 13:
                    if (carList.saveToFile(fileCarsName)) {
                        System.out.println("Saved successfully!");
                    } else {
                        System.out.println("Save failed!");
                    }
                    break;
                case 14:
                    carList.searchAdvanced();
                    break;
                case 15:
                    System.out.print("Do you want to save file before exiting? (y/n): ");
                    String checkSave = sc.nextLine().trim().toLowerCase();
                    if (checkSave.equals("y")) {
                        brandList.saveToFile("brands.txt");
                        carList.saveToFile("cars.txt");
                    }
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 15);

    }

}
