package ecommerence.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import ecommerence.controller.CustomerController;
import ecommerence.controller.MerchantController;
import ecommerence.dao.CustomerDAO;
import ecommerence.dao.MerchantDAO;
import ecommerence.utils.Helper;

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
        CustomerDAO customerDAO = new CustomerDAO();
        CustomerView customerView = new CustomerView();
        CustomerController customerController = new CustomerController(customerDAO, customerView);
        MerchantDAO merchantDAO = new MerchantDAO();
        MerchantView merchantView = new MerchantView();
        MerchantController merchantController = new MerchantController(merchantDAO, merchantView);
        while(true)
        {
            try
            {
                System.out.println(ANSI_BULE+"------------------------------------------------------");
                System.out.println("| Option |            Description                    |");
                System.out.println("------------------------------------------------------");
                System.out.println("|   1    | Customer Login                            |");
                System.out.println("|   2    | Customer SignUp                           |");
                System.out.println("|   3    | Merchant Login                            |");
                System.out.println("|   4    | Merchant SignUp                           |");
                System.out.println("|   5    | Exit                                      |");
                System.out.println("------------------------------------------------------"+ANSI_RESET);

                System.out.print(ANSI_YELLOW+"Enter the Option to perform :"+ANSI_RESET);	
                int n = in.nextInt();
                switch(n)
                {
                    case 1:
                        int login = customerController.login();
                        boolean logout = false;
                        if(login > 0)
                        {
                            System.out.print(ANSI_PURUPLE+"Login Successfully as ");
                            customerController.getCustomerName();
                            System.out.println(ANSI_RESET);
                            while(!logout)
                            {
                                System.out.println(ANSI_BULE+"------------------------------------------------------");
                                System.out.println("| Option |            Description                    |");
                                System.out.println("------------------------------------------------------");
                                System.out.println("|   1    | Browse Products                           |");
                                System.out.println("|   2    | View Cart                                 |");
                                System.out.println("|   3    | Order Status                              |");
                                System.out.println("|   4    | Return Order                              |");
                                System.out.println("|   5    | Order History                             |");
                                System.out.println("|   6    | Add New Address                           |");
                                System.out.println("|   7    | Logout                                    |");
                                System.out.println("------------------------------------------------------"+ANSI_RESET);
                    
                                System.out.print(ANSI_YELLOW+"Enter the Option to perform :"+ANSI_RESET);	
                                int m = in.nextInt(); 
                                switch(m)
                                {
                                    case 1:
                                        customerController.buyProduct();
                                        break;
                                    
                                    case 2:
                                        customerController.cartToBuy();
                                        break;
                                    
                                    case 3:
                                        customerController.orderCancel();
                                        break;
                                    
                                    case 4:
                                        customerController.orderReturn();
                                        break;
                                    
                                    case 5:
                                        customerController.showOrderHistory();
                                        break;
                                    
                                    case 6:
                                        customerController.newAddress();
                                        break;
                                
                                    case 7:
                                        logout = customerController.logout();
                                        System.out.println("Logging Out...!");
                                        break;

                                    default:
                                        System.out.println("Enter the correct option to perform..");
                                        break;
                                }  
                            }
                        }
                        else
                        {
                            System.out.println("Invalid Credentials....!");
                        }
                        break;

                    case 2:
                        customerController.signUp();
                        break;

                    case 3:
                        int loginMerchan = merchantController.login();
                        boolean logoutMerchan = false;
                        if(loginMerchan > 0)
                        {
                            System.out.print(ANSI_PURUPLE+"Merchan Login Successfully as ");
                            merchantController.getMerchanName();
                            System.out.println(ANSI_RESET);
                            while(!logoutMerchan)
                            {
                                System.out.println(ANSI_BULE+"------------------------------------------------------");
                                System.out.println("| Option |            Description                    |");
                                System.out.println("------------------------------------------------------");
                                System.out.println("|   1    | List New Products                         |");
                                System.out.println("|   2    | Update Product Price                      |");
                                System.out.println("|   3    | Update Product Quantity                   |");
                                System.out.println("|   4    | Earnings Profile                          |");
                                System.out.println("|   5    | DashBoard                                 |");
                                System.out.println("|   6    | Logout                                    |");
                                System.out.println("------------------------------------------------------"+ANSI_RESET);
                    
                                System.out.print(ANSI_YELLOW+"Enter the Option to perform :"+ANSI_RESET);	
                                int m = in.nextInt(); 
                                switch(m)
                                {
                                    case 1:
                                        merchantController.addNewProduct();
                                        break;
                                    
                                    case 2:
                                        merchantController.updateProductPrice();
                                        break;
                                    
                                    case 3:
                                        merchantController.updateProductQuantity();
                                        break;
                                    
                                    case 4:
                                        merchantController.showProfile();
                                        break;

                                    case 5:
                                        merchantController.showDashboard();
                                        break;

                                    case 6:
                                        logoutMerchan = merchantController.logout();
                                        break;
                                    
                                    default:
                                        System.out.println("Enter the correct option to perform..");
                                        break;
                                }
                            }
                        }
                        else
                        {
                            System.out.println("Invalid Credentials");
                        }
                        break;

                    case 4:
                        merchantController.signUp();
                        break;

                    case 5:
                        customerController.logout();
                        System.out.println("Application exiting..!");
                        System.exit(0);

                    default:
                        System.out.println("Enter the correct option to perform..");
                        break;
                }
            }
            catch(InputMismatchException e)
            {
                System.out.println(e.getMessage());
            } 
        } 
          
    }
}
