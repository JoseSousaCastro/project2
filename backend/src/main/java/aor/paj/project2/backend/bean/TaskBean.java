package aor.paj.project2.backend.bean;

import aor.paj.project2.backend.dto.Task;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;

@ApplicationScoped
public class TaskBean {
    final String filename = "tasks.json";
    private ArrayList<Task> tasks;

    public TaskBean() {
        File f = new File(filename);
        if (f.exists()) {
            try {
                FileReader filereader = new FileReader(f);
                tasks = JsonbBuilder.create().fromJson(filereader, new ArrayList<Task>() {}.getClass().getGenericSuperclass());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else
            tasks = new ArrayList<Task>();
    }

    public void addTask(Task task) {
        tasks.add(task);
        writeIntoJsonFile();
    }

    public Task getTask(long id) {
        Task task = null;
        boolean found = false;
        while (!found) {
            for (Task a : tasks) {
                if (a.getId() == id) {
                    task = a;
                    found = true;
                }
            }
        }
        return task;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public boolean removeTask(long id) {
        boolean removed = false;
        while (!removed) {
            for (Task a : tasks) {
                if (a.getId() == id) {
                    tasks.remove(a);
                    writeIntoJsonFile();
                    removed = true;
                }
            }
        }
        return removed;
    }

    public boolean updateTask(Task task) {
        boolean updated = false;
        while (!updated) {
            for (Task a : tasks) {
                if (a.getId() == task.getId()) {
                    a.setTitle(task.getTitle());
                    a.setDescription(task.getDescription());
                    a.setPriority(task.getPriority());
                    a.setSTATE_ID(task.getSTATE_ID());
                    String limitDateString = task.getLimitDate().toString();
                    a.setLimitDate(limitDateString);
                    LocalDate limitDate = a.getLimitDate();
                    if (limitDate.isBefore(a.getCreationDate()) || a.getTitle().isEmpty() || a.getDescription().isEmpty()) {
                        updated = false;
                    } else {
                        updated = true;
                        writeIntoJsonFile();
                    }
                }
            }
        }
        return updated;
    }


    private void writeIntoJsonFile() {
        Jsonb jsonb = JsonbBuilder.create(new
                JsonbConfig().withFormatting(true));
        try {
            jsonb.toJson(tasks, new FileOutputStream(filename));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}