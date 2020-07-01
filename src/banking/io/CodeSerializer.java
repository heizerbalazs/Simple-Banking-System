package banking.io;

public class CodeSerializer {

    public static String serialize(int[] arr) {
        String s = "";
        for (int i : arr) {
            s += String.format("%d",i);
        }
        return s;
    }

    public static int[] deserialize(String s) {
        int[] arr = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            try {
                arr[i] = Integer.parseInt(s.substring(i, i+1));
            } catch (NumberFormatException nfe) {
                //NOTE: write something here if you need to recover from formatting errors
            };
        }
        return arr;
    }
}
