package com.bugscreator.menu;

/**
 * Created by admin on 09-02-2018.
 */

public class foodlistitem {

    private  String name;//name
    private  byte[] img; //Image
    private  int price;//price
    private  String des;
    public String tname ;
    public foodlistitem(byte[] img, String name, int price,String des) {
        this.img = img;
        this.name = name;
        this.price = price;
        this.des = des;
    }

    public String getDes() {
        return des;
    }

    public byte[] getImg() { return img;}

        public  String getName() {
            return name;
        }

    public  int getPrice() {
        return price;
    }
}

