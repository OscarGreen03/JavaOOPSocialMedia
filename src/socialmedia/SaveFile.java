package socialmedia;

//import java.io.Serial;
import java.io.Serializable;

public class SaveFile implements Serializable {
    // needed to stop a random serial being generated, using random serial for now
    //@Serial
    private static final long serialVersionUID = -839483944;

    private AccountDatabase accountDatabase;
    private PostDatabase postDatabase;

    public SaveFile(AccountDatabase accountDatabase, PostDatabase postDatabase){
        this.accountDatabase = accountDatabase;
        this.postDatabase = postDatabase;
    }

    public AccountDatabase getAccountDatabase() {
        return accountDatabase;
    }
    public PostDatabase getPostDatabase() {
        return postDatabase;
    }
}
