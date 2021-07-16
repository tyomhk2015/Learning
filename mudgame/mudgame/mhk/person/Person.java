package com.mudgame.mhk.person;

public class Person {

    private String name; // The name of the player

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        // Store the player's name.
        this.name = name;
    }
}
