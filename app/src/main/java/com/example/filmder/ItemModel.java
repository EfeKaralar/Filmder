package com.example.filmder;

public class ItemModel {
    private int image;
    private String name, age, desc;

    public ItemModel() {
    }
    public ItemModel(int image, String name, String age, String city) {
        this.image = image;
        this.name = name;
        this.age = age;
        this.desc = desc;
    }
    public int getImage(){
        return image;
    }
    public String getName(){
        return name;
    }
    public String getAge(){
        return age;
    }
    public String getDesc(){
        return desc;
    }
}
