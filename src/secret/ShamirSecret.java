package secret;

import java.math.BigInteger;
import java.util.Objects;

public class ShamirSecret {
    private Long part;
    private BigInteger secret;
    private BigInteger primeNumber;

    public ShamirSecret(Long part, BigInteger secret) {
        this.part = part;
        this.secret = secret;
    }

    public ShamirSecret(Long part, BigInteger secret,BigInteger primeNumber) {
        this.part = part;
        this.secret = secret;
        this.primeNumber = primeNumber;
    }

    public Long getPart() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShamirSecret that = (ShamirSecret) o;
        return Objects.equals(part, that.part) && Objects.equals(secret, that.secret);
    }

    @Override
    public int hashCode() {
        return Objects.hash(part, secret);
    }
}
