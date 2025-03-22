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

public class BrandList extends ArrayList<Brand> {

    //Các biến lưu trữ thông tin thương hiệu
    private String brandID, brandName, soundBrand;
    private double price;
    private int pos;

    Scanner scanner = new Scanner(System.in); //Scanner để nhập dữ liệu từ bàn phím

    //Đọc dữ liệu brand từ file và lưu vào danh sách
    public boolean loadFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] arr = line.split(",");
                if (arr.length < 3) { // Kiểm tra xem có đủ phần tử không
                    System.out.println("Invalid data format: " + line);
                    continue; // Bỏ qua dòng lỗi
                }

                brandID = arr[0].trim();
                brandName = arr[1].trim();
                soundBrand = arr[2].split(":")[0].trim(); //Chia arr[2] thành 2 arr theo dấu : và gán phần tử đầu tiên vào soundBrand
                price = Double.parseDouble(arr[2].split(":")[1].trim());

                // Thêm vào danh sách
                this.add(new Brand(brandID, brandName, soundBrand, price));
            }
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("File " + fileName + " not found!");
        } catch (IOException e) {
            System.out.println("Error reading file: " + fileName);
        }
        return false;
    }

    //Lưu danh sách brand vào file.
    public boolean saveToFile(String fileName) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            for (Brand brand : this) {
                pw.println(brand.getBrandID() + "," + brand.getBrandName() + ","
                        + brand.getSoundBrand() + ":" + brand.getPrice());
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error writing to file: " + fileName);
        }
        return false;
    }

    //Tìm kiếm vị trí brand trong danh sách dựa vào brandID.
    public int searchID(String bID) {
        for (int i = 0; i < this.size(); i++) {
            if (bID.equals(this.get(i).getBrandID())) {
                return i;
            }
        }
        return -1;
    }

    //Cho phép người dùng chọn một thương hiệu từ danh sách
    public Brand getUserChoice() {
        Menu menu = new Menu();
        return (Brand) menu.ref_getChoice(this);
    }

    //Add a new Brand to the list
    public void addBrand() {
        //Nhập brandID, kiểm tra trùng lặp
        do {
            System.out.print("Input brand ID: ");
            brandID = scanner.nextLine();
            if (searchID(brandID) != -1) {
                System.out.println("This brand ID already exists. Try another one!");
            } else {
                break;
            }
        } while (true);

        //Nhập tên thương hiệu
        do {
            System.out.print("Input brand name: ");
            brandName = scanner.nextLine();
            if (!brandName.isEmpty()) {
                break;
            }
            System.out.println("The brand name cannot be blank. Try again!");
        } while (true);

        //Nhập sound brand
        do {
            System.out.print("Input sound brand: ");
            soundBrand = scanner.nextLine();
            if (!soundBrand.isEmpty()) {
                break;
            }
            System.out.println("The sound brand cannot be blank. Try again!");
        } while (true);

        //Nhập giá tiền
        do {
            System.out.print("Input price: ");
            try {
                price = Double.parseDouble(scanner.nextLine());
                if (price <= 0) {
                    System.out.println("The price must be greater than 0. Try again!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! The price must be a number. Try again!");
                price = -1; //Đặt giá trị âm để tiếp tục vòng lặp
            }
        } while (price <= 0);

        //Thêm thương hiệu vào danh sách
        this.add(new Brand(brandID, brandName, soundBrand, price));
        System.out.println("Brand has added successfully");
    }

    //Update brand_name, sound_brand, price of an existed brand
    public void updateBrand() {
        do {
            System.out.print("Input brand ID: ");
            brandID = scanner.nextLine();
            pos = searchID(brandID);
            if (pos != -1) {
                break;
            }
            System.out.println("Not found !");
        } while (true);

        do {
            System.out.print("Input brand name: ");
            brandName = scanner.nextLine();
            if (!brandName.isEmpty()) {
                break;
            }
            System.out.println("The brand name cannot be blank. Try again!");
        } while (true);

        do {
            System.out.print("Input sound brand: ");
            soundBrand = scanner.nextLine();
            if (!soundBrand.isEmpty()) {
                break;
            }
            System.out.println("The sound brand cannot be blank. Try again!");
        } while (true);

        do {
            System.out.print("Input price: ");
            try {
                price = Double.parseDouble(scanner.nextLine());
                if (price <= 0) {
                    System.out.println("The price must be positive. Try again!");
                    price = 0;
                }
            } catch (NumberFormatException e) {
                System.out.println("The price must be a number. Try again!");
                price = 0;
            }
        } while (price == 0);

        this.get(pos).setUpdatedBrand(brandName, soundBrand, price);
        System.out.println("Brand has updated successfully !");
    }

    //Show the list of the brands
    public void listBrands() {
        if (this.isEmpty()) {
            System.out.println("No brands available.");
            return;
        }

        // Tính toán độ rộng cột dựa trên tiêu đề và dữ liệu của tất cả các Brand
        int idWidth = "ID".length();
        int nameWidth = "Brand Name".length();
        int soundWidth = "Sound Brand".length();
        int priceWidth = "Price".length();

        for (Brand b : this) {
            idWidth = Math.max(idWidth, b.getBrandID().length());
            nameWidth = Math.max(nameWidth, b.getBrandName().length());
            soundWidth = Math.max(soundWidth, b.getSoundBrand().length());
            priceWidth = Math.max(priceWidth, String.valueOf(b.getPrice()).length());
        }
        priceWidth = Math.max(priceWidth, 10); // đảm bảo cột Price có độ rộng tối thiểu

        // Định dạng bảng
        String format = "| %-" + idWidth + "s | %-" + nameWidth + "s | %-" + soundWidth + "s | %-" + priceWidth + "s |\n";
        int tableWidth = idWidth + nameWidth + soundWidth + priceWidth + 13;

        // Tạo dòng kẻ
        String separator = new String(new char[tableWidth]).replace('\0', '-');

        // In tiêu đề bảng
        System.out.println(separator);
        System.out.printf(format, "ID", "Brand Name", "Sound Brand", "Price");
        System.out.println(separator);

        // In dữ liệu
        for (Brand b : this) {
            System.out.printf(format, b.getBrandID(), b.getBrandName(), b.getSoundBrand(), b.getPrice());
        }

        // Kết thúc bảng
        System.out.println(separator);
    }

    //Remove brand based on brandID
    public boolean removeBrand(String brandID) {
        int index = searchID(brandID);
        if (index == -1) {
            System.out.println("Brand ID not found.");
            return false;
        }

        // Hiển thị thông tin thương hiệu để xác nhận trước khi xoá
        System.out.println("Are you sure you want to remove this brand?");
        System.out.println(this.get(index)); // Hiển thị thông tin thương hiệu
        System.out.print("Type 'YES' to confirm: ");

        String confirmation = scanner.nextLine().trim();

        if (confirmation.equalsIgnoreCase("YES")) {
            this.remove(index);
            System.out.println("Brand removed successfully.");
            return true;
        } else {
            System.out.println("Deletion cancelled.");
            return false;
        }
    }

    //Print brandlist by descending of price
    public void sortBrandsByPriceDescending() {
        List<Brand> sortedList = new ArrayList<>(this);

        // Sắp xếp danh sách bản sao
        sortedList.sort((b1, b2) -> Double.compare(b2.getPrice(), b1.getPrice()));

        BrandList tempList = new BrandList();
        tempList.addAll(sortedList);

        // Hiển thị danh sách đã sắp xếp bằng cách tạm thay thế danh sách gốc
        System.out.println("Brands sorted by price (descending):");
        tempList.listBrands();

    }

}
