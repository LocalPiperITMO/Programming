package converters;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class CSVtoStringListConverter {
    /**
     * Converter class
     * Used to convert text from CSV-file into list of arguments
     */
    private Path varPath;

    /**
     * Used for getting the path of file
     *
     * @return file path
     */
    public Path getVarAddress() {
        return varPath;
    }

    /**
     * Used for converting CSV into list of arguments
     *
     * @return list of arguments
     * @throws IOException  if unexpected error occurs
     * @throws CsvException if error related to CSV occurs
     */
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

    /**
     * Takes environment variable name and gets the file with that name
     *
     * @param fileName environment variable name
     * @return file if found, empty object otherwise
     */
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

