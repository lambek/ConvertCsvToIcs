package create_google_documents;

import java.io.FileWriter;
import java.io.IOException;

public interface ConvectrCsv {
    /**
     *
     * @param path
     */
    public void parserCsv(String path);

    /**
     *
     * @return
     */
    public String preparedData();

    /**
     *
     * @param path
     */
    default void creatFile(String path) {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(this.preparedData());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
