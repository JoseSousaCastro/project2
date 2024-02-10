package aor.paj.project2.backend.service;

import aor.paj.project2.backend.bean.TaskBean;
import aor.paj.project2.backend.dto.Task;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/tasks")
public class TaskService {

    @Inject
    TaskBean taskBean;


    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Task> getTasks() {
        return taskBean.getTasks();
    }


    @POST
    @Path("/new")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newTask(Task temporaryTask) {
        System.out.println("Task: " + temporaryTask.getTitle() + " " + temporaryTask.getDescription() + " " + temporaryTask.getPriority() + " " + temporaryTask.getLimitDate());
        taskBean.createTask(temporaryTask);
        return Response.status(201).entity("Task created successfuly").build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTask(@PathParam("id") long id) {
        Task task = taskBean.getTask(id);
        if (task == null)
            return Response.status(200).entity("Task with this id is not found").build();

        return Response.status(200).entity(task).build();
    }


    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeTask(@QueryParam("id")int id) {
        boolean deleted = taskBean.removeTask(id);
        Response response;
        if (!deleted) {
            response = Response.status(200).entity("Task with this id is not found").build();
        } else {
            response = Response.status(200).entity("deleted").build();
        }

        return response;
    }


    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTask(Task task) {
        boolean updated = taskBean.updateTask(task);
        Response response;
        if (!updated) {
            response = Response.status(200).entity("Task with this id is not found").build();
        } else {
            response = Response.status(200).entity("updated").build();
        }
        return response;
    }
}