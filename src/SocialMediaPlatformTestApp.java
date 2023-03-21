import socialmedia.*;

import java.io.*;
import java.util.Scanner;
/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the SocialMediaPlatform interface -- note you will
 * want to increase these checks, and run it on your SocialMedia class (not the
 * BadSocialMedia class).
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class SocialMediaPlatformTestApp {

	/**
	 * Test method.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) throws InvalidPostException, IOException, ClassNotFoundException, PostIDNotRecognisedException, IllegalHandleException, InvalidHandleException, HandleNotRecognisedException, NotActionablePostException {
		System.out.println("The system compiled and started the execution...");

		SocialMediaPlatform platform = new socialmedia();

		//Scanner scanner = new Scanner(System.in);


		//postDatabase.iteratePosts();
		platform.loadPlatform("testfile.ser");
		platform.commentPost("Handle1New", 1, "Comment to post1");
		System.out.println(platform.showIndividualPost(1));
		int handle1 = platform.createAccount("Handle5");
		int handle2 = platform.createAccount("Handle26");
		int handle3 = platform.createAccount("Handle36");

		//int post1 = platform.createPost("Handle1", "Message1");
		//int post4 = platform.createPost("Handle1", "Message4");
		int post2 = platform.createPost("Handle2", "Message2");
		int post3 = platform.createPost("Handle3", "Message3");

		//platform.updateAccountDescription("Handle1", "This is the description of Handle1");
		//platform.changeAccountHandle("Handle1", "Handle1New");
		//System.out.println(platform.showAccount("Handle1New"));
		//System.out.println(platform.showIndividualPost(post4));
		int comment1 = platform.commentPost("Handle2", 1, "Comment to post4");
		int comment1comment1 = platform.commentPost("Handle3", comment1, "Comment to comment1");
		System.out.println("Endorsing now");
		int endorsepost1 = platform.endorsePost("Handle3", post2);
		int endorsepost2 = platform.endorsePost("Handle2", post2);
		//System.out.println(platform.showPostChildrenDetails(post4));
		System.out.println(platform.getMostEndorsedPost());

		System.out.println("End Endorse Test");
		platform.savePlatform("testfile.ser");




		//System.out.println(platform.getTotalOriginalPosts());
		//System.out.println(platform.getTotalCommentPosts());
		//System.out.println(platform.getTotalEndorsmentPosts());


		assert (platform.getNumberOfAccounts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalOriginalPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalCommentPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalEndorsmentPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";

		Integer id;
		try {
			id = platform.createAccount("my_handle");
			assert (platform.getNumberOfAccounts() == 1) : "number of accounts registered in the system does not match";

			platform.removeAccount(id);
			assert (platform.getNumberOfAccounts() == 0) : "number of accounts registered in the system does not match";

		} catch (IllegalHandleException e) {
			assert (false) : "IllegalHandleException thrown incorrectly";
		} catch (InvalidHandleException e) {
			assert (false) : "InvalidHandleException thrown incorrectly";
		} catch (AccountIDNotRecognisedException e) {
			assert (false) : "AccountIDNotRecognizedException thrown incorrectly";
		}

	}
	// class that runs the functions imported
	public static void run() {
		System.out.println("The system compiled and started the execution...");

		SocialMediaPlatform platform = new BadSocialMedia();

		// test the number of accounts
		assert (platform.getNumberOfAccounts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalOriginalPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalCommentPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalEndorsmentPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";

		//test pushing a post



	}

	private static void serializeDatabase(PostDatabase postDatabase) throws IOException {
		try {
			FileOutputStream file = new FileOutputStream("postDatabase.ser");
			ObjectOutputStream out = new ObjectOutputStream(file);

			out.writeObject(postDatabase);
			out.close();
			file.close();
			System.out.println("Object has been serialized");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	private static PostDatabase getDatabase() throws IOException, ClassNotFoundException, InvalidPostException {
		try{
			FileInputStream file = new FileInputStream("postDatabase.ser");
			ObjectInputStream in = new ObjectInputStream(file);
			PostDatabase postDatabase = (PostDatabase) in.readObject();

			in.close();
			file.close();

			return postDatabase;
		}
		catch (FileNotFoundException e){
			System.out.println("File not found, Creating new database");
			return new PostDatabase();
		}


	}
}

