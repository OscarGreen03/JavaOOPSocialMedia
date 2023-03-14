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
        // Create a new map
        // string is the User Handle

        // add random posts to the map
        System.out.println("PostDatabase Constructor");
        //this.postDatabase = new HashMap<Integer, Post>();

        // String is PostID, Post is the post object and all

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
            postDatabase.remove(postID);
        }
        else{
            throw new PostIDNotRecognisedException("Post ID: " + postID + " not recognised");
        }
    }




    public void testfunc(){
        System.out.println("Test Func");
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
}