package helper;

public class HashFunction {

    private static Integer degrees = 360;

    public static int hash(String key) {
        return key.hashCode()%degrees;
    }
}
