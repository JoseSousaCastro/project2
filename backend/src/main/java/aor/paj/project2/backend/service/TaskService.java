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
    public List<Task> getActivities() {
        return taskBean.getTasks();
    }


    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTask(Task a) {
        taskBean.addTask(a);
        return Response.status(200).entity("A new task is created").build();

    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTask(@PathParam("id")int id) {
        Task task = taskBean.getTask(id);
        if (task==null)
            return Response.status(200).entity("Task with this id is not found").build();

        return Response.status(200).entity(task).build();

    }


    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeTask(@QueryParam("id")int id) {
        boolean deleted = taskBean.removeTask(id);
        if (!deleted)
            return Response.status(200).entity("Task with this id is not found").build();

        return Response.status(200).entity("deleted").build();
    }


    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTask(Task a, @HeaderParam("id") int id) {
        boolean updated = taskBean.updateTask(id, a);
        if (!updated)
            return Response.status(200).entity("Task with this id is not found").build();

        return Response.status(200).entity("Task updated").build();
    }
}