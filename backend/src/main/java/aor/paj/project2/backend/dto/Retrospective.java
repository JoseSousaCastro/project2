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
    private ArrayList<Comment> retrospectiveComments = new ArrayList<>();


    public Retrospective() {
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
    public void addComment(Comment comment) {
        retrospectiveComments.add(comment);
    }
    public ArrayList<Comment> getRetrospectiveComments() {
        return retrospectiveComments;
    }
}
