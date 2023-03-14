package socialmedia;

import java.io.IOException;
import java.util.ArrayList;

public class socialmedia implements SocialMediaPlatform{
    private AccountDatabase accountDatabase = new AccountDatabase();
    private PostDatabase postDatabase = new PostDatabase();

    public socialmedia() throws InvalidPostException {
    }


    @Override
    public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
        return this.accountDatabase.createAccount(handle);
    }

    @Override
    public void removeAccount(int id) throws AccountIDNotRecognisedException, PostIDNotRecognisedException {
        ArrayList<Integer> posts = this.accountDatabase.getPostsForAccount(id);
        for (int postID : posts){
            this.postDatabase.deletePost(postID);
        }

        this.accountDatabase.removeAccount(id);
    }

    @Override
    public void changeAccountHandle(String oldHandle, String newHandle) throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
        this.accountDatabase.updateHandle(oldHandle, newHandle);
    }

    @Override
    public String showAccount(String handle) throws HandleNotRecognisedException {
        int accountID = this.accountDatabase.getAccountID(handle);
        String description = this.accountDatabase.getDescription(accountID);
        ArrayList<Integer> posts = this.accountDatabase.getPostsForAccount(accountID);
        ArrayList<String> postTypes = new ArrayList<>();
        for (int postID : posts){
            postTypes.add(this.postDatabase.getPostType(postID));
        }
        int postCount = posts.size();
        int endorsementCount = 0;
        for (String postType : postTypes){
            if(postType.equals("e")){
                endorsementCount++;
            }
        }


        String formattedString = "<pre>\n" +
                "ID: " + accountID + "\n" +
                "Handle: " + handle + "\n" +
                "Description: " + description + "\n" +
                "Post count: " + postCount + "\n" +
                "</pre>";
        return formattedString;
                // need to do endorsement count
    }

    @Override
    public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException, IllegalHandleException {
        Post newPost = new Post(handle, message, "c");
        int postID = this.postDatabase.addPost(newPost);
        this.accountDatabase.addPostToAccount(handle, postID);
        return postID;
    }

    @Override
    public int endorsePost(String handle, int parentID) throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
        if (!accountDatabase.validateHandle(handle)) {
            throw new HandleNotRecognisedException();
        }
        else if (!postDatabase.validatePostID(parentID)) {
            throw new PostIDNotRecognisedException();
        }

        Post post = new Post(handle, "e", parentID);
        int endorsementID = postDatabase.addPost(post);
        // endorsements work by adding the postID of the endorsement to the post being endorsed
        postDatabase.addEndorsementToPost(parentID, endorsementID);
        // endorsements also add the endorsed postID to the account that made the endorsement
        accountDatabase.addEndorsementToAccount(handle, parentID);
        return endorsementID;
    }

    @Override
    public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
        return 0;
    }

    @Override
    public void deletePost(int id) throws PostIDNotRecognisedException {

    }

    @Override
    public String showIndividualPost(int id) throws PostIDNotRecognisedException {
        return null;
    }

    @Override
    public StringBuilder showPostChildrenDetails(int id) throws PostIDNotRecognisedException, NotActionablePostException {
        return null;
    }

    @Override
    public int getMostEndorsedPost() {
        return 0;
    }

    @Override
    public int getMostEndorsedAccount() {
        return 0;
    }

    @Override
    public void erasePlatform() {

    }

    @Override
    public void savePlatform(String filename) throws IOException {

    }

    @Override
    public void loadPlatform(String filename) throws IOException, ClassNotFoundException {

    }

    @Override
    public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
        return 0;
    }

    @Override
    public void removeAccount(String handle) throws HandleNotRecognisedException {

    }

    @Override
    public void updateAccountDescription(String handle, String description) throws IllegalHandleException {
            this.accountDatabase.updateAccountDescription(handle, description);
    }

    @Override
    public int getNumberOfAccounts() {
        return this.accountDatabase.AccountSize();
    }

    @Override
    public int getTotalOriginalPosts() {
        ArrayList<String> postTypes = this.postDatabase.getPostTypes();
        //System.out.println(postTypes);
        int count = 0;
        for (String postType : postTypes){
            if (postType.equals("p")){
                count++;
            }
        }

        return count;
    }

    @Override
    public int getTotalEndorsmentPosts() {
        ArrayList<String> postTypes = this.postDatabase.getPostTypes();
        int count = 0;
        for (String postType : postTypes){
            if (postType.equals("e")){
                count++;
            }
        }

        return count;
    }

    @Override
    public int getTotalCommentPosts() {
        ArrayList<String> postTypes = this.postDatabase.getPostTypes();
        int count = 0;
        for (String postType : postTypes){
            if (postType.equals("c")){
                count++;
            }
        }

        return count;
    }
}
