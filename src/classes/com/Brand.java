/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes.com;

/**
 *
 * @author AnhTN
 * @date 04-03-2025
 * @description
 */
public class Brand {

    private String brandID, brandName, soundBrand;
    private double price;

    Brand() {

    }

    Brand(String brandID, String brandName, String soundBrand, double price) {
        this.brandID = brandID;
        this.brandName = brandName;
        this.soundBrand = soundBrand;
        this.price = price;
    }

    public String getBrandID() {
        return brandID;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getSoundBrand() {
        return soundBrand;
    }

    public double getPrice() {
        return price;
    }

    public void setUpdatedBrand(String brandName, String soundBrand, double price) {
        this.brandName = brandName;
        this.soundBrand = soundBrand;
        this.price = price;
    }

    @Override
    public String toString() {
        return brandID + "," + brandName + "," + soundBrand + ":" + price;
    }
}
