package socialmedia;
import java.io.Serializable;

// java map import
import java.util.Map;
import java.util.HashMap;



public class PostDatabase implements Serializable {

    private static Map<Integer, Post> postDatabase = new HashMap<Integer, Post>();
    public PostDatabase() throws InvalidPostException {
        // Create a new map
        // string is the User Handle

        // add random posts to the map
        System.out.println("PostDatabase Constructor");
        // String is PostID, Post is the post object and all

    }
    public boolean addPost(Post post) throws InvalidPostException {
        // generate new post id
        // add post to map
        int postID = generatePostID();
        try {
            postDatabase.put(postID, post);
            return true;
        }
        catch (Exception e){
            // print stack trace
            e.printStackTrace();
            throw new InvalidPostException("Invalid Post");
        }
    }
    public void iteratePosts(){
        for (Map.Entry<Integer, Post> entry : postDatabase.entrySet()) {
            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue().ReturnPostContent());
        }
    }
    private int generatePostID(){
        // check the last string generated (by checking hash map)
        // add 1 to last string as int and convert back

        // check length of postDatabase, if 0 then return 1

        return (postDatabase.size() + 1);
    }

    }
