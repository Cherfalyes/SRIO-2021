package com.example.srio_td2_partie1_webview;

public class Contact {
    private String name;
    private String numero;
    private String mail;

    public Contact(String name, String numero){
        this.name = name;
        this.numero = numero;
        this.mail = null;
    }
    public Contact(String name, String numero, String mail){
        this.name = name;
        this.numero = numero;
        this.mail = mail;
    }
    public String getName(){
        return this.name;
    }

    public String getNumero() {
        return this.numero;
    }
    public void setNumero(String numero){
        this.numero=numero;
    }

    public String toString(){
        if(this.numero == null) return  this.name +"; \n";
        else if(this.mail == null )return this.name + " : " + this.numero +"; \n";
        else return this.name + " : " + this.numero +", " + this.mail +"\n";
    }

}
