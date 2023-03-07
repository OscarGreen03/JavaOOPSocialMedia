package socialmedia;

public class Post {


    public Post(String userid, String message, String type) throws InvalidPostException {
        //print all inputs
        System.out.println("User ID: " + userid);
        System.out.println("Message: " + message);
        System.out.println("Type: " + type);

        if (message.length() > 100) {
            //raise InvalidPostException
            throw new InvalidPostException("Message is too long");
        }

        // main function takes in userid, message, what type of post it is (post, comment or endorsement (p,c,e))

    }
}

