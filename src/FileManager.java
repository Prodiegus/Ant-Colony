package src;
/**
 * In this class we will stract the data line by line from the file and we will save it in a list for later use
 */

import java.util.ArrayList;

public class FileManager {
    String address;
    public FileManager(String address){
        this.address = address;
    }
    /**
     * This method will read the file and save the data in a list
     * @return a list with the data of the file
     */
    public ArrayList<String> readFile(){
        ArrayList<String> data = new ArrayList<String>();
        try {
            java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(address));
            String line = br.readLine();
            while (line != null) {
                data.add(line);
                line = br.readLine();
            }
            br.close();
        } catch (Exception e) {
            System.err.println("Error reading file: "+address);
            System.err.println(e);
        }
        return data;
    }    
}
