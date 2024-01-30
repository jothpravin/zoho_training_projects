package socailmedia; 

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
		UserController uc = new UserController();
		PostController pc = new PostController();
		ChatMessageController cmc = new ChatMessageController();
		while(true)
		{
			System.out.println("------------------------------------------------------");
			System.out.println("| Option |            Description                    |");
			System.out.println("------------------------------------------------------");
			System.out.println("|   1    | Login                                     |");
			System.out.println("|   2    | SignUp                                    |");
			System.out.println("|   3    | Exit                                      |");
			System.out.println("------------------------------------------------------");

			System.out.print("Enter the Option to perform :");	
			int n = in.nextInt();
			switch(n)
			{
				case 1:
					
					if(uc.login()>0)
					{
						System.out.println("Login Successfully");
						while(true)
						{
							System.out.println("------------------------------------------------------");
							System.out.println("| Option |            Description                    |");
							System.out.println("------------------------------------------------------");
							System.out.println("|   1    | New Post                                  |");
							System.out.println("|   2    | Delete Post                               |");
							System.out.println("|   3    | Show Feed                                 |");
							System.out.println("|   4    | Search Friends                            |");
							System.out.println("|   5    | Profile                                   |");
							System.out.println("|   6    | Chat                                      |");
							System.out.println("|   7    | Log Out!!                                 |");
							System.out.println("------------------------------------------------------");

							System.out.print("Enter the Option to perform :");	
							int m = in.nextInt();
							switch(m)
							{
								case 1:
									pc.newPost();
									break;

								case 2:
									pc.deletePost();
									break;

								case 3:
									pc.showPostFeed();
									break;

								case 4:
									uc.findFriends();
									break;

								case 5:
									uc.profile();
									break;

								case 6:
									cmc.chat();
									break;

								case 7:
									uc.logout();
									break;

								default:
									System.out.println("Enter the correct option to perform..");

							}
						}
					}
					else
					{
						System.out.println("Invalid Crendentials");
					}
					
					break;
					
				case 2:
					uc.signUp();
					break;
				
				case 3:
					System.out.println("Application Exiting...");
					uc.logout();
					System.exit(0);

				default:
					System.out.println("Enter the correct option to perform..");
			}
		}
	}
}
