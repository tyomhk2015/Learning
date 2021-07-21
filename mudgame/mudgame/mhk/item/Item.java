package com.mudgame.mhk.item;

public class Item {

    private String name; // The name of the item.
    private String type; // The type of the item.
    private String description; // The explanation of the item.
    private int price; // The price of the item.

    public Item() {
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }
}
