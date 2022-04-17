package json;

import exception.FileFormatException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import secret.ShamirSecret;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class JsonHandler {
    private BigInteger secret;
    private Optional<Long> primeNumber;
    private Long parts;
    private String PARTS_PATH = "parts/";
    //"parts/part.json";
    public BigInteger getSecret() {
        return secret;
    }

    public Optional<Long> getPrimeNumber() {
        return primeNumber;
    }

    public Long getParts() {
        return parts;
    }

    public void parse(String fileName){
        JSONParser jsonParser = new JSONParser();
        Object object = null;
        try {
            object = jsonParser.parse(new FileReader(fileName));
            JSONObject jsonObject = (JSONObject) object;
            secret = BigInteger.valueOf((Long) jsonObject.get("secret"));
            primeNumber = Optional.ofNullable( (Long) jsonObject.get("P"));
            parts = (Long)jsonObject.get("parts");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }

    public void writeSecretPartToJson(List<ShamirSecret> parts,BigInteger primeNumber){
        clearDirectory();
        int partNumber = 1;
        for (ShamirSecret part : parts) {
            JSONObject secret = new JSONObject();
            secret.put("Value",part.getSecret());
            secret.put("Point",part.getPart());
            secret.put("P",primeNumber);
            write(secret,PARTS_PATH + partNumber + "part.json");
            ++partNumber;
        }


    }
    private void write(JSONObject part,String path){
        try (FileWriter file = new FileWriter(path)) {
            file.write(part.toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearDirectory(){
        File folder = new File("parts");
        File[] files = folder.listFiles();
        if(files!=null) { //some JVMs return null for empty dirs
            for(File f: files) {
                f.delete();
            }
        }

    }

    public void validateFileKeySet(String fileName){
        JSONParser jsonParser = new JSONParser();
        Set<String> expectedKeySet = Set.of("P","Value","Point");
        Object object = null;
        try {
            object = jsonParser.parse(new FileReader(fileName));
            JSONObject jsonObject = (JSONObject) object;
            Collection values = jsonObject.values();
            Set<String> jsonSet = jsonObject.keySet();
            jsonSet.retainAll(expectedKeySet);
            if(jsonSet.size() != 3)
                throw new FileFormatException("inappropriate file format");

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }


}
