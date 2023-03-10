package socialmedia;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class AccountDatabase implements Serializable {
    @Serial
    private static final long serialVersionUID = 983484;
    public Map<Integer, Account> accountDatabase = new HashMap<>();

    public boolean addAccount(Account account) {
        // generate new account id
        // add account to map
        int accountID = generateAccountID();
        try {
            accountDatabase.put(accountID, account);
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

        int lastKey = 0;
        for (Map.Entry<Integer, Account> entry : accountDatabase.entrySet()) {
            lastKey = entry.getKey();
        }
        return lastKey + 1;

    }
    public Integer createAccount(String handle) throws IllegalHandleException {
        if (validateHandle(handle)){
            int handleID = generateAccountID();
            Account account = new Account(handle, handleID);
            accountDatabase.put(handleID, account);
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
}
