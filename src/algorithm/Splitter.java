package algorithm;

import json.JsonHandler;
import secret.ShamirSecret;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Splitter {
    private long parts;
    private int rebuildCount;
    private final String SECRET_FILE_PATH = "secret/secret.json";
    private final int RANDOM_CERTAINTY = 100;
    private final int RANDOM_BIT_LENGTH = 16;
    private BigInteger primeNumber;

    public Splitter(int rebuildCountl) {
        this.rebuildCount = rebuildCountl;
    }

    public List<ShamirSecret> splitIntoPieces(){
        JsonHandler jsonHandler = new JsonHandler();
        jsonHandler.parse(SECRET_FILE_PATH);
        BigInteger secret = jsonHandler.getSecret();
        Optional<Long> probablyPrimeNumber = jsonHandler.getPrimeNumber();
        parts = jsonHandler.getParts();
        if(probablyPrimeNumber.isPresent()){
            this.primeNumber = BigInteger.valueOf(jsonHandler.getPrimeNumber().get());
        }else{
            primeNumber = getRandomPrimeNumber(secret);
        }
        List<BigInteger> coefficents = new ArrayList<>();
        coefficents.add(secret);
        for (int i = 1; i < rebuildCount; i++) {
            coefficents.add(getRandomCoefficent(primeNumber));
        }
        List<ShamirSecret> parts = new ArrayList<>();
        for (int i = 1; i < this.parts; i++) {
            BigInteger part = secret;
            for (int j = 1; j < rebuildCount; j++) {
                part = part.add(coefficents.get(j).multiply(BigInteger.valueOf(i).pow(j))).mod(primeNumber);
            }
            parts.add(new ShamirSecret(i,part));
        }
        //Combiner combiner = new Combiner(rebuildCount);
        //combiner.combine(parts,primeNumber);
        jsonHandler.writeSecretPartToJson(parts,primeNumber);
        return parts;


    }

    private BigInteger getRandomPrimeNumber(BigInteger secret){
        int bitLength = secret.bitLength();
        BigInteger probablyRandom = new BigInteger(bitLength+1,RANDOM_CERTAINTY,new Random());
        while(!isPrime(probablyRandom) || probablyRandom.compareTo(secret) < 0){
            probablyRandom = new BigInteger(bitLength+1,RANDOM_CERTAINTY,new Random());
        }
        return probablyRandom;
    }

    private BigInteger getRandomCoefficent(BigInteger primeNumber){
        int bitLength = primeNumber.bitLength();
        BigInteger randomNumber = new BigInteger(bitLength,new Random());
        while(primeNumber.compareTo(BigInteger.ZERO)<=0 || randomNumber.compareTo(primeNumber) > 0){
            randomNumber = new BigInteger(bitLength,new Random());
        }
        return randomNumber;
    }

    private boolean isPrime(BigInteger probablyRandom){
        BigInteger iter = BigInteger.ZERO;
        while(iter.compareTo(probablyRandom.sqrt()) > 0){
            if(probablyRandom.divide(iter).equals(BigInteger.ZERO)){
                return false;
            }
        }
        return true;
    }
}
