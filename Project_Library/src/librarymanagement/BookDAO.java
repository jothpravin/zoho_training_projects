package librarymanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookDAO 
{
    private static final String book_insert_query = "INSERT INTO book (bookname, author, publisherid, available, entry_by, price) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String book_copies_insert_query = "INSERT INTO bookcopy (bookid, rackid) VALUES (?, ?)";
    private static final String publisher_select_query = "SELECT * FROM publishers";
    private static final String rack_select_query = "SELECT * FROM rack";
    private static final String book_select_query = "SELECT DISTINCT ON (b.bookid) "+
                                                    "bc.bc_id, "+
                                                    "b.bookname, "+
                                                    "b.author, "+
                                                    "p.publishername,"+
                                                    "b.available "+
                                                    "FROM bookcopy AS bc "+
                                                    "JOIN book AS b ON bc.bookid = b.bookid "+
                                                    "JOIN publishers AS p ON b.publisherid = p.publisherid "+
                                                    "WHERE bc.rackid = ? AND bc.isavailable = 'true' ";
    private static final String book_reserve_insert_query = "INSERT INTO bookreservation (userid, bc_id) VALUES (?, ?)";
    private static final String update_book_available_query = "UPDATE bookcopy SET isavailable = 'false' WHERE bc_id = ?";
    private static final String update_book_available_count_query = "UPDATE book SET available = available-1 WHERE bookid = ?";
    private static final String bookid_select_query = "SELECT DISTINCT ON (bookid) bookid FROM bookcopy WHERE bc_id = ?";
    private static final String reservation_select_query = "SELECT * FROM bookreservation WHERE userid = ?";
    private static final String book_issue_inser_query = "INSERT INTO bookissue (userid, bc_id) VALUES (?, ?)";
    private static final String book_return_query = "UPDATE bookissue SET returndate = ? WHERE userid = ? AND bc_id = ?";
    private static final String update_return_book_available_query = "UPDATE bookcopy SET isavailable = 'true' WHERE bc_id = ?";
    private static final String update_return_book_count_query = "UPDATE book SET available = available+1 WHERE bookid = ?";
    private static final String update_fine_amount_query = "UPDATE bookissue SET fine = ? WHERE bc_id = ? AND userid = ?";
    private static final String select_issue_date_query = "SELECT dateofissue FROM bookissue WHERE bc_id = ? AND userid = ?";
    
    public void newBookEntry(Book book, int rackID)
    {
        Connection con = ConnectionDB.getConnection();
        try(PreparedStatement ps = con.prepareStatement(book_insert_query, Statement.RETURN_GENERATED_KEYS))
        {
            ps.setString(1, book.getBookName());
            ps.setString(2, book.getAuthorName());
            ps.setInt(3, book.getPublisherID());
            ps.setInt(4, book.getAvailable());
            ps.setString(5, book.getEntryBy());
            ps.setInt(6, book.getPrice());

            int row = ps.executeUpdate();
            if(row > 0)
            {
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next())
                {
                    book.setBookID(rs.getInt(1));
                    if(book.getBookID()>0)
                    {
                        for(int i=0; i<book.getAvailable(); i++)
                        {
                            bookCopiesEntry(book.getBookID(), rackID);
                        }
                    }
                    System.out.println("SuccessFully inserted a new book");
                }
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            ConnectionDB.closeConnection();
        }
    }

    private void bookCopiesEntry(int bookId, int rackID)
    {
        Connection con = ConnectionDB.getConnection();
        try(PreparedStatement ps = con.prepareStatement(book_copies_insert_query))
        {
            ps.setInt(1, bookId);
            ps.setInt(2, rackID);
            int row = ps.executeUpdate();
            if(row > 0)
            {
                System.out.println("SuccessFully Inserted into Copies table");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            ConnectionDB.closeConnection();
        }
    }

    public List<Publisher> getPublishers()
    {
        List<Publisher> publishers = new ArrayList<>();
        Connection con = ConnectionDB.getConnection();
        try(PreparedStatement ps = con.prepareStatement(publisher_select_query);
            ResultSet rs = ps.executeQuery())
        {
            while(rs.next())
            {
                Publisher publisher = new Publisher(rs.getString("publishername"));
                publisher.setPublisherID(rs.getInt("publisherid"));
                publishers.add(publisher);
            }
            return publishers;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            ConnectionDB.closeConnection();
        }
        return null;
    }

    public List<Rack> getRacks()
    {
        List<Rack> racks = new ArrayList<>();

        Connection con = ConnectionDB.getConnection();
        try(PreparedStatement ps = con.prepareStatement(rack_select_query);
            ResultSet rs = ps.executeQuery())
            {
                while(rs.next())
                {
                    Rack rack = new Rack(rs.getString("rackname"));
                    rack.setRackID(rs.getInt("rackid"));
                    racks.add(rack);
                }
                return racks;
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
            finally
            {
                ConnectionDB.closeConnection();
            }
        return null;
    }

    public List<String> getBooks(int rackid)
    {
        List<String> books = new ArrayList<>();

        Connection con = ConnectionDB.getConnection();
        try(PreparedStatement ps = con.prepareStatement(book_select_query))
        {
            ps.setInt(1, rackid);
            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                String book = "Book ID :"+rs.getInt("bc_id")+"\n"+
                                "Book Name :"+rs.getString("bookname")+"\n"+
                                "Author Name :"+rs.getString("author")+"\n"+
                                "Publisher Name :"+rs.getString("publishername")+"\n"+
                                "Available Books :"+rs.getInt("available");
                books.add(book);
            }
            return books;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            ConnectionDB.closeConnection();
        }
        return null;
    }

    public void setBookReservation(int userid, int bc_id)
    {
        Connection con = ConnectionDB.getConnection();
        try(PreparedStatement ps = con.prepareStatement(book_reserve_insert_query))
        {
            int bookId = getBookId(bc_id);
            ps.setInt(1, userid);
            ps.setInt(2, bc_id);
            int row = ps.executeUpdate();
            if(row > 0)
            {
                updateBookAvailable(bc_id);
                updateBookAvailableCount(bookId);
                System.out.println("Successfully reserved");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            ConnectionDB.closeConnection();
        }
    }

    private void updateBookAvailable(int bookid)
    {
        Connection con = ConnectionDB.getConnection();
        try(PreparedStatement ps = con.prepareStatement(update_book_available_query))
        {
            ps.setInt(1, bookid);
            int row = ps.executeUpdate();
            if(row > 0)
            {
                System.out.println("Successfully available updated in bookcopy");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void updateBookAvailableCount(int bookid)
    {
        Connection con = ConnectionDB.getConnection();
        try(PreparedStatement ps = con.prepareStatement(update_book_available_count_query))
        {
            ps.setInt(1, bookid);
            int row = ps.executeUpdate();
            if(row > 0)
            {
                System.out.println("Successfully updated in book");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    private int getBookId(int bookCopyid)
    {
        Connection con = ConnectionDB.getConnection();
        try(PreparedStatement ps = con.prepareStatement(bookid_select_query))
        {
            ps.setInt(1, bookCopyid);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                return rs.getInt("bookid");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    public List<String> getReservations(int userid)
    {
        List<String> reservationList = new ArrayList<>(); 
        Connection con = ConnectionDB.getConnection();
        try(PreparedStatement ps = con.prepareStatement(reservation_select_query))
        {
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                String reservation = "Book Id: "+rs.getInt("bc_id")+"\n"+
                                     "User Id: "+rs.getInt("userid");
                reservationList.add(reservation);
            }
            return reservationList;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            ConnectionDB.closeConnection();
        }
        return null;
    }

    public void bookReserverIssueEntry(int userid, int bookCopyId)
    {
        Connection con = ConnectionDB.getConnection();
        try(PreparedStatement ps = con.prepareStatement(book_issue_inser_query))
        {
            ps.setInt(1, userid);
            ps.setInt(2, bookCopyId);
            int row = ps.executeUpdate();
            if(row > 0)
            {
                System.out.println("SuccessFully Inserted into book issue");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            ConnectionDB.closeConnection();
        }
    }

    public void bookDirectIssueEntry(int userid, int bookCopyId)
    {
        Connection con = ConnectionDB.getConnection();
        try(PreparedStatement ps = con.prepareStatement(book_issue_inser_query))
        {
            int bookid = getBookId(bookCopyId);
            ps.setInt(1, userid);
            ps.setInt(2, bookCopyId);
            int row = ps.executeUpdate();
            if(row > 0)
            {
                updateBookAvailable(bookCopyId);
                updateBookAvailableCount(bookid);
                System.out.println("SuccessFully Inserted into book issue");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            ConnectionDB.closeConnection();
        }
    }

    public void returnEntry(int userid, int bookid, Date returnDate)
    {
        Connection con = ConnectionDB.getConnection();
        try(PreparedStatement ps = con.prepareStatement(book_return_query))
        {
            int bid = getBookId(bookid);
            java.sql.Date returndDate = new java.sql.Date(returnDate.getTime());
            ps.setDate(1, returndDate);
            ps.setInt(2, userid);
            ps.setInt(3, bookid);
            int row = ps.executeUpdate();
            if(row > 0)
            {
                Date issueDate = getIssuseDate(bookid, userid);
                int fine = getFineAmount(issueDate);
                bookFineUpdate(bookid, userid, fine);
                updateReturnBookAvailable(bookid);
                updateReturnBookAvailableCount(bid);
                System.out.println("Successfully book returned");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            ConnectionDB.closeConnection();
        }
    }

    private void updateReturnBookAvailable(int bookid)
    {
        Connection con = ConnectionDB.getConnection();
        try(PreparedStatement ps = con.prepareStatement(update_return_book_available_query))
        {
            ps.setInt(1, bookid);
            int row = ps.executeUpdate();
            if(row > 0)
            {
                System.out.println("Successfully available updated in bookcopy");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void updateReturnBookAvailableCount(int bookid)
    {
        Connection con = ConnectionDB.getConnection();
        try(PreparedStatement ps = con.prepareStatement(update_return_book_count_query))
        {
            ps.setInt(1, bookid);
            int row = ps.executeUpdate();
            if(row > 0)
            {
                System.out.println("Successfully updated in book");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void bookFineUpdate(int bookId, int userid, int fineAmount)
    {
        Connection con = ConnectionDB.getConnection();
        try(PreparedStatement ps = con.prepareStatement(update_fine_amount_query))
        {
            ps.setInt(1, fineAmount);
            ps.setInt(2, bookId);
            ps.setInt(3, userid);
            int row = ps.executeUpdate();
            if(row > 0)
            {
                System.out.println("SuccessFully Updated fine into book issue");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            ConnectionDB.closeConnection();
        }
    }

    private Date getIssuseDate(int bookid, int userid)
    {
        Connection con = ConnectionDB.getConnection();
        try(PreparedStatement ps = con.prepareStatement(select_issue_date_query))
        {
            ps.setInt(1, bookid);
            ps.setInt(2, userid);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                return rs.getDate("dateofissue");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private int getFineAmount(Date issueDate)
    {
        java.sql.Date issuseDate = new java.sql.Date(issueDate.getTime());
        LocalDate localDate = issuseDate.toLocalDate();
        LocalDate curDate = LocalDate.now();
        long daysDifference = java.time.temporal.ChronoUnit.DAYS.between(localDate, curDate);
        if(daysDifference > 5)
        {
            int days = (int)daysDifference-5;
            return days*20;
        }
        return 0;
    }



}