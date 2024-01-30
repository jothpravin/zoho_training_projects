package librarymanagement;

import java.util.Scanner;

public class Main 
{
    static
    {
        new Helper();
    }
    static Scanner in = new Scanner(System.in);
    public static void main(String []args)
    {
        UserDAO userDAO = new UserDAO();
        UserController userController = new UserController(userDAO);
        BookDAO bookDAO = new BookDAO();
        BookController bookController = new BookController(bookDAO, userController);
        while(true)
		{
			System.out.println("------------------------------------------------------");
			System.out.println("| Option |            Description                    |");
			System.out.println("------------------------------------------------------");
			System.out.println("|   1    | Login User                                |");
			System.out.println("|   2    | Login Admin                               |");
			System.out.println("|   3    | SignUp User                               |");
			System.out.println("|   4    | SignUp Admin                              |");
			System.out.println("|   5    | Exit                                      |");
			System.out.println("------------------------------------------------------");

			System.out.print("Enter the Option to perform :");	
			int n = in.nextInt();
			switch(n)
			{
				case 1:
                    int loginUser = userController.loginUser();
                    boolean logoutUser = false;
                    if(loginUser > 0)
                    {
						System.out.println("Login Successfully as User: "+userController.getUserName());
						while(!logoutUser)
						{
							System.out.println("------------------------------------------------------");
							System.out.println("| Option |            Description                    |");
							System.out.println("------------------------------------------------------");
							System.out.println("|   1    | View Book                                 |");
							System.out.println("|   2    | Book Reservation                          |");
							System.out.println("|   3    | Log Out                                   |");
							System.out.println("------------------------------------------------------");

							System.out.print("Enter the Option to perform :");	
							int m = in.nextInt();
							switch(m)
							{
								case 1:
                                    boolean exit = false;
                                    while(!exit)
                                    {
                                        bookController.getBooksByRack();
                                        System.out.print("Enter 1 to go back: ");
                                        int e = in.nextInt();
                                        if(e==1)
                                        {
                                            exit = true;
                                        }
                                    }
									break;

								case 2:
                                    bookController.sendBookReserveRequest();
									break;

								case 3:
                                    logoutUser = userController.logout();
									break;

                                default:
                                    System.out.println("Enter the correct option to perform..");
                                    break;

							}
						}
                    }
                    else
                    {
                        System.out.println("Invalid Crendentials");
                    }
                    break;
					
				case 2:
                    int loginAdmin = userController.loginAdmin();
                    boolean logoutAdmin = false;
                    if(loginAdmin> 0)
                    {
                        System.out.println("Login Successfully as Admin: "+userController.getUserName());
                        while(!logoutAdmin)
                        {
                            System.out.println("------------------------------------------------------");
                            System.out.println("| Option |            Description                    |");
                            System.out.println("------------------------------------------------------");
                            System.out.println("|   1    | New Book Entry                            |");
                            System.out.println("|   2    | Direct Issue Entry                        |");
                            System.out.println("|   3    | Reservation Issue Entry                   |");
                            System.out.println("|   4    | Return Entry                              |");
                            System.out.println("|   5    | Log Out                                   |");
                            System.out.println("------------------------------------------------------");

                            System.out.print("Enter the Option to perform :");	
                            int m = in.nextInt();
                            switch(m)
                            {
                                case 1:
                                    bookController.addNewBook();
                                    break;

                                case 2:
                                    bookController.bookDirectIssue();
                                    break;

                                case 3:
                                    bookController.bookReservationIssue();
                                    break;

                                case 4:
                                    bookController.returnBookEntry();
                                    break;

                                case 5:
                                    logoutAdmin = bookController.userController.logout();
                                    break;

                                default:
                                    System.out.println("Enter the correct option to perform..");
                                    break;
                            }
                        }
                        
                    }
                    else
                    {
                        System.out.println("Invalid Crendentials");
                    }
					break;
				case 3:
                    userController.signUpForUsers();
                    break;

                case 4:
                    userController.signUpForAdmin();    
                    break;

				case 5:
					System.out.println("Application Exiting");
					System.exit(0);

				default:
					System.out.println("Enter the correct option to perform..");
                    break;
			}
		}
	}
}