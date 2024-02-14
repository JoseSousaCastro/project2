
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
import java.util.ArrayList;
import java.util.Iterator;

@ApplicationScoped
public class TaskBean {
   // private final String filename = "tasks.json";
    private ArrayList<Task> tasks;

 /*public TaskBean() {
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
*/

   /* public void addTask(Task temporaryTask) {
        Task task = new Task();
        task.setId();
        task.setTitle(temporaryTask.getTitle());
        task.setDescription(temporaryTask.getDescription());
        task.setPriority(temporaryTask.getPriority());
        task.setInitialStateId();
        task.setCreationDate();
        task.setLimitDate(temporaryTask.getLimitDate());
        //tasks.add(task);
        //writeIntoJsonFile();
    }*/

    public Task getTask(String id) {
        Task task = null;
        boolean found = false;
        while (!found) {
            for (Task a : tasks) {
                if (a.getId().equals(id)) {
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

    public boolean removeTask(String id) {
        boolean removed = false;
        Iterator<Task> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            if (task.getId().equals(id)) {
                iterator.remove();
                removed = true;
                //writeIntoJsonFile();
            }
        }
        return removed;
    }

/*    public boolean updateTask(UserBean userBean, String username, String id, Task task) {
        System.out.println(" entrou no método updateTask");
        boolean updated = false;
        while (!updated) {
            System.out.println("entrou no while do updateTask" + username);
            for (Task a : userBean.getUserAndHisTasks(username)) {
                System.out.println("entrou no for do updateTask \n" + "a. getId = " + a.getId() + " id passado " + id);
                if (a.getId().equals(id)) {
                    a.setTitle(task.getTitle());
                    a.setDescription(task.getDescription());
                    a.setPriority(task.getPriority());
                    //a.setInitialStateId();
                    a.editStateId(task.getStateId());
                    a.setLimitDate(task.getLimitDate());
                    if (!validateTask(a)) {
                        updated = false;
                    } else {
                        updated = true;
                        userBean.writeIntoJsonFile();
                    }
                }
            }
        }
        return updated;
    }*/

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

    @Override
    public String toString() {
        return "TaskBean{" +
                "tasks=" + tasks +
                '}';
    }

    /* private void writeIntoJsonFile() {
        Jsonb jsonb = JsonbBuilder.create(new JsonbConfig().withFormatting(true));
        try {
            jsonb.toJson(tasks, new FileOutputStream(filename));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }*/

}

