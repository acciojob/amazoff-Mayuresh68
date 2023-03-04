package com.driver;
public class Order {

    private String id; //orderId
    private int deliveryTime;

    public Order(String id, String deliveryTime) { //time = "HH:MM";

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM

        String time[]=deliveryTime.split(":"); //String time[]={"HH","MM"};
          this.deliveryTime = Integer.parseInt(time[0])*60 + Integer.parseInt(time[1]);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}
