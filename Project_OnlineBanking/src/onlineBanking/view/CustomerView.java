package onlineBanking.view;

import java.io.Console;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import onlineBanking.model.Account;
import onlineBanking.model.Customer;
import onlineBanking.model.Gender;
import onlineBanking.utils.Validator;

public class CustomerView 
{
    static Scanner in = new Scanner(System.in);
    static Console con = System.console();

    public Customer newCustomer()
    {
        System.out.print("Enter the First Name :");
        String fName = in.next();
        System.out.print("Enter the Last Name :");
        String lName = in.next();
        System.out.println("\n1.Male\n2.Female\nEnter Gender(1 or 2): ");
        int n = in.nextInt();
        while(n > 2)
        {
            System.out.println("\n1.Male\n2.Female\nEnter Gender(1 or 2): ");
            n = in.nextInt();
        }
        Gender g = Gender.Male;
        if(n==1)
        {
            g = Gender.Male;
        }
        else if(n==2)
        {
            g = Gender.Female;
        }
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
        System.out.print("Enter the Mobile number: ");
        Long mobile = in.nextLong();
        Date date = null;
        while(true)
        {
            System.out.print("Enter the User date of birth(dd/mm/yyyy): ");
            String dob = in.next();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            try 
            {
                date = formatter.parse(dob);
                break;
            } 
            catch(ParseException e) 
            {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        System.out.print("Enter the pan number: ");
        String pan_no = in.next();
        while(!Validator.isValidPan(pan_no))
        {
            System.out.print("Enter the Pan number: ");
            pan_no = in.next();
        }
        System.out.print("Enter the Street Address :");
        String streetName = in.nextLine();
        System.out.print("Enter the City Name :");
        String city = in.nextLine();
        System.out.print("Enter the Street Address :");
        String state = in.nextLine();
        System.out.print("Enter the Pincode :");
        int pincode = in.nextInt();
        Customer customer = new Customer(fName, lName, g, date, email, pass, mobile, streetName, city, state, pincode);
        return customer;
    }

    public String[] getCredentials()
    {
        System.out.print("Enter the Email ID: ");
		String email = in.next();
		System.out.print("Enter the Password: ");
        char[] pas = con.readPassword();
		String pass = new String(pas);
		if(Validator.emailValidator(email) && Validator.isValidPassword(pass))
		{
			return new String[]{email, pass};
		}
		return null;
    }

    public Account newAccount()
    {
        return null;
    }
}
