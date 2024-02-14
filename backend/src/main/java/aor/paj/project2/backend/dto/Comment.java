package aor.paj.project2.backend.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class Comment {
    @XmlElement
    private String id;
    @XmlElement
    private String description;
    @XmlElement
    private String user;
    @XmlElement
    private int commentId;
    @XmlElement
    private static final int STRENGTHS = 100;
    @XmlElement
    private static final int CHALLENGES = 200;
    @XmlElement
    private static final int IMPROVEMENTS = 300;


    public Comment() {
    }

    public String getId() {
        return id;
    }
    public void setId() {
        this.id = String.valueOf(System.currentTimeMillis());
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public int getCommentId() {
        return commentId;
    }
    public void setCommentId(int stateId) {
        if (stateId == STRENGTHS) {
            this.commentId = STRENGTHS;
        } else if (stateId == CHALLENGES) {
            this.commentId = CHALLENGES;
        } else if (stateId == IMPROVEMENTS) {
            this.commentId = IMPROVEMENTS;
        }
    }
}