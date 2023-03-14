package socialmedia;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class AccountDatabase implements Serializable {
    @Serial
    private static final long serialVersionUID = 983484;
    private Map<Integer, Account> accountDatabase = new HashMap<>();
    private Integer lastID = 0;
    private Map<String, Integer> handleToID = new HashMap<>();

    public boolean addAccount(Account account) {
        // generate new account id
        // add account to map
        int accountID = generateAccountID();
        try {
            accountDatabase.put(accountID, account);
            handleToID.put(account.getHandle(), accountID);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
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
        if (validateHandle(handle)){
            int handleID = generateAccountID();
            Account account = new Account(handle, handleID);
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
    public boolean validateHandle(String handle){
        for (Map.Entry<Integer, Account> entry : accountDatabase.entrySet()) {
            if (entry.getValue().getHandle().equals(handle)){
                return false;
            }
        }
        return true;
    }
    public Integer AccountSize(){
        return accountDatabase.size();
    }
    public void addPostToAccount(String handle, int postID) throws IllegalHandleException {
        int accountID = handleToID.get(handle);
        addPostByID(accountID, postID);

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
        }else if (!validateHandle(newhandle)) {
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
        try{return handleToID.get(handle);}
        catch (Exception e){
            throw new HandleNotRecognisedException("The handle does not have a corresponding ID");
        }
    }

}
