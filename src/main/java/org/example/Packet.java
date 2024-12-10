package org.example;

import java.io.*;
import java.util.ArrayList;

public class Packet implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    ArrayList<ArrayList<?>> database;

    public Packet() {
        this.database = new ArrayList<>();
    }

    public void addDatabase(ArrayList<?> database) {
        this.database.add(database);
    }

    public void readAll(){
        for(ArrayList<?> db: database){
            for(var data: db){
                System.out.println(data.toString());
            }
            System.out.println("\n");
        }
    }

    public ArrayList<ArrayList<?>> getDatabase() {
        return database;
    }



    public void serialize(ArrayList<?> obj) throws FileNotFoundException {
        // Serialize the object
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("src/main/java/org/example/packet.csv");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(database);
            objectOutputStream.flush();
            objectOutputStream.close();

        } catch (IOException e ){
            e.getCause();
        }
    }

    @SuppressWarnings("unchecked")
    public void deserialize() throws FileNotFoundException{
        // Deserialize the object
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("src/main/java/org/example/packet.csv");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            database = (ArrayList<ArrayList<?>>) objectInputStream.readObject();

            objectInputStream.close();
        } catch (IOException e) {
            e.getCause();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
