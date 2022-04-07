package json;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class JsonHandler {


    public void parse(String fileName){
        JSONParser jsonParser = new JSONParser();
        Object object = null;
        try {
            object = jsonParser.parse(new FileReader("secret/secret.json"));
            JSONObject jsonObject = (JSONObject) object;
            Long secret = (Long) jsonObject.get("secret");
            System.out.println("here");
            System.out.println(secret);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
