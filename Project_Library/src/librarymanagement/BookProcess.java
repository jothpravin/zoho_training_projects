package librarymanagement;

public interface BookProcess 
{
    void addNewBook();
    void bookDirectIssue();
    void bookReservationIssue();
    void returnBookEntry();
    void getBooksByRack();

}
