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
    private Retrospective retrospective;
    @XmlElement
    public static final int TODO = 100;
    @XmlElement
    public static final int DOING = 200;
    @XmlElement
    public static final int DONE = 300;
    @XmlElement
    public static final int LOWPRIORITY = 100;
    @XmlElement
    public static final int MEDIUMPRIORITY = 200;
    @XmlElement
    public static final int HIGHPRIORITY = 300;

    public Task() {
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void generateId() {
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
        this.stateId = stateId;
    }
    public void setInitialStateId() {
        this.stateId = TODO;
    }
    public void editStateId(int stateId) {
        /*if (stateId != TODO && stateId != DOING && stateId != DONE) {
            throw new IllegalArgumentException("Invalid stateId");
        } else {*/
            if (stateId == TODO) {
                this.stateId = TODO;
            } else if (stateId == DOING) {
                this.stateId = DOING;
            } else {
                this.stateId = DONE;
            }
        //}
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
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
    public void defineCreationDate() {
        this.creationDate = LocalDate.now();
    }

    public LocalDate getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(LocalDate limitDate) {
        this.limitDate = limitDate;
    }

    public LocalDate getEditionDate() {
        return editionDate;
    }

    public void setEditionDate() {
        this.editionDate = LocalDate.now();
    }


    public void addRetrospective(Retrospective retrospective) {
        if (stateId == DONE) {
            this.retrospective = retrospective;
        }
    }
    public Retrospective getRetrospective() {
        return retrospective;
    }

  @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", stateId=" + stateId +
                ", priority=" + priority +
                ", creationDate=" + creationDate +
                ", editionDate=" + editionDate +
                ", limitDate=" + limitDate +
                '}';
    }
}