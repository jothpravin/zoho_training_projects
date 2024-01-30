package socailmedia;

import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.io.Console;

class UserController
{
	Scanner in = new Scanner(System.in);
	Console con = System.console();
	UserDAO userDAO = new UserDAO();
	
	public void signUp()
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
				System.out.print("Enter the valid Password: ");
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
				g = Gender.male;
			}
			else if(n==2)
			{
				g = Gender.female;
			}
			in.nextLine();
			System.out.print("Enter the userName :");
			String userName = in.nextLine();
			System.out.print("Enter the Bio: ");
			String bio = in.nextLine();
			
			User newUser = new User(userName, pass, email, date, bio, g);
			userDAO.addUser(newUser);
			System.out.println("New User added with ID: " + newUser.getUserID());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public long login()
	{
		System.out.print("Enter the Email ID: ");
		String email = in.next();
		System.out.print("Enter the Password: ");
        char[] pas = con.readPassword();
		String pass = new String(pas);
		if(Validator.emailValidator(email) && Validator.isValidPassword(pass))
		{
			return userDAO.loginCredential(email, pass);
		}
		return -1;
	}

	public void logout()
	{
		if(userDAO.logout())
		{
			System.out.println("Logging out...");
			System.exit(0);
		}
		else
		{
			System.out.println("Unable to log out...");
		}
	}

	private boolean searchFriends()
	{
		List<User> users = userDAO.getUserInfo();
		if(users.size() == 0)
		{
			return false;
		}
		System.out.println("---------------Find-Peoples----------------");
		for(User user : users)
		{
			System.out.println("ID: " + user.getUserID());
			System.out.println("Name: " + user.getUserName());
			System.out.println("Bio: " + user.getBio());
			System.out.println("-----------------------------------------");
		}
		return true;
	}

	public void findFriends()
	{
		if(!searchFriends())
		{
			System.out.println("No new peoples..");
			return;
		}
		System.out.print("Enter the ID to connect or 0 to back: ");
		long id = in.nextLong();
		if(id == 0)
		{
			return;
		}
		userDAO.connectFriends(UserDAO.USER_ID, id);
	}

	public void profile() 
	{
		List<User> users = userDAO.getFullUserInfo(UserDAO.USER_ID);

		for (User user : users) 
		{
			System.out.println("=======================================");
			System.out.println("Username: " + user.getUserName());
			System.out.println("Bio: " + user.getBio());
			System.out.println("Posts: " + getPostCount(user)); 
			System.out.println("Followers: " + user.getFollowers());
			System.out.println("Following: " + user.getFollowing());
			System.out.println("Status: " + (user.getStatus() == Status.online ? "Online" : "Offline"));
			System.out.println("Joined: " + formatDate(user.getJoined())); 
			System.out.println("=======================================");
		}
	}

	private long getPostCount(User user) 
	{
		return userDAO.getUserPostCount(user.getUserID());
	}

	private String formatDate(Date joined) 
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        return dateFormat.format(joined);
	}

}
