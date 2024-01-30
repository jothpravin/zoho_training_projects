package librarymanagement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.io.Console;

public class UserController 
{
    static Scanner in = new Scanner(System.in);
    static Console con = System.console();
    private static final int key = 372001;
    private UserDAO userDAO;
    public UserController(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    public void signUpForUsers()
    {
        try
		{
			System.out.print("Enter the Email ID: ");
			String email = in.next();
			while(!Validator.emailValidator(email))
			{
				System.out.print("Enter the Valid Email ID: ");
				email = in.next();
			}
			System.out.print("Enter the Password: ");
			char[] pas = con.readPassword();
            String pass = new String(pas);
			while(!Validator.isValidPassword(pass))
			{
				System.out.print("Enter the Valid Password: ");
				pass = in.next();
			}
			System.out.print("Enter the User date of birth(dd/mm/yyyy): ");
			String dob = in.next();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date date = formatter.parse(dob);
			System.out.println("\n1.Male\n2.Female\nEnter Gender: ");
			int n = in.nextInt();
			Gender g = null;
			if(n==1)
			{
				g = Gender.Male;
			}
			else if(n==2)
			{
				g = Gender.Female;
			}
			in.nextLine();
			System.out.print("Enter the userName :");
			String userName = in.nextLine();
            User newUser = new User(userName, email, pass, false, g, date);
            userDAO.createNewUser(newUser);
			System.out.println("New User added with ID: " + newUser.getUserID());
            System.out.println("User was Successfully created");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    public void signUpForAdmin()
    {
        try
		{
			System.out.print("Enter the Key to Register Admin: ");
			int keyp = in.nextInt();
            if(keyp == key)
            {
                System.out.print("Enter the Email ID: ");
                String email = in.next();
                while(!Validator.emailValidator(email))
                {
                    System.out.print("Enter the Valid Email ID: ");
                    email = in.next();
                }
                System.out.print("Enter the Password: ");
                char[] pas = con.readPassword();
                String pass = new String(pas);
                while(!Validator.isValidPassword(pass))
                {
                    System.out.print("Enter the Valid Password: ");
                    pass = in.next();
                }
                System.out.print("Enter the User date of birth(dd/mm/yyyy): ");
                String dob = in.next();
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date date = formatter.parse(dob);
                System.out.println("\n1.Male\n2.Female\nEnter Gender: ");
                int n = in.nextInt();
                Gender g = null;
                if(n==1)
                {
                    g = Gender.Male;
                }
                else if(n==2)
                {
                    g = Gender.Female;
                }
                in.nextLine();
                System.out.print("Enter the userName :");
                String userName = in.nextLine();
                User newAdmin = new User(userName, email, pass, true, g, date);
                userDAO.createNewUser(newAdmin);
                System.out.println("New Admin added with ID: " + newAdmin.getUserID());
                System.out.println("Admin was Successfully created");
            }
            else
            {
                System.out.println("Key is wrong !!!");
            }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    public int loginUser()
    {
        System.out.print("Enter the Email ID: ");
		String email = in.next();
		System.out.print("Enter the Password: ");
        char[] pas = con.readPassword();
		String pass = new String(pas);
		if(Validator.emailValidator(email) && Validator.isValidPassword(pass))
		{
			return userDAO.loginUser(email, pass);
		}
		return -1;
    }

    public int loginAdmin()
    {
        System.out.print("Enter the Email ID: ");
		String email = in.next();
		System.out.print("Enter the Password: ");
        char[] pas = con.readPassword();
		String pass = new String(pas);
		if(Validator.emailValidator(email) && Validator.isValidPassword(pass))
		{
			return userDAO.loginAdmin(email, pass);
		}
		return -1;
    }

    public boolean logout()
    {
        System.out.println("Logging out !!");
        return true;
    }

    public String getUserName()
    {
        return userDAO.getUserNameDB();
    }
}
