package algorithm;

import exception.PartsValidator;
import json.JsonHandler;
import secret.ShamirSecret;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SecretCombiner {
    private List<File> partsList;
    private int rebuildCount;

    public SecretCombiner(int rebuildCount){
        this.rebuildCount = rebuildCount;
    }

    private SecretCombiner(List<File> partsList){
        this.partsList = partsList;
    }

    public BigInteger combine(List<ShamirSecret> shares){
        BigInteger secret;
        BigInteger finalSecret = BigInteger.ZERO;
        BigInteger temp;
        BigInteger primeNumber = shares.get(0).getPrimeNumber();
        for (int i = 0; i < shares.size(); i++) {
            secret = BigInteger.ONE;
            for (int j = 0; j < shares.size(); j++) {
                if (j == i) continue;
                long numenator =shares.get(j).getPart() - shares.get(i).getPart();
                temp = BigInteger.valueOf(numenator).modInverse(primeNumber);
                secret = secret.multiply(BigInteger.valueOf(shares.get(j).getPart()).multiply(temp));
            }
            secret=secret.multiply(shares.get(i).getSecret());
            finalSecret = finalSecret.add(secret);
        }
        finalSecret = finalSecret.mod(primeNumber);
        return finalSecret;


    }

    public void combineParts(List<File> partsList){
        JsonHandler jsonHandler = new JsonHandler();
        List<ShamirSecret> secretParts = jsonHandler.getSecretPartsFromFiles(partsList);
        PartsValidator.validateSecretParts(secretParts,rebuildCount);
        BigInteger combinedSecret = combine(secretParts);
        jsonHandler.writeCombinedSecret(combinedSecret,secretParts.get(0).getPrimeNumber());

    }




}













/*
*
* BigInteger resultSum = BigInteger.ZERO;
        for (int i = 0; i < rebuildCount; i++) {
            BigInteger product = BigInteger.ONE;
            for (int j = 0; j < rebuildCount; j++) {
                if(i!=j) {
                    ShamirSecret current = parts.get(j);
                    BigInteger currentPart = parts.get(j).getSecret();
                    BigInteger diveder = current.getSecret().subtract(parts.get(i).getSecret()).mod(primeNumber);
                    double reesult = currentPart.divide(diveder).mod(primeNumber);
                    product = product.multiply(reesult);
                }
            }
            int part = parts.get(i).getPart();
            resultSum = resultSum.add(product.multiply(BigInteger.valueOf(part)).mod(primeNumber));
        }
        System.out.println("SECRET : " + resultSum);
*
*
* */