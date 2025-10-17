package com.atm.domain;

public class User {
    private String id;
    private String name;
    private String pin;

    public User(String id, String name, String pin) {
        this.id = id;
        this.name = name;
        this.pin = pin;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean verifyPin(String pin) {
        return this.pin.equals(pin);
    }
}