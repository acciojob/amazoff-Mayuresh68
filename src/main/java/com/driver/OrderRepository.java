package com.driver;

import org.springframework.stereotype.Repository;

import javax.swing.border.Border;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {
    HashMap<String,Order> orderDb = new HashMap<>(); // (orderId,Order-obj)
    HashMap<String,DeliveryPartner> deliveryPartnerDb = new HashMap<>(); //(D_PartnerId,D_Partner-obj

    HashMap<String,String> orderPartnerDb = new HashMap<>(); //(orderId,partnerId)which order has to assigned which delivery partner

    HashMap<String, List<String>> partnerOrderDb = new HashMap<>();//(partnerId,listOfOrderId)

    public void addOrder(Order order) {
        orderDb.put(order.getId(), order);
    }

    public void addPartner(String partnerId) {
        deliveryPartnerDb.put(partnerId,new DeliveryPartner(partnerId));
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        //orderPartnerDb
        if(orderDb.containsKey(orderId) && deliveryPartnerDb.containsKey(partnerId)){
            orderPartnerDb.put(orderId,partnerId);
        }
        //partnerOrderDb
        List<String> currentOrders = new ArrayList<>();

        //first get the order_list nd update
        if(partnerOrderDb.containsKey(partnerId)){
            partnerOrderDb.get(partnerId).add(orderId);
        }
       partnerOrderDb.put(partnerId,currentOrders);

        //increase the no.of orders of current partner
        //first get that partner id nd update the count
        DeliveryPartner deliveryPartner = deliveryPartnerDb.get(partnerId);
        deliveryPartner.setNumberOfOrders(currentOrders.size());

    }

    public Order getOrderById(String orderId){
        return orderDb.get(orderId);
    }


    public DeliveryPartner getPartnerById(String partnerId) {
        return deliveryPartnerDb.get(partnerId);
    }

    public int getOrderCountByPartnerId(String partnerId) {
        return partnerOrderDb.get(partnerId).size();
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return partnerOrderDb.get(partnerId);
    }

    public List<String> getAllOrders() {
        List<String> list =  new ArrayList<>();
        for(String order : orderDb.keySet()){
            list.add(order);
        }
        return list;
    }

    public int getCountOfUnassignedOrders() {
        //no.of orders - no.assigned orders = unassigned orders
        return orderDb.size()-orderPartnerDb.size();
    }

    public int getOrderCountByPartnerId(int time,String partnerId){
        int count=0;
        List<String> orders = partnerOrderDb.get(partnerId);

        for(String orderId : orders){
            int deliveryTime = orderDb.get(orderId).getDeliveryTime();
            if(deliveryTime > time){
                count++;
            }
        }
        return count;

    }

    public int getLastDeliveryTimeByPartnerId(String partnerId){
        int maxTime=0;
        List<String> orders = partnerOrderDb.get(partnerId);

        for(String orderId : orders){
            int currentTime = orderDb.get(orderId).getDeliveryTime();
           maxTime=Math.max(maxTime,currentTime);
        }
        return maxTime;
    }

    public void deletePartnerById(String partnerId) {
        deliveryPartnerDb.remove(partnerId);

        List<String> listOfOrders = partnerOrderDb.get(partnerId);
        partnerOrderDb.remove(partnerId);
    }


    public void deleteOrderById(String orderId) {
        orderDb.remove(orderId);

        String partnerId = orderPartnerDb.get(orderId);
        orderPartnerDb.remove(orderId);

        partnerOrderDb.get(partnerId).remove(orderId);
        deliveryPartnerDb.get(partnerId).setNumberOfOrders(partnerOrderDb.get(partnerId).size());
    }
}
