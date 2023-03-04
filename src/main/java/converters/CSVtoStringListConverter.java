package converters;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class CSVtoStringListConverter {
    private Path varPath;

    public Path getVarAddress() {
        return varPath;
    }

    public List<String[]> convertCSVtoStringList() throws IOException, CsvException {
        String fileName = "FILE";
        InputStreamReader reader;
        if (openFileWithPathFromEnv(fileName).isPresent()) {
            reader = new InputStreamReader(new FileInputStream(openFileWithPathFromEnv(fileName).get()));
            CSVReader csvReader = new CSVReader(reader);
            return csvReader.readAll();
        }
        throw new FileNotFoundException(fileName);
    }

    public Optional<File> openFileWithPathFromEnv(String fileName) {
        String path = System.getenv(fileName);
        if (path != null) {
            varPath = Path.of(path);
            return Optional.of(new File(path));
        } else {
            return Optional.empty();
        }

    }
}

