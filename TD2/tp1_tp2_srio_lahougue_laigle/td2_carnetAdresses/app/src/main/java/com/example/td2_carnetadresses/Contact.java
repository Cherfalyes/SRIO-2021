package com.example.td2_carnetadresses;

public class Contact {
    String name;
    String phoneNumber;

    public Contact(String name, String phoneNumber){
        this.name=name;
        this.phoneNumber=phoneNumber;
    }

    public String toString(){
        return this.name+" "+this.phoneNumber;
    }
}
