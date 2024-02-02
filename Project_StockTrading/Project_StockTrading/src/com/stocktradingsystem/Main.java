package com.stocktradingsystem;

import java.util.Scanner;

public class Main 
{
    static
    {
        new Helper();
    }
    static Scanner in = new Scanner(System.in); 
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BULE = "\u001B[34m";
    private static final String ANSI_PURUPLE = "\u001B[35m";
    public static void main(String[] args) 
    {
        UserDAO userDAO = new UserDAO();
        TradingDAO tradingDAO = new TradingDAO();
        UserController userController = new UserController(userDAO, tradingDAO);
        TradingController tradingController = new TradingController(tradingDAO); 
        StockManager stockManager = new StockManager(tradingDAO.getStockCount());
        stockManager.setDaemon(true);
        stockManager.start();
        while(true)
		{
			System.out.println(ANSI_BULE+"------------------------------------------------------");
			System.out.println("| Option |            Description                    |");
			System.out.println("------------------------------------------------------");
			System.out.println("|   1    | Login User                                |");
			System.out.println("|   2    | SignUp User                               |");
			System.out.println("|   3    | Exit                                      |");
			System.out.println("------------------------------------------------------"+ANSI_RESET);

			System.out.print(ANSI_YELLOW+"Enter the Option to perform :"+ANSI_RESET);	
			int n = in.nextInt();
			switch(n)
			{
				case 1:
                    int login = userController.login();
                    boolean logout = false;
                    if(login > 0)
                    {
                        System.out.println(ANSI_PURUPLE+"Login Successfully as "+userController.getUserName(login)+ANSI_RESET);
                        while(!logout)
                        {
                            System.out.println(ANSI_BULE+"------------------------------------------------------");
                            System.out.println("| Option |            Description                    |");
                            System.out.println("------------------------------------------------------");
                            System.out.println("|   1    | Buy Stocks                                |");
                            System.out.println("|   2    | Sell Stocks                               |");
                            System.out.println("|   3    | Portfolio                                 |");
                            System.out.println("|   4    | Create Trading Account                    |");
                            System.out.println("|   5    | Profile                                   |");
                            System.out.println("|   6    | Logout                                    |");
                            System.out.println("------------------------------------------------------"+ANSI_RESET);

                            System.out.print(ANSI_YELLOW+"Enter the Option to perform :"+ANSI_RESET);	
                            int m = in.nextInt();
                            switch(m)
                            {
                                case 1:
                                    tradingController.buyStock();
                                    break;

                                case 2:
                                    tradingController.sellStock();
                                    break;

                                case 3:
                                    tradingController.portfolio();
                                    break;

                                case 4:
                                    tradingController.createTradingAccount();
                                    break;

                                case 5:
                                    userController.profile();
                                    break;

                                case 6:
                                    tradingDAO.closeConnection();
                                    logout = userController.logout();
                                    break;
                                    
                                default:
                                    System.out.println("Enter the correct option to perform..");
                                    break;
                            }
                        }
                    }
                    else
                    {
                        System.out.println("Login Failed");
                    }
                    break;

                case 2:
                    userController.signUp();
                    break;

                case 3:
					System.out.println("Application Exiting");
					System.exit(0);
                    break;
                
                default:
                    System.out.println("Enter the correct option to perform..");
                    break;
            }
        }
    }           
}    