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
    private int stateId;
    private int priority;
    private LocalDate creationDate;
    private LocalDate limitDate;

    public Task() {
    }
    public Task(String title, String description, String priority, String limitDate) {
        this.id = System.currentTimeMillis(); 
        this.title = title;
        this.description= description;
        this.stateId = setStateId("todo");
        this.creationDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        this.limitDate = LocalDate.parse(limitDate, formatter);
        this.priority = setPriority(priority);
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
    public int getStateId() {
        return stateId;
    }
    public int setStateId (String stateId) {
        final int TODO = 100; // perceber se tem de ser static e final
        final int DOING = 200;
        final int DONE = 300;
        int newStateId = -1;
        if (stateId.equalsIgnoreCase("todo")) {
            newStateId = TODO;
        } else if (stateId.equalsIgnoreCase("doing")) {
            newStateId = DOING;
        } else if (stateId.equalsIgnoreCase("done")) {
            newStateId = DONE;
        }
        return newStateId;
    }
    // converte o estado de int para string, para ser usada no método updateTask
    public String stateIdString (int stateId) {
        String stateIdString = "";
        if (stateId == 100) {
            stateIdString = "todo";
        } else if (stateId == 200) {
            stateIdString = "doing";
        } else if (stateId == 300) {
            stateIdString = "done";
        }
        return stateIdString;
    }
    @XmlElement
    public int getPriority() {
        return priority;
    }
    // converte a prioridade de string para int, para não permitir que user insira um valor que não seja low, medium ou high
    // low = 100, medium = 200, high = 300 são valores constantes, apenas definidos para serem usados aqui
    public int setPriority(String priority) {
        final int lowPriority = 100;
        final int mediumPriority = 200;
        final int highPriority = 300;
        int newPriority = -1;
        if (priority.equalsIgnoreCase("low")) {
            newPriority = lowPriority;
        } else if (priority.equalsIgnoreCase("medium")) {
            newPriority = mediumPriority;
        } else if (priority.equalsIgnoreCase("high")) {
            newPriority = highPriority;
        }
        return newPriority;

    }
    // converte a prioridade de int para string, para ser usada no método updateTask
    public String priorityString (int priority) {
        String priorityString = "";
        if (priority == 100) {
            priorityString = "low";
        } else if (priority == 200) {
            priorityString = "medium";
        } else if (priority == 300) {
            priorityString = "high";
        }
        return priorityString;
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

    public void setId(long id) {
        this.id = id;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setLimitDate(LocalDate limitDate) {
        this.limitDate = limitDate;
    }
}

