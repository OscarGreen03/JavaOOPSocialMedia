package socialmedia;
import java.io.Serializable;

// java map import
import java.util.Map;
import java.util.HashMap;



public class PostDatabase implements Serializable {

    private static Map<String, Post> postDatabase = new HashMap<String, Post>();
    public PostDatabase() throws InvalidPostException {
        // Create a new map
        // string is the User Handle

        // add random posts to the map
        System.out.println("PostDatabase Constructor");
        // String is PostID, Post is the post object and all

    }
    public boolean  addPost(Post post) throws InvalidPostException {
        try {
            postDatabase.put(post.getPostID(), post);
            return true;
        }
        catch (Exception e){
            // print stack trace
            e.printStackTrace();
            throw new InvalidPostException("Invalid Post");
        }
    }
    public void iteratePosts(){
        for (Map.Entry<String, Post> entry : postDatabase.entrySet()) {
            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue().ReturnPostContent());
        }

    }


    }
