package librarymanagement;

import java.util.List;
import java.util.Scanner;

public class BookController implements BookProcess
{
    static Scanner in = new Scanner(System.in);

    BookDAO bookDAO;
    UserController userController;
    public BookController(BookDAO bookDAO, UserController userController)
    {
        this.bookDAO = bookDAO;
        this.userController = userController;
    }

    public void addNewBook()
    {
        System.out.print("Enter the book Name :");
        String bookName = in.nextLine();
        System.out.print("Enter the Author Name :");
        String authorName = in.nextLine();
        in.nextLine();
        getAllPublishers();
        System.out.print("Enter the publisherId :");
        int pubId = in.nextInt();
        System.out.print("Enter the book copies count :");
        int copies = in.nextInt();
        System.out.print("Enter the price of book :");
        int price = in.nextInt();
        getAllRacks();
        System.out.print("Enter the rackId :");
        int rackId = in.nextInt();
        Book book = new Book(bookName, authorName, pubId, price, copies, userController.getUserName());
        bookDAO.newBookEntry(book, rackId);
    }

    private void getAllPublishers()
    {
        List<Publisher> publishers = bookDAO.getPublishers();
        System.out.println("------------------------------------------------");
        for(Publisher publisher: publishers)
        {
            System.out.println(publisher);
        }
        System.out.println("------------------------------------------------");
    }

    private void getAllRacks()
    {
        List<Rack> racks = bookDAO.getRacks();

        System.out.println("------------------------------------------------");
        for(Rack rack: racks)
        {
            System.out.println(rack);
        }
        System.out.println("------------------------------------------------");
    }

    public void getBooksByRack()
    {
        getAllRacks();
        System.out.print("Enter the rackId :");
        int rackId = in.nextInt();

        List<String> books = bookDAO.getBooks(rackId);

        if(books != null)
        {
            System.out.println("---------------------------------------");
            for(String book: books)
            {
                System.out.println(book);
                System.out.println("---------------------------------------");
            }
        }
        else
        {
            System.out.println("Rack "+rackId+" is empty :");
        }
    }

    public void sendBookReserveRequest()
    {
        getBooksByRack();
        System.out.print("Enter the Book id to reserve(0 to back): ");
        int bookid = in.nextInt();
        if(bookid==0)
        {
            return;
        }
        bookDAO.setBookReservation(UserDAO.USERID, bookid);
    }

    public void bookDirectIssue()
    {
        System.out.print("Enter the Book id: ");
        int bookid = in.nextInt();
        System.out.print("Enter the user id: ");
        int userid = in.nextInt();
        bookDAO.bookDirectIssueEntry(userid, bookid);
        
    }

    public void bookReservationIssue()
    {
        System.out.print("Enter the user id: ");
        int userid = in.nextInt();
        showAllReservations(userid);
        System.out.print("Enter the Book id: ");
        int bookid = in.nextInt();
        bookDAO.bookReserverIssueEntry(userid, bookid);
    }

    private void showAllReservations(int userid)
    {
        List<String> reservations = bookDAO.getReservations(userid);
        
        System.out.println("---------------------------------------");
        for(String reservation: reservations)
        {
            System.out.println(reservation);
            System.out.println("---------------------------------------");
        }
    }

    public void returnBookEntry()
    {
        System.out.print("Enter the user id: ");
        int userid = in.nextInt();
        System.out.print("Enter the Book id: ");
        int bookid = in.nextInt();
        bookDAO.returnEntry(userid, bookid, new java.util.Date());
    }
}