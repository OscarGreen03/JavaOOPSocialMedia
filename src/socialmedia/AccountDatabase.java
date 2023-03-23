package socialmedia;

//import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class AccountDatabase implements Serializable {
    //@Serial
    private static final long serialVersionUID = 983484;
    private Map<Integer, Account> accountDatabase = new HashMap<Integer, Account>();
    private Integer lastID = 0;
    private Map<String, Integer> handleToID = new HashMap<String, Integer>();

    public int addAccount(Account account) throws IllegalHandleException {
        // generate new account id
        // add account to map
        if (!uniqueHandle(account.getHandle())){
            throw new IllegalHandleException("The Handle already exists in the databse");
        }
        int accountID = generateAccountID();
        accountDatabase.put(accountID, account);
        handleToID.put(account.getHandle(), accountID);
        return accountID;


    }
    private Integer generateAccountID(){
        // check the last string generated (by checking hash map)
        // add 1 to last string as int and convert back

        // get last element in the maps key
        // add 1 to it

        lastID += 1;
        return lastID;

    }
    public Integer createAccount(String handle) throws IllegalHandleException {
        if (uniqueHandle(handle)){
            int handleID = generateAccountID();
            Account account = new Account(handle);
            accountDatabase.put(handleID, account);
            handleToID.put(handle, handleID);
            return handleID;
        }
        else{
            throw new IllegalHandleException("The Handle already exists in the databse");
        }





    }
    public Account removeAccount(int id){
        // need to add removing posts from account

        accountDatabase.remove(id);
        return null;
    }
    public ArrayList getPostsForAccount(int id){
        return accountDatabase.get(id).getPosts();

    }
    public boolean uniqueHandle(String handle){
        //System.out.println(handle);
        //System.out.println(handleToID);
        //System.out.println(handleToID.containsKey(handle));
        if (handleToID.containsKey(handle)){
            return false;
        }
        return true;
    }
    public Integer AccountSize(){
        return accountDatabase.size();
    }
    public void addPostToAccount(String handle, int postID) throws IllegalHandleException {

        try {
            int accountID = handleToID.get(handle);
            addPostByID(accountID, postID);
        }
        catch (Exception e){
            System.out.println(e);
            throw new IllegalHandleException("The handle does not have a corresponding ID");
        }
    }

    public void addPostByID(int accountID, int postID){
        accountDatabase.get(accountID).addPost(postID);
    }

    public void updateAccountDescription(String handle, String description) throws IllegalHandleException {
        int accountID = handleToID.get(handle);
        accountDatabase.get(accountID).setDescription(description);
    }
    public void updateHandle(String oldhandle, String newhandle) throws IllegalHandleException {
        if (oldhandle.equals(newhandle)) {
            throw new IllegalHandleException("The new handle is the same as the old handle");
        }else if (!uniqueHandle(newhandle)) {
            throw new IllegalHandleException("The new handle already exists in the database");
        }else {
        int accountID = handleToID.get(oldhandle);
        accountDatabase.get(accountID).setHandle(newhandle);
        handleToID.remove(oldhandle);
        handleToID.put(newhandle, accountID);
        }
    }
    public String getDescription(int id){
        return accountDatabase.get(id).getDescription();
    }
    public int getAccountID(String handle) throws HandleNotRecognisedException {
        try{
            return handleToID.get(handle);
        }
        catch (Exception e){
            System.out.println(e);
            throw new HandleNotRecognisedException("The handle does not have a corresponding ID");

        }
    }

    public int getAccountIDFromPostID(int postID) throws PostIDNotRecognisedException {
        for (Map.Entry<Integer, Account> entry : accountDatabase.entrySet()) {
            System.out.println("Account Posts: " + entry.getValue().getPosts());
            if (entry.getValue().getPosts().contains(postID)){

                return entry.getKey();
            }
        }
        throw new PostIDNotRecognisedException("The post ID does not have a corresponding account ID: " + postID);
    }


    public void addEndorsementToAccount(String handle, int endorsementID)  {
        int accountID = handleToID.get(handle);
        System.out.println("Account ID: " + accountID);
        System.out.println("Endorsement ID: " + endorsementID);
        accountDatabase.get(accountID).addEndorsement(endorsementID);
    }


    public int getAccountIDFromEndorsement(int postID) throws PostIDNotRecognisedException {
        for (Map.Entry<Integer, Account> entry : accountDatabase.entrySet()) {
            System.out.println("Account Endorsements: " + entry.getValue().getEndorsedAccounts());
            if (entry.getValue().getEndorsedAccounts().contains(postID)){

                return entry.getKey();
            }
        }
        throw new PostIDNotRecognisedException("The post ID does not have a corresponding account ID: " + postID);
    }


    public String getHandle(int id){
        return accountDatabase.get(id).getHandle();
    }

}
