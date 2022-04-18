import algorithm.Combiner;
import algorithm.Splitter;
import json.JsonHandler;
import secret.ShamirSecret;

import java.io.File;
import java.math.BigInteger;
import java.util.*;

public class Main {
    private static int REBUILD_COUNT = 4;
    public static void main(String[] args) {

        //Splitter splitter = new Splitter(REBUILD_COUNT);
        //splitter.splitIntoPieces();
        //List<ShamirSecret> shamirSecrets = splitter.splitIntoPieces(123);
        //handler.validateFile("parts/1part.json");
        //handler.writeSecretPartToJson(testSecret,BigInteger.valueOf(98914),"parts/part.json");

        /*JsonHandler handler = new JsonHandler();
        handler.clearDirectory();*/


        Splitter splitter = new Splitter(REBUILD_COUNT);
        splitter.splitIntoPieces();
        List<File> parts = getRandomSecretParts("parts", 4);
        Combiner combiner = new Combiner(parts);
        combiner.combine();
    }

    private static List<File> getRandomSecretParts(String folderPath, int partCount){
        File[] files = new File(folderPath).listFiles();
        Set<Integer> parts = new HashSet<>();
        List<File> partsList = new ArrayList<>();
        while(parts.size() != partCount){
            Random rand = new Random();
            parts.add(rand.nextInt(files.length));
        }
        for (Integer part : parts) {
            partsList.add(files[part]);
        }
        return partsList;

    }

}
