package ecommerence.models;

public class Category 
{
    private int categoryId;
    private String categoryName;

    public Category(String categoryName) 
    {
        this.categoryName = categoryName;
    }

    public int getCategoryId() 
    {
        return categoryId;
    }
    public void setCategoryId(int categoryId) 
    {
        this.categoryId = categoryId;
    }
    public String getCategoryName() 
    {
        return categoryName;
    }
}