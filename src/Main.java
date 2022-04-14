import algorithm.Splitter;
import json.JsonHandler;
import secret.ShamirSecret;

import java.math.BigInteger;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Splitter splitter = new Splitter(7,4);
        List<ShamirSecret> shamirSecrets = splitter.splitIntoPieces(123);
        ShamirSecret testSecret = new ShamirSecret(1,BigInteger.valueOf(123456));
        JsonHandler handler = new JsonHandler();
        handler.writeSecretPartToJson(testSecret,BigInteger.valueOf(98914),"parts/part.json");
    }
}
