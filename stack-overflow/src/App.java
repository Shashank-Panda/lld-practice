public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
    }
}


/*
Requirements:
1. Users can post questions, answer questions, and comment on questions and answers.
2. Users can vote on questions and answers.
3. Questions should have tags associated with them.
4. Users can search for questions based on keywords, tags, or user profiles.
5. The system should assign reputation score to users based on their activity and the quality of their contributions.
6. The system should handle concurrent access and ensure data consistency.

Entities:
1. User (list of posts (categorized by type), reputation score, list of votes, user id, username, email, password, user image, short summary)
2. Post (Question, Answer, Comment) 
3. Tags (could be anything after a #)


Reputation scoring:
1. flat points for posting anything (1 pt)
2. flat points for votes on posts (0.15 pt to the voter, +-0.25 pt to the post owner depending on the vote type)
3. flat points for accepted answers (15 pt to the answerer, 5 pt to the question asker)
4. Users should not be able to vote on their own posts.

Concurrency Handling:
Concurrency needs to be handled for the number of votes on a post, the number of posts by a user, and the reputation score of a user. 
This can be achieved by using synchronized methods or blocks in the relevant classes to ensure that only one thread can access the 
critical section of code at a time. Additionally, we can use concurrent data structures like ConcurrentHashMap to store the posts and 
votes to ensure thread safety.

Out of scope:
1. User authentication and authorization.
2. User profile management.
3. Notifications and messaging between users.
4. post editing and deletion.
*/