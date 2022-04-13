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

    public void combine(List<ShamirSecret> parts,BigInteger primeNumber){
        BigDecimal resultSum = BigDecimal.ONE;
        for (int i = 0; i < rebuildCount; i++) {
            BigDecimal product = BigDecimal.ONE;
            for (int j = 0; j < rebuildCount; j++) {
                if(i!=j) {
                    ShamirSecret current = parts.get(j);
                    BigDecimal currentPart = new BigDecimal(parts.get(j).getSecret());
                    BigDecimal diveder = new BigDecimal(current.getSecret().subtract(parts.get(i).getSecret()).mod(primeNumber));
                    BigDecimal reesult = currentPart.divide(diveder, RoundingMode.HALF_UP).remainder(new BigDecimal(primeNumber));
                    product = product.multiply(reesult);
                }
            }
            int part = parts.get(i).getPart();
            resultSum = resultSum.add(product.multiply(BigDecimal.valueOf(part)).remainder(new BigDecimal(primeNumber)));
        }
        System.out.println("SECRET : " + resultSum);
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