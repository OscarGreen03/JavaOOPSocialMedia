package socialmedia;

import java.io.Serializable;

public class Post implements Serializable {
    private String postID;
    private String userHandle;
    private String message;
        // postid must be valid
        // userid must be valid
        // we can check message
        // we can check type
        // parentid must be checked

    public Post(String postid, String userhandle, String message, String type) throws InvalidPostException {
        //Used only for creating a post, parentid and input for verifyPost is sanitized and normalised or whatever its called
        verifyPost(postid, userhandle, message, type, null);
    }


    public Post(String postid, String userhandle, String message, String type, String parentid) throws InvalidPostException {
        //This Constructor is used for when a post is a comment or an endorsement
        verifyPost(postid, userhandle, message, type, parentid);
    }

    private boolean verifyPost(String postid, String userhandle, String message, String type, String parentid) throws InvalidPostException {
        System.out.println("Post ID: " + postid);
        System.out.println("User ID: " + userhandle);
        System.out.println("Message: " + message);
        System.out.println("Type: " + type);
        System.out.println("Parent ID: " + parentid);
        this.postID = postid;
        this.userHandle = userhandle;
        this.message = message;

        // check parentid is not null



        // verify the message is acceptable
        if (message.length() > 100) {
            //raise InvalidPostException
            throw new InvalidPostException("Message is too long");
        }
        if (type == "p"){

            System.out.println("Post");
        }

        else if (type == "c"){
            System.out.println("Comment");
        }
        else if (type == "e"){
            System.out.println("Endorsement");
        }
        else{
            throw new InvalidPostException("Invalid Post Type");
        }
    return true;
    }
    public String getPostID() {
        return this.postID;
    }
    public String ReturnPostContent(){
        return this.message;
    }
}

