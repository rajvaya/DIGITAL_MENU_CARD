package com.bugscreator.menu;

/**
 * Created by admin on 07-03-2018.
 */

public class orderlistitem {


    private String name;
    private int price,qty,tprice;

    public orderlistitem(String name, int price, int qty, int tprice) {
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.tprice = tprice;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQty() {
        return qty;
    }

    public int getTprice() {
        return tprice;
    }

}
