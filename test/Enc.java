package test;

public class Enc {
    public static void main(String[] a) {
        System.out.println("中文测试");
        System.out.println("file.encoding=" + System.getProperty("file.encoding")
            + " stdout.encoding=" + System.getProperty("stdout.encoding"));
        System.out.println(java.util.Arrays.toString("中".getBytes()));
    }
}
