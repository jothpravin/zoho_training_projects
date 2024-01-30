package socailmedia;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ChatMessageController 
{
    ChatMessageDAO chatMessageDAO = new ChatMessageDAO();
    UserDAO userDAO = new UserDAO();
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BULE = "\u001B[34m";
    private static final String ANSI_PURUPLE = "\u001B[35m";
    static Scanner in = new Scanner(System.in); 
    public void chat()
    {
        showFriends();
        System.out.println("Enter the friend id to chat :");
        int friendId = in.nextInt();
        if (friendId == -1 || friendId == UserDAO.USER_ID) 
        {
            System.out.println("Invalid friend ID. Please enter a valid ID.");
            return;
        }
        boolean flag = true;
        in.nextLine();
        System.out.println("---"+ANSI_GREEN+userDAO.getAuthor(friendId)+ANSI_RESET+"----------------------------------------------------------------");
        while(flag)
        {
            System.out.println(ANSI_BULE+"Enter the message (type V to view message) or (type exit to end the chat):"+ANSI_RESET);
            String message = in.nextLine();
            if(message.equalsIgnoreCase("V"))
            {
                showMessage(friendId, UserDAO.USER_ID);
            }
            else if(message.equalsIgnoreCase("exit"))
            {
                flag = false;
                System.out.println("--------------------------------------------------------------------------------------");
                break;
            }
            else
            {
                if(!message.isEmpty())
                {    
                    ChatMessage cm = new ChatMessage(UserDAO.USER_ID, friendId, message, new Timestamp(new Date().getTime()));
                    chatMessageDAO.insertNewMessage(cm);
                    System.out.println(ANSI_GREEN+"Message sent successfully!"+ANSI_RESET);
                }
            }
        }
    }

    public void showMessage(int friendId, long userid)
    {
        List<ChatMessage> messages = chatMessageDAO.getAllMessages(friendId, userid);

        for(ChatMessage chatMessage : messages)
        {
            if(chatMessage.getSenderID() == friendId)
            {
                System.out.println(ANSI_BULE+"Message :"+ANSI_RESET+ANSI_YELLOW+chatMessage.getContent()+ANSI_RESET);
                System.out.println(ANSI_BULE+"Time :"+ANSI_RESET+ANSI_PURUPLE+formatTimeStamp(chatMessage.getTimeStamp())+ANSI_RESET);
            }
            else
            {
                System.out.println(ANSI_BULE+"\t\t\t\tMessage :"+ANSI_RESET+ANSI_YELLOW+chatMessage.getContent()+ANSI_RESET);
                System.out.println(ANSI_BULE+"\t\t\t\tTime :"+ANSI_RESET+ANSI_PURUPLE+formatTimeStamp(chatMessage.getTimeStamp())+ANSI_RESET);   
            }
        }

    }

    public void showFriends()
    {
        List<User> users = chatMessageDAO.getAllUsers();

        System.out.println("---------------Your Friends----------------");
        for (User user : users) {
            String onlineStatus = (chatMessageDAO.isUserOnline(user.getUserID())) ? "Online" : "Offline";
            System.out.println("ID: " + user.getUserID() + ", Name: " + user.getUserName() + ", Status: " + onlineStatus);
        }
        System.out.println("-----------------------------------------");
  
    }

    private String formatTimeStamp(Timestamp timestamp) 
	{
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");
        return dateFormat.format(timestamp);
	}
}