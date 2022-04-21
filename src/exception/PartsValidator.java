package exception;

import secret.ShamirSecret;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PartsValidator {
    public static void validateSecretParts(List<ShamirSecret> parts,int rebuildCount){
        checkOnPartCount(parts,rebuildCount);
        checkOnEqualSecretPart(parts);
    }
    private static void checkOnEqualSecretPart(List<ShamirSecret> secretParts){
        Set<ShamirSecret> check = new HashSet<>(secretParts);
        if(check.size() != secretParts.size()) {
            throw new IllegalStateException("Identical secret's part");
        }
    }

    private static void checkOnPartCount(List<ShamirSecret> secretParts,int rebuildCount){
        if(secretParts.size()<rebuildCount || rebuildCount<4){
            throw new IllegalStateException("few secret's parts");
        }
    }
}
