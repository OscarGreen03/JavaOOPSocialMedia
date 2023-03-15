package socialmedia;

import java.io.Serializable;
import java.util.ArrayList;
public class Post implements Serializable {

    private String userHandle;
    private String message;
    private String type;
    private int parentID;
    private ArrayList<Integer> endorsements = new ArrayList<>();
    private ArrayList<Integer> children;
        // postid must be valid
        // userid must be valid
        // we can check message
        // we can check type
        // parentid must be checked

    public Post(String userhandle, String message, String type) throws InvalidPostException {
        //Used only for creating a post, parentid and input for verifyPost is sanitized and normalised or whatever its called
        verifyPost( userhandle, message, type, 0);
    }

    public Post( String userhandle, String message, String type, Integer parentid) throws InvalidPostException {
        //This Constructor is used for when a post is a comment
        verifyPost( userhandle, message, type, parentid);
    }

    public Post (String userhandle, String type, Integer parentid) throws InvalidPostException {
        //This constructor is used for when a post is a endorsement as no message is needed
        verifyPost( userhandle, "", type, parentid);
    }

    public Post(){
        this.message = "The original content was removed from the system and is no longer available.";
        this.userHandle = "Null";
        this.type = "x";// this is the constructor for nullpost, which will be pointed to by the parent of a post that has no parent (been deleted)
    }
    private boolean verifyPost( String userhandle, String message, String type, Integer parentid) throws InvalidPostException {




        this.userHandle = userhandle;
        this.message = message;
        this.type = type;

       // check if parentid is not 0 else don't declare
        if (parentid != 0){
            this.parentID = parentid;
        }

        // check parentid is not null



        // verify the message is acceptable
        if (message.length() > 100) {
            //raise InvalidPostException
            throw new InvalidPostException("Message is too long");
        }

        if (type.equals("p")){
            //System.out.println("Post");
        }
        else if (type.equals("c")){
            //System.out.println("Comment");
        }
        else if (type.equals("e")){
            //System.out.println("Endorsement");
        }
        else{
            throw new InvalidPostException("Invalid Post Type");
        }
    return true;
    }

    public String ReturnPostContent(){
        return "\nUser Handle: " + this.userHandle + " \nMessage: " + this.message + "\n Type: " + this.type;
    }

    public void addChildren(int childID){
        this.children.add(childID);
    }
    public ArrayList getChildren(){
        return this.children;
    }
    public void removeChildren(int childID){
        this.children.remove(childID);

    }
    public String getType() {
        //System.out.println("Post Type: " + this.type);
        return this.type;
    }
    public void addEndorsement(int EndorsingAccount){
        this.endorsements.add(EndorsingAccount);
    }
    public void removeEndorsement(int EndorsingAccount){
        this.endorsements.remove(EndorsingAccount);
    }
    public ArrayList getEndorsements(){
        return this.endorsements;
    }
    public int getEndorsedID(){
        return this.parentID;
    }

    public void setParent(int id){
        this.parentID = id;
    }
}

