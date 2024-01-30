package librarymanagement;

import java.util.Date;

public class BookIssue 
{
    private int bookIssueID;
    private int userID;
    private int bookCopyID;
    private Date dateOfIssue;
    private Date dateOfReturn;
    private int fine;
    
    public BookIssue(int userID, int bookCopyID, Date dateOfIssue, Date dateOfReturn, int fine) 
    {
        this.bookIssueID = 0;
        this.userID = userID;
        this.bookCopyID = bookCopyID;
        this.dateOfIssue = dateOfIssue;
        this.dateOfReturn = dateOfReturn;
        this.fine = fine;
    }

    public void setBookIssueID(int bookIssueID) 
    {
        this.bookIssueID = bookIssueID;
    }

    public int getBookIssueID() 
    {
        return bookIssueID;
    }
    public int getUserID() 
    {
        return userID;
    }
    public int getBookCopyID() 
    {
        return bookCopyID;
    }
    public Date getDateOfIssue() 
    {
        return dateOfIssue;
    }
    public Date getDateOfReturn() 
    {
        return dateOfReturn;
    }
    public int getFine() 
    {
        return fine;
    }   
}
