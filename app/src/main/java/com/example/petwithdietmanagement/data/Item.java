package com.example.petwithdietmanagement.data;

import java.util.HashMap;
import java.util.Map;

public class Item {
    private int itemId;
    private String itemType;
    private String itemName;
    private int itemPrice;
    private String itemImage;
    private String description;
    private int purchased;

    // Getters and Setters
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int isPurchased() { return purchased; }

    public void setPurchased(int purchased) { this.purchased = purchased; }
}
