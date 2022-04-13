package secret;

import java.math.BigInteger;

public class ShamirSecret {
    private int part;
    private BigInteger secret;

    public ShamirSecret(int part, BigInteger secret) {
        this.part = part;
        this.secret = secret;
    }

    public int getPart() {
        return part;
    }

    public BigInteger getSecret() {
        return secret;
    }

    @Override
    public String toString() {
        return "ShamirSecret{" +
                "part=" + part +
                ", secret=" + secret +
                '}';
    }
}
