package ecommerence.models;

import java.util.Date;

public class Order 
{
    private int orderId;
    private int customerId;
    private int productId;
    private int merchantId;
    private int addressid;
    private Date orderDate;
    private Date deliveryDate;
    private int quantity;
    private double buy_price;
    private double amount;
    private DeliveryStatus deliveryStatus;

    public Order(int customerId, int productId, int merchantId, int addressid, int quantity, double buy_price, double amount) 
    {
        this.customerId = customerId;
        this.productId = productId;
        this.merchantId = merchantId;
        this.quantity = quantity;
        this.buy_price = buy_price;
        this.amount = amount;
        this.addressid = addressid;
    }

    public void setOrderId(int orderId) 
    {
        this.orderId = orderId;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) 
    {
        this.deliveryStatus = deliveryStatus;
    }

    public int getOrderId() 
    {
        return orderId;
    }
    public int getCustomerId() 
    {
        return customerId;
    }
    public int getProductId() 
    {
        return productId;
    }
    public int getMerchantId() 
    {
        return merchantId;
    }
    public int getAddressId()
    {
        return addressid;
    }
    public Date getOrderDate() 
    {
        return orderDate;
    }
    public Date getDeliveryDate() 
    {
        return deliveryDate;
    }
    public int getQuantity() 
    {
        return quantity;
    }
    public double getBuy_price() 
    {
        return buy_price;
    }
    public double getAmount() 
    {
        return amount;
    }
    public DeliveryStatus getDeliveryStatus()
    {
        return deliveryStatus;
    }
}

