package json;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import secret.ShamirSecret;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public class JsonHandler {
    private BigInteger secret;
    private Optional<Long> primeNumber;

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

    public void writeSecretPartToJson(ShamirSecret part,BigInteger primeNumber, String path){
        JSONObject secret = new JSONObject();
        secret.put("point",part.getPart());
        secret.put("value",part.getSecret());
        secret.put("P",primeNumber);
        try (FileWriter file = new FileWriter(path)) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(secret.toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
