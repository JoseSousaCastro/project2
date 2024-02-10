package aor.paj.project2.backend.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@XmlRootElement
public class Task {
    private String id;
    private String title;
    private String description;
    private int stateId;
    private int priority;
    private LocalDate creationDate;
    private LocalDate editionDate;
    private LocalDate limitDate;
    private static final int TODO = 100;
    private static final int DOING = 200;
    private static final int DONE = 300;
    private static final int LOWPRIORITY = 100;
    private static final int MEDIUMPRIORITY = 200;
    private static final int HIGHPRIORITY = 300;

    public Task() {
    }

    @XmlElement
    public String getId() {
        System.out.println("Task id: " + id);
        return id;
    }

    public void setId() {
        this.id = String.valueOf(System.currentTimeMillis());
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
    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        if (stateId == TODO) {
            this.stateId = TODO;
        } else if (stateId == DOING) {
            this.stateId = DOING;
        } else if (stateId == DONE) {
            this.stateId = DONE;
        }
    }

    @XmlElement
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        if (priority == LOWPRIORITY) {
            this.priority = LOWPRIORITY;
        } else if (priority == MEDIUMPRIORITY) {
            this.priority = MEDIUMPRIORITY;
        } else if (priority == HIGHPRIORITY) {
            this.priority = HIGHPRIORITY;
        }
    }

    @XmlElement
    public LocalDate getCreationDate() {
        return creationDate;
    }

    @XmlElement
    public LocalDate getLimitDate() {
        return limitDate;
    }

    @XmlElement
    public LocalDate getEditionDate() {
        return editionDate;
    }

    public void setEditionDate() {
        this.editionDate = LocalDate.now();
    }

    public void setCreationDate() {
        this.creationDate = LocalDate.now();
    }

    public void setLimitDate(LocalDate limitDate) {
        this.limitDate = limitDate;
    }
}

