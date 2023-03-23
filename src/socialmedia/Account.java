package socialmedia;

import java.io.Serializable;
import java.util.ArrayList;

public class Account implements Serializable {
    private String handle;
    private String description;
    private int id;
    private ArrayList<Integer> posts = new ArrayList<Integer>();

    private ArrayList<Integer> endorsedAccounts = new ArrayList<Integer>();

    public Account(String handle, String description) {
        this.handle = handle;
        this.description = description;
        this.id = id;

    }

    public Account(String handle) {
        this.handle = handle;
        this.id = id;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addPost(int id){
        posts.add(id);
    }

    public ArrayList<Integer> getPosts() {
        return this.posts;
    }

    public void addEndorsement(int id){
        endorsedAccounts.add(id);
    }
    public ArrayList<Integer> getEndorsedAccounts(){

        return this.endorsedAccounts;
    }

}
