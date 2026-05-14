package Framework;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Handles serialization and deserialization of objects.
 * 
 * This can be used for save files, high scores or saved game states.
 */
public class SerializationManager {

    /**
     * Serializes an object and saves it to a file.
     * 
     * The object must implement Serializable.
     *
     * @param object the object to save
     * @param outputFilePath the file path to save to
     * @param <T> the type of object
     */
    public <T extends Serializable> void serialize(T object, String outputFilePath) {
        try {
            FileOutputStream file = new FileOutputStream(outputFilePath);
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(object);

            out.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserializes an object from a file.
     *
     * @param filePath the file path to load from
     * @return the loaded object, or null if loading failed
     */
    public Object deserialize(String filePath) {
        Object object = null;

        try {
            FileInputStream file = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(file);

            object = in.readObject();

            in.close();
            file.close();
        } catch (IOException | ClassNotFoundException e) {
            if (!(e instanceof FileNotFoundException)) {
                e.printStackTrace();
            }
        }

        return object;
    }
}