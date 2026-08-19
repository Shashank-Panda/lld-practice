package model.post;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import enums.Vote;
import model.Tag;
import model.User;

public class Question extends Post implements Votable {
    private String title;
    private Set<Tag> tags;
    private List<Answer> answers = new ArrayList<>();
    private Answer acceptedAnswer;
    private final VoteBox voteBox = new VoteBox();

    public Question(String title, String content, User author, Set<Tag> tags) {
        super(content, author);
        this.title = title;
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    public Answer getAcceptedAnswer() {
        return acceptedAnswer;
    }

    public void acceptAnswer(Answer answer) {
        this.acceptedAnswer = answer;
        answer.markAccepted();
    }

    @Override
    public void vote(User user, Vote voteType) {
        voteBox.vote(user, voteType);
    }

    @Override
    public int getVoteCount() {
        return voteBox.getVoteCount();
    }
}
