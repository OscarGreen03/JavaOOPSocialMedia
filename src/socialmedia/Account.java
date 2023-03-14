package socialmedia;

import java.util.ArrayList;

public class Account {
    private String handle;
    private String description;
    private int id;
    private ArrayList<Integer> posts = new ArrayList<>();

    private ArrayList<Integer> endorsedAccounts = new ArrayList<>();

    public Account(String handle, String description, int id) {
        this.handle = handle;
        this.description = description;
        this.id = id;
    }

    public Account(String handle, int id) {
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
