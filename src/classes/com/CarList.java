/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes.com;

/**
 *
 * @author AnhTN
 * @date 07-03-2025
 * @description
 */
import java.io.*;
import java.util.*;
import java.util.Scanner;

public class CarList extends ArrayList<Car> {

    //Các thuộc tính chính của xe
    private String carID, color, frameID, engineID;
    private Brand brand;
    Menu menu = new Menu();
    Scanner scanner = new Scanner(System.in);
    BrandList brandList; //Danh sách thương hiệu (BrandList) được truyền vào từ bên ngoài

    //Constructor khởi tạo danh sách xe dựa trên danh sách thương hiệu có sẵn
    public CarList(BrandList bList) {
        brandList = bList;
    }

    //Load dữ liệu xe từ file
    public boolean loadFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] arr = line.split(",");
                if (arr.length < 5) { // Kiểm tra đủ dữ liệu
                    System.out.println("Invalid data format: " + line);
                    continue;
                }

                carID = arr[0].trim();
                brand = brandList.get(brandList.searchID(arr[1].trim())); //Tìm brand tương ứng
                color = arr[2].trim();
                frameID = arr[3].trim();
                engineID = arr[4].trim();

                // Thêm xe vào danh sách
                this.add(new Car(carID, brand, color, frameID, engineID));
            }
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("File " + fileName + " not found!");
        } catch (IOException e) {
            System.out.println("Error reading file: " + fileName);
        }
        return false;
    }

    //Open the file based on the filename to write data in line-by-line in text format
    public boolean saveToFile(String fileName) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            for (Car car : this) {
                pw.println(car.getCarID() + "," + car.getBrand().getBrandID() + ","
                        + car.getColor() + "," + car.getFrameID() + "," + car.getEngineID());
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error writing to file: " + fileName);
        }
        return false;
    }

    //Search a car based on car ID
    public int searchID(String carID) {
        for (int i = 0; i < this.size(); i++) {
            if (carID.equals(this.get(i).getCarID())) {
                return i;
            }
        }
        return -1;
    }

    //Search a car by its engine ID. Use in checking frames are not duplicated.
    private int searchEngineID(String searchEngineID) {
        for (int i = 0; i < this.size(); i++) {
            if (searchEngineID.equals(this.get(i).getEngineID())) {
                return i;
            }
        }
        return -1;
    }

    //Search a car by its frame ID. Use in checking engines are not duplicated.
    private int searchFrameID(String searchFrameID) {
        for (int i = 0; i < this.size(); i++) {
            if (searchFrameID.equals(this.get(i).getFrameID())) {
                return i;
            }
        }
        return -1;
    }

    //Add car to the set
    public void addCar() {
        //Kiểm tra carID đã tồn tại chưa
        do {
            System.out.print("Input car ID: ");
            carID = scanner.nextLine();
            if (searchID(carID) != -1) {
                System.out.println("This car ID already exists. Try another one!");
            } else {
                break;
            }
        } while (true);

        //Chọn thương hiệu từ menu
        Brand brand = menu.ref_getChoice(brandList);

        do {
            System.out.print("Input color: ");
            color = scanner.nextLine();
            if (!color.isEmpty()) {
                break;
            }
            System.out.println("The color cannot be blank. Try again !");
        } while (true);

        // Nhập frame ID
        while (true) {
            System.out.print("Input frame ID: ");
            frameID = scanner.nextLine().trim();

            if (!frameID.matches("F[0-9][0-9][0-9][0-9][0-9]")) {
                System.out.println("The frame ID must be in F00000 format. Try again!");
                continue;
            }
            if (searchFrameID(frameID) != -1) {
                System.out.println("The frame ID is duplicated. Try again!");
                continue;
            }
            break;
        }

        // Nhập engine ID
        while (true) {
            System.out.print("Input engine ID: ");
            engineID = scanner.nextLine().trim();

            if (!engineID.matches("E[0-9][0-9][0-9][0-9][0-9]")) {
                System.out.println("The engine ID must be in E00000 format. Try again!");
                continue;
            }
            if (searchEngineID(engineID) != -1) {
                System.out.println("The engine ID is duplicated. Try again!");
                continue;
            }
            break;
        }

        this.add(new Car(carID, brand, color, frameID, engineID));
        System.out.println("Car has added successfully !");
    }

    public void printBasedBrandName() {
        System.out.print("Enter part of brand name: ");
        String part = scanner.nextLine();
        int count = 0;
        for (Car c : this) {
            if (c.getBrand().getBrandName().toLowerCase().contains(part.toLowerCase())) {
                System.out.println(c.screenString());
                count++;
            }
        }
        if (count == 0) {
            System.out.println("No result!");
        } else {
            System.out.println("Total car found: " + count);
        }
    }

    //Remove a car based on it’s ID
    public boolean removeCar() {
        int pos;
        String removedID;
        System.out.print("Input car ID to removed: ");
        removedID = scanner.nextLine();
        pos = searchID(removedID);
        if (pos != -1) {
            this.remove(pos);
            return true;
        }
        return false;
    }

    //Update a car based on it’s ID
    public boolean updateCar() {
        System.out.print("Input car ID to update: ");
        String updatedID = scanner.nextLine().trim();

        if (updatedID.isEmpty()) {
            System.out.println("Car ID cannot be empty. Try again!");
            return false;
        }

        int pos = searchID(updatedID);
        if (pos == -1) {
            System.out.println("Car ID does not exist. Please check and try again!");
            return false;
        }

        Brand brand = menu.ref_getChoice(brandList);

        do {
            System.out.print("Input color: ");
            color = scanner.nextLine().trim();
            if (!color.isEmpty()) {
                break;
            }
            System.out.println("The color must not be empty. Try again!");
        } while (true);

        // Nhập Frame ID
        do {
            System.out.print("Input frame ID: ");
            frameID = scanner.nextLine().trim();

            if (!frameID.matches("F[0-9][0-9][0-9][0-9][0-9]")) {
                System.out.println("The frame ID must be in F00000 format. Try again!");
                continue;
            }
            if (searchFrameID(frameID) != -1) {
                System.out.println("The frame ID is duplicated. Try again!");
                continue;
            }
            break;
        } while (true);

        // Nhập Engine ID
        do {
            System.out.print("Input engine ID: ");
            engineID = scanner.nextLine().trim();

            if (!engineID.matches("E[0-9][0-9][0-9][0-9][0-9]")) {
                System.out.println("The engine ID must be in E00000 format. Try again!");
                continue;
            }
            if (searchEngineID(engineID) != -1) {
                System.out.println("The engine ID is duplicated. Try again!");
                continue;
            }
            break;
        } while (true);

        this.get(pos).setUpdatedCar(brand, color, frameID, engineID);
        System.out.println("Car with ID " + updatedID + " has been updated successfully!");
        return true;
    }

    public void listCars() {
        Collections.sort(this);
        int idWidth = "ID".length();
        int brandWidth = "Brand ID".length();
        int colorWidth = "Color".length();
        int frameWidth = "Frame ID".length();
        int engineWidth = "Engine ID".length();

        for (Car c : this) {
            idWidth = Math.max(idWidth, c.getCarID().length());
            brandWidth = Math.max(brandWidth, c.getBrand().getBrandID().length());
            colorWidth = Math.max(colorWidth, c.getColor().length());
        }
        // Định dạng bảng
        String format = "| %-" + idWidth + "s | %-" + brandWidth + "s | %-" + colorWidth + "s | %-" + frameWidth + "s | %-" + engineWidth + "s |\n";
        int tableWidth = idWidth + brandWidth + colorWidth + frameWidth + engineWidth + 16;

        // Tạo dòng kẻ
        String separator = new String(new char[tableWidth]).replace('\0', '-');

        // In tiêu đề bảng
        System.out.println(separator);
        System.out.printf(format, "ID", "Brand ID", "Color", "FrameID", "Engine ID");
        System.out.println(separator);

        // In dữ liệu
        for (Car c : this) {
            System.out.printf(format, c.getCarID(), c.getBrand().getBrandID(), c.getColor(), c.getFrameID(), c.getEngineID());
        }

        // Kết thúc bảng
        System.out.println(separator);
    }

    //Search car base on max price, color, soundbrand
    public void searchAdvanced() {
        System.out.println("=================Advanced Search=================");

        //Input min price, 0 is used to skip this criterion
        double minPrice;
        while (true) {
            System.out.print("Enter minimum price (or 0 to skip): ");
            try {
                minPrice = Double.parseDouble(scanner.nextLine().trim());
                if (minPrice < 0) {
                    System.out.println("Minimum price cannot be negative!");
                } else {
                    break; // Exit loop if input is valid
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number or 0 to skip.");
            }
        }

        //Input max price, -1 is used to skip this criterion
        double maxPrice;
        while (true) {
            System.out.print("Enter maximum price (or -1 to skip): ");
            try {
                maxPrice = Double.parseDouble(scanner.nextLine().trim());
                if (maxPrice < 0 && maxPrice != -1) {
                    System.out.println("Maximum price cannot be negative (except -1 to skip)!");
                } else {
                    break; // Exit loop if input is valid 
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number or -1 to skip.");
            }
        }

        //Check maxPrice >= minPrice
        if (minPrice != 0 && maxPrice != -1 && minPrice > maxPrice) {
            System.out.println("Minimum price cannot be greater than maximum price!");
            return;
        }

        // Input color
        System.out.print("Enter color (or leave blank to skip): ");
        String findColor = scanner.nextLine().trim();

        // Input sound brand
        System.out.print("Enter sound brand (or leave blank to skip): ");
        String soundBrand = scanner.nextLine().trim();

        // Create a new ArrayList to store cars that match the criteria
        ArrayList<Car> matchedCars = new ArrayList<>();

        for (Car c : this) {
            boolean matches = true;
            double carPrice = c.getBrand().getPrice();

            // Check minPrice
            if (minPrice != 0 && carPrice < minPrice) {
                matches = false;
            }
            // Check maxPrice
            if (maxPrice != -1 && carPrice > maxPrice) {
                matches = false;
            }
            // Check color
            if (!findColor.isEmpty() && !c.getColor().equalsIgnoreCase(findColor)) {
                matches = false;
            }
            // Check sound brand
            if (!soundBrand.isEmpty() && !c.getBrand().getSoundBrand().equalsIgnoreCase(soundBrand)) {
                matches = false;
            }

            if (matches) {
                matchedCars.add(c); // Add car to the matched list 
            }
        }

        // Print the output list of matching cars
        System.out.println("==============Advanced search result==============");
        int count = matchedCars.size();
        if (count == 0) {
            System.out.println("No car matches the criteria!");
        } else {
            System.out.println("Total cars found: " + count);
            // Output each matching car using the screenString method
            for (Car c : matchedCars) {
                System.out.println(c.screenString());
            }
        }
    }
}
