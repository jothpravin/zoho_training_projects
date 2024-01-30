package socailmedia;

import java.util.Scanner;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

class PostController 
{
	Scanner in = new Scanner(System.in);
    PostDAO postDAO = new PostDAO();
    UserDAO userDAO = new UserDAO();

    public void newPost()
    {
        try
        {
            System.out.print("Enter the tweet: ");
            String tweet = in.nextLine();
            Date curDate = new Date();
            Timestamp timestamp = new Timestamp(curDate.getTime());

            Post p = new Post(UserDAO.USER_ID, tweet,userDAO.getAuthor(UserDAO.USER_ID), timestamp);
            postDAO.createPost(p);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void deletePost()
    {
        postSummary();
        System.out.print("Enter the post id to delete or 0 to back: ");
        long postid = in.nextLong();
        if(postid == 0) return;
        postDAO.deletePost(postid);
    }

    private void postSummary()
    {
        List<String> post = postDAO.showPostSum();

        for(String p : post)
        {
            System.out.println(p);
        }
    }

    public void showPostFeed()
    {
        List<Post> posts = postDAO.showAllFeeds();

        for (Post post : posts) 
        {
            System.out.println("Author: " + post.getAuthorName());
            System.out.println("Content: " + post.getContent());
            System.out.println("Timestamp: " + formatTimeStamp(post.getTimestamp()));
            if(isLiked(UserDAO.USER_ID, post.getPostID()))
            {
                System.out.println("Likes ♥️: " + postDAO.getLikesCount(post.getPostID()));
            }
            else
            {
                System.out.println("Likes ♡: " + postDAO.getLikesCount(post.getPostID()));
            }
            System.out.println("Comments 🗨️ : ");
            List<Comment> comments = postDAO.getCommentsForPost(post.getPostID());
            if(comments != null)
            {
                for(Comment comment : comments) 
                {
                    //System.out.println("  Comment ID: " + comment.getCommentID());
                    System.out.println("  Author: " + userDAO.getAuthor(comment.getUserID()));
                    System.out.println("  Content: " + comment.getContent());
                    System.out.println("  Timestamp: " + formatTimeStamp(comment.getTimestamp()));
                    System.out.println("-----------------------------------------");
                }
            }
            System.out.println("Press L to like, C to comment, or any other key to continue.");
            String choice = in.next().toUpperCase();
            if(choice.equals("L")) 
            {
                    postDAO.addLike(UserDAO.USER_ID, post.getPostID());
                    System.out.println("You liked the post!");
            } 
            else if(choice.equals("C")) 
            {
                in.nextLine(); // Consume the newline character
                System.out.print("Enter your comment: ");
                String commentText = in.nextLine();
                Date cur = new Date();
                Timestamp timestamp = new Timestamp(cur.getTime());
                Comment comment = new Comment(post.getPostID(), UserDAO.USER_ID, timestamp, commentText);
                postDAO.addComment(comment);
                System.out.println("Your comment has been added!");
            }
            System.out.println("-----------------------------------------");
        }
    }
    
    private String formatTimeStamp(Timestamp timestamp) 
	{
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");
        return dateFormat.format(timestamp);
	}

    private boolean isLiked(long user_id, long post_id)
    {
        return postDAO.isLikedPost(user_id, post_id);
    }
    
    
}
