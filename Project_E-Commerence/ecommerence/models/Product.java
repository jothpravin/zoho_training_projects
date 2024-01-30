package ecommerence.models;

import java.util.Date;

public class Product
{
    private int productId;
    private String productName;
    private String productDesc;
    private int productPrice;
    private int categoryId;
    private int merchantId;
    private int stockQuantity;
    private int ratings;
    private Date listDate;
    private ProductStatus status;

    public Product(String productName, String productDesc, int productPrice, int categoryId, int merchantId,int stockQuantity) 
    {
        this.productId = 0;
        this.productName = productName;
        this.productDesc = productDesc;
        this.productPrice = productPrice;
        this.categoryId = categoryId;
        this.merchantId = merchantId;
        this.stockQuantity = stockQuantity;
        this.ratings = 0;
    }

    public void setProductId(int productId) 
    {
        this.productId = productId;
    }

    public void setProductPrice(int productPrice)
    {
        this.productPrice = productPrice;
    }

    public void setStockQuantity(int stockQuantity) 
    {
        this.stockQuantity = stockQuantity;
    }

    public void setStatus(ProductStatus status)
    {
        this.status = status;
    }

    public void setRatings(int ratings) 
    {
        this.ratings = ratings;
    }

    public int getProductId() 
    {
        return productId;
    }

    public ProductStatus getStatus()
    {
        return status;
    }

    public String getProductName() 
    {
        return productName;
    }

    public String getProductDesc() 
    {
        return productDesc;
    }

    public int getProductPrice() 
    {
        return productPrice;
    }

    public int getCategoryId()
    {
        return categoryId;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public int getStockQuantity() 
    {
        return stockQuantity;
    }

    public int getRatings() 
    {
        return ratings;
    }

    public Date getListDate() 
    {
        return listDate;
    }
}

enum ProductStatus
{
    Active,
    Inactive
}