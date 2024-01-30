package librarymanagement;

public class BookCopy 
{
    private int bookCopyID;
    private int bookID;
    private int bookTokenNo;
    private int rackID;

    public BookCopy(int bookID, int bookTokenNo, int rackID) 
    {
        this.bookCopyID = 0;
        this.bookID = bookID;
        this.bookTokenNo = bookTokenNo;
        this.rackID = rackID;
    }

    public void setBookCopyID(int bookCopyID) {
        this.bookCopyID = bookCopyID;
    }

    public int getBookCopyID() 
    {
        return bookCopyID;
    }

    public int getBookID() 
    {
        return bookID;
    }

    public int getBookTokenNo() 
    {
        return bookTokenNo;
    }

    public int getRackID() 
    {
        return rackID;
    }
    
    
}
