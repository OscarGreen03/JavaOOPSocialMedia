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
		/*
		//Scanner scanner = new Scanner(System.in);


		//postDatabase.iteratePosts();
		//platform.loadPlatform("testfile.ser");
		//platform.commentPost("Handle1New", 1, "Comment to post1");
		//System.out.println(platform.showIndividualPost(1));
		*/



		//platform.erasePlatform();
		int user1 = platform.createAccount("user1");
		int post1 = platform.createPost("user1", "I like examples.");

		int user2 = platform.createAccount("user2");
		int post1comment1 = platform.commentPost("user2", post1, "No more than me...");
		int post1endorse1 = platform.endorsePost("user2", post1);
		int post1comment1comment1 = platform.commentPost("user1", post1comment1, "I can prove!");
		int post1comment1comment1comment1 = platform.commentPost("user2", post1comment1comment1, "prove it");
		int post1comment1comment1comment1comment1 = platform.commentPost("user1", post1comment1comment1comment1, "Okay, here's the proof");
		int post1comment1comment1comment1comment1comment1 = platform.commentPost("user2", post1comment1comment1comment1comment1, "Interesting, tell me more");

		int user3 = platform.createAccount("user3");
		int post2 = platform.createPost("user3", "I have a question.");
		int post2comment1 = platform.commentPost("user2", post2, "What's your question?");
		int post2comment1comment1 = platform.commentPost("user3", post2comment1, "My question is about programming languages.");
		int post2comment1comment1comment1 = platform.commentPost("user1", post2comment1comment1, "What do you want to know?");
		int post2comment1comment1comment1comment1 = platform.commentPost("user3", post2comment1comment1comment1, "Which programming language is the best?");

		int user4 = platform.createAccount("user4");
		int post3 = platform.createPost("user4", "I just finished my project!");


		int endorse1 = platform.endorsePost("user1", post1);
		int endorse2 = platform.endorsePost("user1", post1);



		//System.out.println(platform.showPostChildrenDetails(post1));
		//System.out.println(platform.showIndividualPost(post1comment1comment1comment1));
		//platform.deletePost(post1comment1comment1comment1);
		System.out.println(platform.showPostChildrenDetails(post1));
		System.out.println(endorse2);
		platform.deletePost(endorse2);
		System.out.println(platform.showPostChildrenDetails(post1));




		/*System.out.println("==================================");
		platform.endorsePost("user1", post1comment1comment1comment1);
		platform.endorsePost("user2", post1comment1comment1comment1);
		platform.endorsePost("user3", post1comment1comment1comment1);
		platform.endorsePost("user4", post1comment1comment1comment1);
		System.out.println(user2);
		System.out.println(platform.getMostEndorsedAccount());

		platform.savePlatform("test2.ser");*/




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
	public static void run() throws InvalidPostException {
		System.out.println("The system compiled and started the execution...");

		SocialMediaPlatform platform = new socialmedia();

		// test the number of accounts
		assert (platform.getNumberOfAccounts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalOriginalPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalCommentPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalEndorsmentPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";

		//test pushing a post



	}


}

