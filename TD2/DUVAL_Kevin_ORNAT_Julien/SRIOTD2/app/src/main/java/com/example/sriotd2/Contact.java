package com.example.sriotd2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Contact{

    public String name;
    public String num;

    public Contact(String name, String num){
        this.name = name;
        this.num = num;
    }

    public String getName(){
        return this.name;
    }

    public String getNum(){
        return this.num;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setNum(String num){
        this.num = num;
    }
}