package json;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import secret.ShamirSecret;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public class JsonHandler {
    private BigInteger secret;
    private Optional<Long> primeNumber;
    String PARTS_PATH = "parts/";
    //"parts/part.json";
    public BigInteger getSecret() {
        return secret;
    }

    public Optional<Long> getPrimeNumber() {
        return primeNumber;
    }

    public void parse(String fileName){
        JSONParser jsonParser = new JSONParser();
        Object object = null;
        try {
            object = jsonParser.parse(new FileReader(fileName));
            JSONObject jsonObject = (JSONObject) object;
            secret = BigInteger.valueOf((Long) jsonObject.get("secret"));
            primeNumber = Optional.ofNullable( (Long) jsonObject.get("P"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }

    public void writeSecretPartToJson(List<ShamirSecret> parts,BigInteger primeNumber){
        clearDirectory();
        int partNumber = 1;
        for (ShamirSecret part : parts) {
            JSONObject secret = new JSONObject();
            secret.put("point",part.getPart());
            secret.put("value",part.getSecret());
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

}
