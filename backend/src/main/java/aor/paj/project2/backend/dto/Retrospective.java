package aor.paj.project2.backend.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.time.LocalDate;
import java.util.ArrayList;

@XmlRootElement
public class Retrospective {
    @XmlElement
    private String id;
    @XmlElement
    private String title;
    @XmlElement
    private LocalDate date;
    @XmlElement
    private ArrayList<String> members;
    @XmlElement
    private int commentId;
    @XmlElement
    private ArrayList<String> strengths;
    @XmlElement
    private ArrayList<String> challenges;
    @XmlElement
    private ArrayList<String> improvements;
    @XmlElement
    private static final int STRENGTHS = 100;
    @XmlElement
    private static final int CHALLENGES = 200;
    @XmlElement
    private static final int IMPROVEMENTS = 300;

    public Retrospective() {
    }

    public Retrospective (String title, LocalDate date, ArrayList<String> members, ArrayList<String> strengthsComments, ArrayList<String> challengesComments, ArrayList<String> improvements) {
        this.title = title;
        this.date = date;
        this.members = members;
        this.strengths = strengthsComments;
        this.challenges = challengesComments;
        this.improvements = improvements;
    }

    public String getId() {
        return id;
    }
    public void setId() {
        this.id = String.valueOf(System.currentTimeMillis());
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public ArrayList<String> getMembers() {
        return members;
    }
    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }
    public int getCommentId() {
        return commentId;
    }
    public void setCommentId(int commentId) {
        if (commentId == STRENGTHS) {
            this.commentId = STRENGTHS;
        } else if (commentId == CHALLENGES) {
            this.commentId = CHALLENGES;
        } else if (commentId == IMPROVEMENTS) {
            this.commentId = IMPROVEMENTS;
        }
    }
    public ArrayList<String> getStrengths() {
        return strengths;
    }
    public void addStrength(String comment) {
        this.strengths.add(comment);
    }
    public ArrayList<String> getChallenges() {
        return challenges;
    }
    public void addChallenge(String comment) {
        this.challenges.add(comment);
    }
    public ArrayList<String> getImprovements() {
        return improvements;
    }
    public void addImprovement(String comment) {
        this.improvements.add(comment);
    }

}
