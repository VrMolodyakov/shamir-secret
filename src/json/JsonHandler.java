package json;

import exception.FileFormatException;
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

    public void writeSecretPartToJson(List<ShamirSecret> parts){
        clearDirectory();
        int partNumber = 1;
        for (ShamirSecret part : parts) {
            JSONObject secret = new JSONObject();
            secret.put("Value",part.getSecret());
            secret.put("Point",part.getPart());
            secret.put("P",part.getPrimeNumber());
            write(secret,PARTS_PATH + partNumber + "part.json");
            ++partNumber;
        }


    }

    public void writeCombinedSecret(BigInteger rebuildSecret,BigInteger primeNumber){
        JSONObject secret = new JSONObject();
        secret.put("Secret",rebuildSecret);
        secret.put("P",primeNumber);
        write(secret,"secret/" + "rebuildSecret.json");
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

    public void validateFileFormat(String fileName){
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

    public List<ShamirSecret> getSecretPartsFromFiles(List<File> files){
        List<ShamirSecret> secretList = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        Object object = null;
        try {
            for (File file : files) {
                validateFileFormat(PARTS_PATH + file.getName());
                object = jsonParser.parse(new FileReader(PARTS_PATH + file.getName()));
                JSONObject jsonObject = (JSONObject) object;
                //BigInteger.valueOf((Long) jsonObject.get("secret"));
                BigInteger value = BigInteger.valueOf((Long) jsonObject.get("Value"));
                BigInteger primeNumber = BigInteger.valueOf((Long) jsonObject.get("P"));
                Long point = (Long) jsonObject.get("Point");
                secretList.add(new ShamirSecret(point,value,primeNumber));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return secretList;
    }
}
