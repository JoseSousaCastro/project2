package aor.paj.project2.backend.bean;

import aor.paj.project2.backend.dto.Task;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.Iterator;

@ApplicationScoped
public class TaskBean {

    public void newTask(Task task) {
        task.generateId();
        task.setInitialStateId();
        task.defineCreationDate();
    }

    public boolean editTask(Task task, ArrayList<Task> tasks) {
        boolean edited = false;

            for (Task a : tasks) {
                if (a.getId().equals(task.getId())) {
                    a.setTitle(task.getTitle());
                    a.setDescription(task.getDescription());
                    a.setPriority(task.getPriority());
                    a.editStateId(task.getStateId());
                    a.setLimitDate(task.getLimitDate());
                    a.setEditionDate();
                    edited = validateTask(a);
                }
            }

        return edited;
    }

    public boolean removeTask(String id, ArrayList<Task> tasks) {
        boolean removed = false;
        Iterator<Task> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            if (task.getId().equals(id)) {
                iterator.remove();
                removed = true;
            }
        }
        return removed;
    }

    public boolean validateTask(Task task) {
        boolean valid = false;
        if (!(task.getLimitDate().isBefore(task.getCreationDate())
                || task.getTitle().isBlank()
                || task.getDescription().isBlank()
                || (task.getPriority() != Task.LOWPRIORITY && task.getPriority() != Task.MEDIUMPRIORITY && task.getPriority() != Task.HIGHPRIORITY)
                || (task.getStateId() != Task.TODO && task.getStateId() != Task.DOING && task.getStateId() != Task.DONE)
            )){
            valid = true;
        }
        return valid;
    }
}

