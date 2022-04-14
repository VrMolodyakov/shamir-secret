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
        BigInteger accum = BigInteger.ZERO;

        for(int formula = 0; formula < shares.size(); formula++)
        {
            BigInteger numerator = BigInteger.ONE;
            BigInteger denominator = BigInteger.ONE;

            for(int count = 0; count < shares.size(); count++)
            {
                if(formula == count)
                    continue; // If not the same value

                int startposition = shares.get(formula).getPart();
                int nextposition = shares.get(count).getPart();

                numerator = numerator.multiply(BigInteger.valueOf(nextposition).negate()).mod(primeNumber); // (numerator * -nextposition) % prime;
                denominator = denominator.multiply(BigInteger.valueOf(startposition - nextposition)).mod(primeNumber); // (denominator * (startposition - nextposition)) % prime;
            }
            BigInteger value = shares.get(formula).getSecret();
            BigInteger tmp = value.multiply(numerator). multiply(denominator.modInverse(primeNumber));
            accum = primeNumber.add(accum).add(tmp).mod(primeNumber); //  (prime + accum + (value * numerator * modInverse(denominator))) % prime;
        }

        System.out.println("The secret is: " + accum + "\n");


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