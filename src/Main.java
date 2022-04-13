import algorithm.Splitter;
import secret.ShamirSecret;

import java.math.BigInteger;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Splitter splitter = new Splitter(7,4);
        List<ShamirSecret> shamirSecrets = splitter.splitIntoPieces(123);

    }
}
