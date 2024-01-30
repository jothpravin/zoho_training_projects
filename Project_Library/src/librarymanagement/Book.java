package librarymanagement;

import java.sql.Date;

public class Book 
{
    private int bookID;
    private String bookName;
    private String authorName;
    private int publisherID;
    private int available;
    private int price;
    private String entryBy;
    private Date entryOn;

    public Book(String bookName, String authorName, int publisherID, int price, int available, String entryBy) 
    {
        this.bookID = 0;
        this.bookName = bookName;
        this.authorName = authorName;
        this.publisherID = publisherID;
        this.price = price;
        this.available = available;
        this.entryBy = entryBy;
    }

    public void setBookID(int bookID) 
    {
        this.bookID = bookID;
    }
    
    public void setEntryBy(String entryBy) 
    {
        this.entryBy = entryBy;
    }

    public void setEntryOn(Date entryOn) 
    {
        this.entryOn = entryOn;
    }

    public int getAvailable() 
    {
        return available;
    }

    public int getPrice() 
    {
        return price;
    }

    public String getEntryBy() 
    {
        return entryBy;
    }

    public Date getEntryOn() 
    {
        return entryOn;
    }

    public int getBookID() 
    {
        return bookID;
    }

    public String getBookName() 
    {
        return bookName;
    }

    public String getAuthorName() 
    {
        return authorName;
    }

    public int getPublisherID() 
    {
        return publisherID;
    }
    
    
    
}
