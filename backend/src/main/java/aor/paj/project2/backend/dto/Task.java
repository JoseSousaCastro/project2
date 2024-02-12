package aor.paj.project2.backend.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.time.LocalDate;

@XmlRootElement
public class Task {
    @XmlElement
    private String id;
    @XmlElement
    private String title;
    @XmlElement
    private String description;
    @XmlElement
    private int stateId;
    @XmlElement
    private int priority;
    @XmlElement
    private LocalDate creationDate;
    @XmlElement
    private LocalDate editionDate;
    @XmlElement
    private LocalDate limitDate;
    @XmlElement
    private static final int TODO = 100;
    @XmlElement
    private static final int DOING = 200;
    @XmlElement
    private static final int DONE = 300;
    @XmlElement
    private static final int LOWPRIORITY = 100;
    @XmlElement
    private static final int MEDIUMPRIORITY = 200;
    @XmlElement
    private static final int HIGHPRIORITY = 300;

    public Task() {
    }

    public String getId() {
        System.out.println("Task id: " + id);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getLimitDate() {
        return limitDate;
    }

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