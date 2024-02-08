package aor.paj.project2.backend.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Task {
    private long id;
    private String title;
    private String description;
    private int STATE_ID; // perceber se tem de ser static e final
    private int priority;
    public Task() {
    }
    public Task(int id, String title, String description, int priority) {
        this.id = System.currentTimeMillis(); 
        this.title = title;
        this.description= description;
        this.STATE_ID = 100;
        this.priority = priority;
    }
    @XmlElement
    public long getId() {
        return id;
    }
    @XmlElement
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    @XmlElement
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    @XmlElement
    public int getSTATE_ID() {
        return STATE_ID;
    }
    public void setSTATE_ID(int STATE_ID) {
        this.STATE_ID = STATE_ID;
    }
    @XmlElement
    public int getPriority() {
        return priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }

}

