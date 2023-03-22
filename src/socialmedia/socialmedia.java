package socialmedia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.*;

public class socialmedia implements SocialMediaPlatform{
    private AccountDatabase accountDatabase = new AccountDatabase();
    private PostDatabase postDatabase = new PostDatabase();

    public socialmedia() throws InvalidPostException {
    }


    @Override
    public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
        Account newAccount = new Account(handle);

        int accountID = this.accountDatabase.addAccount(newAccount);
        return accountID;
    }

    @Override
    public void removeAccount(int id) throws AccountIDNotRecognisedException, PostIDNotRecognisedException {
        ArrayList<Integer> posts = this.accountDatabase.getPostsForAccount(id);
        // Deletes all posts for the account
        for (int postID : posts){
            String postType = this.postDatabase.getPostType(postID);
            if (postType.equals("p")){
                this.postDatabase.deletePost(postID);
            } else if (postType.equals("c")){
                // comment must first find the post its commenting on and delete it

                this.postDatabase.removeComment(postID);
            } else if (postType.equals("e")){
                int endorsedID = this.postDatabase.getEndorsedID(postID);
                int accountID = this.accountDatabase.getAccountIDFromPostID(postID);
                postDatabase.removeEndorsementFromPost(endorsedID, accountID);
            }
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
        postDatabase.iteratePosts();
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
                "Endorse count: " + "Implement this" + "\n" +
                "</pre>";
        return formattedString;
                // need to do endorsement count
    }

    @Override
    public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException, IllegalHandleException {
        Post newPost = new Post(handle, message, "p");
        int postID = this.postDatabase.addPost(newPost);
        this.accountDatabase.addPostToAccount(handle, postID);
        return postID;
    }

    @Override
    public int endorsePost(String handle, int parentID) throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {

        if (this.accountDatabase.uniqueHandle(handle)) {

            throw new HandleNotRecognisedException();

        }
        else if (!this.postDatabase.validatePostID(parentID)) {
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
    public int commentPost(String handle, int parentID, String message) throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException, InvalidPostException, IllegalHandleException {
        // create post object
        Post newPost = new Post(handle, message, "c", parentID);
        int postID = postDatabase.addPost(newPost);
        accountDatabase.addPostToAccount(handle, postID);
        postDatabase.addCommentToPost(parentID, postID);


        return postID;
    }

    @Override
    public void deletePost(int postID) throws PostIDNotRecognisedException {

            String postType = this.postDatabase.getPostType(postID);
            if (postType.equals("p")){
                this.postDatabase.deletePost(postID);
            } else if (postType.equals("c")){
                // comment must first find the post its commenting on and delete it

                this.postDatabase.removeComment(postID);
            } else if (postType.equals("e")){
                int endorsedID = this.postDatabase.getEndorsedID(postID);
                int accountID = this.accountDatabase.getAccountIDFromPostID(postID);
                postDatabase.removeEndorsementFromPost(endorsedID, accountID);
            }
            this.postDatabase.deletePost(postID);


    }

    @Override
    public String showIndividualPost(int id) throws PostIDNotRecognisedException {
        int accountID = accountDatabase.getAccountIDFromPostID(id);
        int endorsementNum = postDatabase.getEndorsementNum(id);
        int commentNum = postDatabase.getCommentNum(id);
        String message = postDatabase.getMessage(id);
        String formattedPost =
                "<pre>\n" +
                "ID: " + id + "\n" +
                "Account: " + accountID + "\n" +
                "No. endorsements: " + endorsementNum + " | No. comments: " + commentNum + "\n" +
                message + "\n" +
                "</pre>";
        return formattedPost;
    }

    @Override
    public StringBuilder showPostChildrenDetails(int id) throws PostIDNotRecognisedException, NotActionablePostException {
        StringBuilder stb = new StringBuilder();
        ArrayList<Integer> Children = postDatabase.getPost(id).getChildren();
        ArrayList<Integer> Visited = new ArrayList<>();
        Visited.add(id);
        ArrayList<Integer> Stack = new ArrayList<>();
        boolean NoChildren = false;
        for(int i = 0;i < Children.size();i++){
            if(postDatabase.getPost(Children.get(i)).getChildrenSize() != 0){
                Stack.addAll(postDatabase.getPost(Children.get(i)).getChildren());
            }
            Visited.add(Children.get(i));
            int finalItem = 1;
            while(!Stack.isEmpty()){
                Visited.add(Stack.get(0));//Pre-Order traversal
                if(postDatabase.getPost(Stack.get(0)).getChildrenSize() != 0){
                    Stack.addAll(postDatabase.getPost(Stack.get(0)).getChildren());
                    finalItem = Stack.get(0);
                }
                Stack.remove(0);
            }
        }System.out.println(Visited);
        for (int i = 0; i < Visited.size()-1; i++){
            if(i != 0 && (postDatabase.getPost(Visited.get(i)).getParentID() == Visited.get(i-1))){
                stb.append("\t"+this.showIndividualPost(Visited.get(i)).replace("\n","\n \t"));
            }else{
                stb.append(this.showIndividualPost(Visited.get(i)));
            }
            stb.append("\n");
        }
        return stb;
    }

    @Override
    public int getMostEndorsedPost() {
        int PostID = postDatabase.getMostEndorsedID();
        return PostID;
    }

    @Override
    public int getMostEndorsedAccount() throws HandleNotRecognisedException {
        // hashmap containing handle, and total endorsements as value (int)
        HashMap<String, Integer> accountEndorsements = postDatabase.endorsementCountPerHandle();
        // find the account with the highest value
        int max = 0;
        String maxHandle = "";
        for (String handle : accountEndorsements.keySet()){
            if (accountEndorsements.get(handle) >= max){
                max = accountEndorsements.get(handle);
                maxHandle = handle;
            }

        }
        return accountDatabase.getAccountID(maxHandle);
    }

    @Override
    public void erasePlatform() {

        // unsure if this is deleting .ser file but no harm in overwriting it if this function is called
        // however don't get filename
        this.accountDatabase = new AccountDatabase();
        this.postDatabase = new PostDatabase();

    }

    @Override
    public void savePlatform(String filename) throws IOException {
        SaveFile saveFile = new SaveFile(accountDatabase, postDatabase);
        try{
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(saveFile);
            out.close();
            fileOut.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void loadPlatform(String filename) throws IOException, ClassNotFoundException {
        try{
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);
            SaveFile saveFile = (SaveFile) in.readObject();
            this.accountDatabase = saveFile.getAccountDatabase();
            this.postDatabase = saveFile.getPostDatabase();
            in.close();
            file.close();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @Override
    public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
        Account newAccount = new Account(handle, description);
        int accountID = this.accountDatabase.addAccount(newAccount);
        return accountID;
    }

    @Override
    public void removeAccount(String handle) throws HandleNotRecognisedException, PostIDNotRecognisedException {
        int id;
        try {
            id = this.accountDatabase.getAccountID(handle);
        } catch (HandleNotRecognisedException e) {
            throw new HandleNotRecognisedException("The handle provided does not exist.");
        }
        ArrayList<Integer> posts = this.accountDatabase.getPostsForAccount(id);
        // Deletes all posts for the account
        for (int postID : posts) {
            String postType = this.postDatabase.getPostType(postID);
            if (postType.equals("p")) {
                this.postDatabase.deletePost(postID);
            } else if (postType.equals("c")) {
                // comment must first find the post its commenting on and delete it

                this.postDatabase.removeComment(postID);
            } else if (postType.equals("e")) {
                int endorsedID = this.postDatabase.getEndorsedID(postID);
                int accountID = this.accountDatabase.getAccountIDFromPostID(postID);
                postDatabase.removeEndorsementFromPost(endorsedID, accountID);
            }
            this.postDatabase.deletePost(postID);

        }

        this.accountDatabase.removeAccount(id);
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

    private Map<String, String> getCommentDetails (int id) throws PostIDNotRecognisedException {
        Map<String, String> commentDetails = new HashMap<>();
        int accountID = accountDatabase.getAccountIDFromPostID(id);
        int endorsementNum = postDatabase.getEndorsementNum(id);
        int commentNum = postDatabase.getCommentNum(id);
        String message = postDatabase.getMessage(id);
        commentDetails.put("accountID", Integer.toString(accountID));
        commentDetails.put("endorsementNum", Integer.toString(endorsementNum));
        commentDetails.put("commentNum", Integer.toString(commentNum));
        commentDetails.put("message", message);
        return commentDetails;
    }

}
