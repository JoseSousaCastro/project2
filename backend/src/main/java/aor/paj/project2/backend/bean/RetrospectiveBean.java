package aor.paj.project2.backend.bean;

import aor.paj.project2.backend.dto.Retrospective;
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