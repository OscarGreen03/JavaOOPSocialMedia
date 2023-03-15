package socialmedia;
import java.io.Serial;
import java.io.Serializable;

// java map import
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;



public class PostDatabase implements Serializable {
    @Serial
    private static final long serialVersionUID = 245345;
    public Map<Integer, Post> postDatabase = new HashMap<>();
    private Integer lastID = 0;
    public PostDatabase() throws InvalidPostException {
        Post nullPost = new Post();
        postDatabase.put(-1, nullPost);
    }
    public int addPost(Post post) throws InvalidPostException {
        // generate new post id
        // add post to map
        int postID = generatePostID();

        try {
            // print both postid and post

            postDatabase.put(postID, post);
            return postID;
        }
        catch (Exception e){
            // print stack trace
            e.printStackTrace();
            throw new InvalidPostException("Invalid Post");
        }
    }
    public void iteratePosts(){
        // iterate through the map and print the posts
        for (Map.Entry<Integer, Post> entry : postDatabase.entrySet()) {
            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue());
        }
    }
    private int generatePostID(){
        // check the last string generated (by checking hash map)
        // add 1 to last string as int and convert back

        // check length of postDatabase, if 0 then return 1
        // else return the last key + 1
        lastID += 1;
        return lastID;

    }
    public void deletePost(int postID) throws PostIDNotRecognisedException {
        // check if postID is in the map
        // if it is then remove it
        // if it isn't then raise PostIDNotRecognisedException

        if (postDatabase.containsKey(postID)){
            ArrayList children = postDatabase.get(postID).getChildren();
            for (Object child : children){
                postDatabase.get(child).setParent(-1);
            }
            postDatabase.remove(postID);
        }
        else{
            throw new PostIDNotRecognisedException("Post ID: " + postID + " not recognised");
        }
    }

    public ArrayList<String> getPostTypes(){
        ArrayList<String> postTypes = new ArrayList<>();
        for (Map.Entry<Integer, Post> entry : postDatabase.entrySet()) {
            postTypes.add(entry.getValue().getType());
        }
        //System.out.println(postTypes);
        return postTypes;
    }
    public String getPostType(int id){
        return postDatabase.get(id).getType();
    }
    public boolean validatePostID(int id) throws PostIDNotRecognisedException {
        if (!postDatabase.containsKey(id)) {
            return false;
        }
        return true;
    }

    public void addCommentToPost(int postID, int commentID){
        postDatabase.get(postID).addChildren(commentID);
    }
    public void addEndorsementToPost(int postID, int endorserID){
        postDatabase.get(postID).addEndorsement(endorserID);
    }
    public void removeComment(int commentID){
        for (Map.Entry<Integer, Post> entry : postDatabase.entrySet()) {
            ArrayList children = entry.getValue().getChildren();
            // if comment ID is in children array, then remove it and break from loop
            if (children.contains(commentID)){
                int postID = entry.getKey();
                postDatabase.get(postID).removeChildren(commentID);
            }
        }
    }
}