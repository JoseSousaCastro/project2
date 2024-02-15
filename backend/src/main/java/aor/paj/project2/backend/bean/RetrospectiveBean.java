package aor.paj.project2.backend.bean;

import aor.paj.project2.backend.dto.Retrospective;
import aor.paj.project2.backend.dto.Comment;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;

@ApplicationScoped
public class RetrospectiveBean {
    final String filename = "retrospectives.json";
    private ArrayList<Retrospective> retrospectives;

    public RetrospectiveBean() {
        File f = new File(filename);
        if (f.exists()) {
            try {
                FileReader filereader = new FileReader(f);
                retrospectives = JsonbBuilder.create().fromJson(filereader, new ArrayList<Retrospective>() {}.getClass().getGenericSuperclass());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else
            retrospectives = new ArrayList<Retrospective>();
    }
    public void addRetrospective(Retrospective retrospective) {
        retrospective.setId();
        retrospective.setTitle(retrospective.getTitle());
        retrospective.setDate(retrospective.getDate());
        retrospectives.add(retrospective);
        writeIntoJsonFile();
    }
    public Retrospective getRetrospective(String id) {
        Retrospective retrospective = null;
        boolean found = false;
        while (!found) {
            for (Retrospective a : retrospectives) {
                if (a.getId().equals(id)) {
                    retrospective = a;
                    found = true;
                }
            }
        }
        return retrospective;
    }
    public ArrayList<Retrospective> getRetrospectives() {
        return retrospectives;
    }
    public ArrayList<Comment> getComments(String id) {
        ArrayList<Comment> comment = null;
        for (Retrospective a : retrospectives) {
            if (a.getId().equals(id)) {
                comment = a.getRetrospectiveComments();
            }
        }
        return comment;
    }

    public Comment getComment(String id, String commentId) {
        Comment comment = null;
        for (Retrospective a : retrospectives) {
            if (a.getId().equals(id)) {
                for (Comment c : a.getRetrospectiveComments()) {
                    if (c.getId().equals(commentId)) {
                        comment = c;
                    }
                }
            }
        }
        return comment;
    }

    public boolean updateRetrospective(String id, Retrospective retrospective) {
        boolean updated = false;
        for (Retrospective a : retrospectives) {
            if (a.getId().equals(id)) {
                a.setTitle(retrospective.getTitle());
                a.setDate(retrospective.getDate());
                a.addComment(retrospective.getRetrospectiveComments().get(0));
                updated = true;
                writeIntoJsonFile();
            }
        }
        return updated;
    }

    public boolean updateComment(String id, String commentId, Comment comment) {
        boolean updated = false;
        for (Retrospective a : retrospectives) {
            if (a.getId().equals(id)) {
                for (Comment c : a.getRetrospectiveComments()) {
                    if (c.getId().equals(commentId)) {
                        c.setDescription(comment.getDescription());
                        updated = true;
                        writeIntoJsonFile();
                    }
                }
            }
        }
        return updated;
    }

    public boolean deleteRetrospective(String id) {
        boolean removed = retrospectives.removeIf(a -> a.getId().equals(id));
        if (removed) {
            writeIntoJsonFile();
        }
        return removed;
    }

    public boolean deleteAllComments(String id) {
    Retrospective retrospective = getRetrospective(id);
        if (retrospective != null) {
            retrospective.getRetrospectiveComments().clear();
            writeIntoJsonFile();
            return true;
        }
        return false;
    }

    public boolean deleteComment(String id, String commentId) {
        Retrospective retrospective = getRetrospective(id);
        if (retrospective != null) {
            boolean removed = retrospective.getRetrospectiveComments().removeIf(c -> c.getId().equals(commentId));
            if (removed) {
                writeIntoJsonFile();
            }
            return removed;
        }
        return false;
    }

    public void addCommentToRetrospective(String id, Comment comment) {
        for (Retrospective a : retrospectives) {
            if (a.getId().equals(id)) {
                a.addComment(comment);
                writeIntoJsonFile();
            }
        }
    }

    private void writeIntoJsonFile() {
        Jsonb jsonb = JsonbBuilder.create(new
                JsonbConfig().withFormatting(true));
        try {
            jsonb.toJson(retrospectives, new FileOutputStream(filename));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}