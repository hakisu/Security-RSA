package project.rsa;

import java.math.BigInteger;
import java.util.List;

public class RSA {

    private static final String ALPHABET_MAPPING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz~!@#$%^&*()_+`1234567890-=<>?:\"{}|,./;'[]\\ ";
    private static final int ALPHABET_OFFSET = -2;

    private PublicKey publicKey;
    // pValue and qValue are two prime numbers
    private int pValue;
    private int qValue;
    private int dValue;

    public RSA(PublicKey publicKey) {
        this.publicKey = publicKey;
        splitNumberIntoPrimes(publicKey.getN());
        int phiValue = calculatePhiValue(pValue, qValue);
        this.dValue = calculateDValue(publicKey.getE(), phiValue);
    }

    public String decodeEncryptedValues(List<Integer> encryptedValues) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer currentValue : encryptedValues) {
            stringBuilder.append(decodeEncryptedValue(currentValue));
        }
        return stringBuilder.toString();
    }

    private char decodeEncryptedValue(int encryptedValue) {
        BigInteger encryptedValueBigInteger = BigInteger.valueOf(encryptedValue);
        BigInteger d = BigInteger.valueOf(dValue);
        BigInteger n = BigInteger.valueOf(publicKey.getN());
        BigInteger index = encryptedValueBigInteger.modPow(d, n);

        return ALPHABET_MAPPING.charAt(index.intValue() + ALPHABET_OFFSET);
    }

    private void splitNumberIntoPrimes(int number) {
        int p, q = 0;
        boolean breakOuterLoop = false;
        for (p = 2; p <= number / 2; p++) {
            for (q = 2; q <= number / 2; q++) {
                if (p * q == number) {
                    breakOuterLoop = true;
                    break;
                }
            }
            if (breakOuterLoop) {
                break;
            }
        }
        this.pValue = p;
        this.qValue = q;
    }

    private int calculatePhiValue(int pValue, int qValue) {
        return (pValue - 1) * (qValue - 1);
    }

    private int calculateDValue(int eValue, int phiValue) {
        BigInteger e = BigInteger.valueOf(eValue);
        BigInteger phi = BigInteger.valueOf(phiValue);
        BigInteger negativeExponent = BigInteger.valueOf(-1);

        BigInteger dValue = e.modPow(negativeExponent, phi);
        return dValue.intValue();
    }
}