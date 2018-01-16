package project.rsa;

public class PublicKey {

    // modulus
    private int n;
    // public key exponent
    private int e;

    public PublicKey(int n, int e) {
        this.n = n;
        this.e = e;
    }

    public int getN() {
        return n;
    }

    public int getE() {
        return e;
    }

    @Override
    public String toString() {
        return "PublicKey{" + "n=" + n + ", e=" + e + '}';
    }
}