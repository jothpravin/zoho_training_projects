package onlineBanking.view;

import java.util.Scanner;

import onlineBanking.utils.Helper;

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
    //private static final String ANSI_PURUPLE = "\u001B[35m";

    public static void main(String[] args) 
    {
        try 
        {
            while(true)
            {
                System.out.println(ANSI_BULE+"------------------------------------------------------");
                System.out.println("| Option |            Description                    |");
                System.out.println("------------------------------------------------------");
                System.out.println("|   1    | Customer Login                            |");
                System.out.println("|   2    | Customer SignUp                           |");
                System.out.println("|   3    | Exit                                      |");
                System.out.println("------------------------------------------------------"+ANSI_RESET);
    
                System.out.print(ANSI_YELLOW+"Enter the Option to perform :"+ANSI_RESET);	
                int n = in.nextInt();
                switch(n)
                {
                    case 1:
                        break;

                    case 2:
                        break;

                    case 3:
                        System.out.println("Application exiting....!");
                        System.exit(0);
                        break;
                    
                    default:
                        System.out.println("Enter the correct option to perform..");
                        break;
                }
            }
        } 
        catch(Exception e) 
        {
            System.err.println(e.getMessage());
        }
    }   
}
