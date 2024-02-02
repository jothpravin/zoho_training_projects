package librarymanagement;

public class Publisher 
{
    private int publisherID;
    private String publisherName;

    public Publisher(String publisherName) 
    {
        this.publisherID = 0;
        this.publisherName = publisherName;
    }

    public void setPublisherID(int publisherID) 
    {
        this.publisherID = publisherID;
    }

    public int getPublisherID() 
    {
        return publisherID;
    }

    public String getPublisherName() 
    {
        return publisherName;
    }

    public String toString()
    {
        return "Publisher ID:"+publisherID+"  Publisher Name :"+publisherName;
    }
    
}
