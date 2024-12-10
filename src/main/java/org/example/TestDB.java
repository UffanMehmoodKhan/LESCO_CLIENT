package org.example;

import java.io.*;
import java.util.ArrayList;

public class TestDB implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    protected static final String FILENAME = "src/main/java/org/example/test.csv";
    protected static final String RESULT = "src/main/java/org/example/result.csv";
    ArrayList<UserData> allData;

    public static class UserData implements Serializable {
        String name, uni, city;

        UserData(String name, String uni, String city){
            this.name = name;
            this.uni = uni;
            this.city = city;
        }

        @Override
        public String toString(){
            return this.name + "," + this.uni + "," + this.city;
        }
    }

    public TestDB(){
        allData = new ArrayList<>();
    }

    public String getFilename(){
        return FILENAME;
    }

    public String getResult(){
        return RESULT;
    }

    public void test(){
        try(BufferedReader br = new BufferedReader(new FileReader(FILENAME))){
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                UserData newData = new UserData(values[0], values[1], values[2]);
                allData.add(newData);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void read(){
        for (UserData d : allData) {
            System.out.println(d.name + " " + d.uni + " " + d.city);
        }
    }

    public ArrayList<UserData> getDatabase() {
        return this.allData;
    }

    public void SerializeDB(ArrayList<UserData> obj) throws FileNotFoundException {
        // Serialize the object
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(RESULT);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
            objectOutputStream.close();

        } catch (IOException e ){
            e.getCause();
        }
    }

    @SuppressWarnings("unchecked")
    public void DeserializeDB(String result) throws FileNotFoundException{
        // Deserialize the object
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(result);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ArrayList<UserData> obj = (ArrayList<UserData>) objectInputStream.readObject();
            for (UserData d : obj) {
                System.out.println(d.name + " " + d.uni + " " + d.city);
            }
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.getCause();
        }
    }


    public static void main(String[] args) throws FileNotFoundException {
        TestDB db = new TestDB();
        db.test();
        //db.read();
        ArrayList<UserData> database = db.getDatabase();
        db.SerializeDB(database);
        db.DeserializeDB(db.getResult());
    }


}
