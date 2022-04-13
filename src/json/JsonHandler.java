package json;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
