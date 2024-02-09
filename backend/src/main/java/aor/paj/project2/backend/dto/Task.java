package aor.paj.project2.backend.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@XmlRootElement
public class Task {
    private long id;
    private String title;
    private String description;
    private int STATE_ID;
    private int priority;
    private LocalDate creationDate;
    private LocalDate limitDate;
    private final int lowPriority = 100;
    private final int mediumPriority = 200;
    private final int highPriority = 300;
    private final int TODO = 100; // perceber se tem de ser static e final
    private final int DOING = 200;
    private final int DONE = 300;
    public Task() {
    }
    public Task(String title, String description, String priority, String limitDate) {
        this.id = System.currentTimeMillis(); 
        this.title = title;
        this.description= description;
        this.STATE_ID = TODO;
        this.creationDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        this.limitDate = LocalDate.parse(limitDate, formatter);
        if (priority.equalsIgnoreCase("low")) {
            this.priority = lowPriority;
        } else if (priority.equalsIgnoreCase("medium")) {
            this.priority = mediumPriority;
        } else if (priority.equalsIgnoreCase("high")) {
            this.priority = highPriority;
        }
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
    @XmlElement
    public LocalDate getCreationDate() {
        return creationDate;
    }
    @XmlElement
    public LocalDate getLimitDate() {
        return limitDate;
    }
    public void setLimitDate(String limitDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        this.limitDate = LocalDate.parse(limitDate, formatter);
    }
}

