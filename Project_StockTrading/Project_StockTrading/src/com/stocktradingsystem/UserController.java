package com.stocktradingsystem;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class UserController 
{
    static Scanner in = new Scanner(System.in);
    static Console con = System.console();
    private UserDAO userDAO;
	private TradingDAO tradingDAO;
    public UserController(UserDAO userDAO, TradingDAO tradingDAO)
    {
        this.userDAO = userDAO;
		this.tradingDAO = tradingDAO;
    }

    public void SignUp()
    {
        try
		{
			System.out.print("Enter the First Name :");
			String fName = in.next();
			System.out.print("Enter the Last Name :");
			String lName = in.next();
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
            User newUser = new User(fName, lName, email, pass, mobile, g, date);
            userDAO.createNewUser(newUser);
            System.out.println("User was Successfully created");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    public int login()
    {
        System.out.print("Enter the Email ID: ");
		String email = in.next();
		System.out.print("Enter the Password: ");
        char[] pas = con.readPassword();
		String pass = new String(pas);
		if(Validator.emailValidator(email) && Validator.isValidPassword(pass))
		{
			int userid = userDAO.login(email, pass);
			tradingDAO.getTradingAccountId();
			return userid;
		}
		return -1;
    }

    public boolean logout() 
    {
		userDAO.logout();
        System.out.println("Logging out !!");
        return true;
    }

	public String getUserName(int userid)
	{
		return userDAO.getUser(userid).getFirstName();
	}

	public void profile()
    {
		User user = userDAO.getUser(UserDAO.USERID);
		TradingAccount tradingAccount = tradingDAO.getTradingAccount();
		System.out.println("------------------------------------");
        System.out.println("Name :"+user.getFirstName()+" "+user.getLastName());
		System.out.println("Email Id :"+user.getEmail());
		System.out.println("Mobile :"+user.getMobile_no());
		System.out.println("Demat Account Number :"+tradingAccount.getDemat_acc_no());
		System.out.println("Aadhar Number :"+tradingAccount.getAadhar_no());
		System.out.println("Pan Number :"+tradingAccount.getPan_no());
		System.out.println("Amount :"+tradingAccount.getAmount());
		System.out.println("------------------------------------");
    }
}
