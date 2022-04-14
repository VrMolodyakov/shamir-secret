package algorithm;

import secret.ShamirSecret;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;

public class Combiner {
    private final int rebuildCount;

    public Combiner(int rebuildCount) {
        this.rebuildCount = rebuildCount;
    }

    public void combine(List<ShamirSecret> shares,BigInteger primeNumber){
        BigInteger secret;
        BigInteger finalSecret = BigInteger.ZERO;
        BigInteger temp;
        for (int i = 0; i < shares.size(); i++) {
            secret = BigInteger.ONE;
            for (int j = 0; j < shares.size(); j++) {
                if (j == i) continue;
                int numenator =shares.get(j).getPart() - shares.get(i).getPart();
                temp = BigInteger.valueOf(numenator).modInverse(primeNumber);
                secret = secret.multiply(BigInteger.valueOf(shares.get(j).getPart()).multiply(temp));
            }
            secret=secret.multiply(shares.get(i).getSecret());
            finalSecret = finalSecret.add(secret);
        }
        finalSecret = finalSecret.mod(primeNumber);
        System.out.println(finalSecret);


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