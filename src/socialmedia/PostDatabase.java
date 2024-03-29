package socialmedia;
//import java.io.Serial;
import java.io.Serializable;

// java map import
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;



public class PostDatabase implements Serializable {
    //@Serial
    private static final long serialVersionUID = 245345;
    public Map<Integer, Post> postDatabase = new HashMap<Integer, Post>();
    private Integer lastID = 0;
    public PostDatabase() {
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
            // gets array of children and set their parents to -1 (null post)
            ArrayList children = postDatabase.get(postID).getChildren();
            if (children.size() > 0){
                for (Object child : children){
                    postDatabase.get(child).setParent(-1);
                }
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
        // validateID checks if the postID already exists
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

    public void removeComment(int commentID) throws PostIDNotRecognisedException {
        for (Map.Entry<Integer, Post> entry : postDatabase.entrySet()) {
            ArrayList children = entry.getValue().getChildren();
            // if comment ID is in children array, then remove it and break from loop
            if (children.contains(commentID)){
                int postID = entry.getKey();
                postDatabase.get(postID).removeChildren(commentID);

            }
        }
        deletePost(commentID);
    }

    public int getEndorsedID(int postID){
        return postDatabase.get(postID).getEndorsedID();
    }
    public void removeEndorsementFromPost(int postID, int endorserID){
        postDatabase.get(postID).removeEndorsement(endorserID);
    }

    public int getCommentNum(int postID){
        return postDatabase.get(postID).getChildrenSize();
    }
    public int getEndorsementNum(int postID) {
        return postDatabase.get(postID).getEndorsementNum();
    }
    public String getMessage(int postID){
        return postDatabase.get(postID).getMessage();
    }
    public ArrayList<Integer> getChildren(int postID){
        return postDatabase.get(postID).getChildren();
    }
    public int getMostEndorsedID(){
        // iterate through map and find the post with the most endorsements
        // return the postID
        int highestEndorsement = -1;
        int highestEndorsementID = -1;
        for (Map.Entry<Integer, Post> entry : postDatabase.entrySet()){
            int endorsementNum = entry.getValue().getEndorsementNum();
            if (endorsementNum > highestEndorsement){
                highestEndorsement = endorsementNum;
                highestEndorsementID = entry.getKey();
            }

        }
        return highestEndorsementID;
    }
    public Post getPost(int id) throws PostIDNotRecognisedException {
        Post post = postDatabase.get(id);
        if (post == null){
            throw new PostIDNotRecognisedException("Post ID '" + id + "' not recognised");
        }
        return post;
    }

    public HashMap endorsementCountPerHandle(){
        HashMap<String, Integer> accountEndorsements = new HashMap<String, Integer>();
        for (Map.Entry<Integer, Post> entry : postDatabase.entrySet()){
            int endorsementNum = entry.getValue().getEndorsementNum();
            String handle = entry.getValue().getHandle();
            if (accountEndorsements.containsKey(handle)){
                accountEndorsements.put(handle, accountEndorsements.get(handle) + endorsementNum);
            }
            else{
                accountEndorsements.put(handle, endorsementNum);
            }
        }
        return accountEndorsements;
    }
}