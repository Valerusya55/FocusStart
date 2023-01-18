import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void mergeInt(String order, String[] filenames) {
        ArrayList<Integer> interim = new ArrayList<>();
        for (int i = 1; i < filenames.length; i++) {
            try (BufferedReader bf = new BufferedReader(new FileReader("src/main/java/" + filenames[i]))) {
                String line = bf.readLine();
                while (line != null && !line.equals("")) {
                    if (line.contains(" ")) {
                        System.out.println("Erroneous line in the file " + filenames[i]);
                    } else {
                        interim.add(Integer.valueOf(line));
                    }
                    line = bf.readLine();
                }
            } catch (FileNotFoundException e) {
                System.out.println("File " + filenames[i] + " cannot be found");

            } catch (NumberFormatException e) {
                System.out.println("File " + filenames[i] + " contains incorrect data");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileWriter writer;
        int[] result = Arrays.stream(interim.toArray(new Integer[0])).mapToInt(j -> j).toArray();
        int[] arr = mergeIntSort(order, result, result.length);
        try {
            writer = new FileWriter(filenames[0]);
            assert arr != null;
            for (int k : arr) {
                writer.write(k + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void mergeString(String order, String[] filenames) {
        ArrayList<String> interim = new ArrayList<>();
        for (int i = 1; i < filenames.length; i++) {
            try (BufferedReader bf = new BufferedReader(new FileReader("src/main/java/" + filenames[i]))) {
                String line = bf.readLine();
                while (line != null && !line.equals("")) {
                    if (line.contains(" ")) {
                        System.out.println("Erroneous line in the file " + filenames[i]);
                    } else {
                        interim.add(line);
                    }
                    line = bf.readLine();
                }
            } catch (FileNotFoundException e) {
                System.out.println("File " + filenames[i] + " cannot be found");
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileWriter writer;
            String[] result = interim.toArray(new String[0]);
            String[] arr = mergeStringSort(order, result, result.length);
            try {
                writer = new FileWriter(filenames[0]);
                assert arr != null;
                for (String k : arr) {
                    writer.write(k + System.lineSeparator());
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static int[] mergeIntSort(String order, int[] arr, int length) {
        if (length < 2) {
            return arr;
        }
        int mid = length / 2;
        int[] left = new int[mid];
        int[] right = new int[length - mid];
        System.arraycopy(arr, 0, left, 0, mid);
        if (length - mid >= 0) System.arraycopy(arr, mid, right, 0, length - mid);
        mergeIntSort(order, left, mid);
        mergeIntSort(order, right, length - mid);
        if (order.equals("a")) {
            return mergeIntAMerge(arr, left, right, mid, length - mid);
        } else {
            return mergeIntDMerge(arr, left, right, mid, length - mid);
        }
    }

    private static String[] mergeStringSort(String order, String[] arr, int length) {
        if (length < 2) {
            return arr;
        }
        int mid = length / 2;
        String[] left = new String[mid];
        String[] right = new String[length - mid];
        System.arraycopy(arr, 0, left, 0, mid);
        if (length - mid >= 0) System.arraycopy(arr, mid, right, 0, length - mid);
        mergeStringSort(order, left, mid);
        mergeStringSort(order, right, length - mid);
        if (order.equals("a")) {
            return mergeStringAMerge(arr, left, right, mid, length - mid);
        } else {
            return mergeStringDMerge(arr, left, right, mid, length - mid);
        }
    }

    private static String[] mergeStringAMerge(String[] arr, String[] leftArr, String[] rightArr, int left, int right) {
        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (leftArr[i].compareToIgnoreCase(rightArr[j]) == 0 || leftArr[i].compareToIgnoreCase(rightArr[j]) < 0) {
                arr[k++] = leftArr[i++];
            } else {
                arr[k++] = rightArr[j++];
            }
        }
        while (i < left) {
            arr[k++] = leftArr[i++];
        }
        while (j < right) {
            arr[k++] = rightArr[j++];
        }
        return arr;
    }

    private static String[] mergeStringDMerge(String[] arr, String[] leftArr, String[] rightArr, int left, int right) {
        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (leftArr[i].compareToIgnoreCase(rightArr[j]) == 0 || leftArr[i].compareToIgnoreCase(rightArr[j]) > 0) {
                arr[k++] = leftArr[i++];
            } else {
                arr[k++] = rightArr[j++];
            }
        }
        while (i < left) {
            arr[k++] = leftArr[i++];
        }
        while (j < right) {
            arr[k++] = rightArr[j++];
        }
        return arr;
    }


    private static int[] mergeIntAMerge(int[] arr, int[] leftArr, int[] rightArr, int left, int right) {
        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (leftArr[i] <= rightArr[j]) {
                arr[k++] = leftArr[i++];
            } else {
                arr[k++] = rightArr[j++];
            }
        }
        while (i < left) {
            arr[k++] = leftArr[i++];
        }
        while (j < right) {
            arr[k++] = rightArr[j++];
        }
        return arr;
    }

    private static int[] mergeIntDMerge(int[] arr, int[] leftArr, int[] rightArr, int left, int right) {
        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (leftArr[i] >= rightArr[j]) {
                arr[k++] = leftArr[i++];
            } else {
                arr[k++] = rightArr[j++];
            }
        }
        while (i < left) {
            arr[k++] = leftArr[i++];
        }
        while (j < right) {
            arr[k++] = rightArr[j++];
        }
        return arr;
    }

    public static void main(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("Missing number of parameters");
        }
        switch (args[0]) {
            case "-i":
                if (!args[1].equals("-a") && !args[1].equals("-d")) {
                    String[] res = Arrays.copyOfRange(args, 1, args.length);
                    mergeInt("a", res);
                } else if (args[1].equals("-a")) {
                    String[] res = Arrays.copyOfRange(args, 2, args.length);
                    mergeInt("a", res);
                } else {
                    String[] res = Arrays.copyOfRange(args, 2, args.length);
                    mergeInt("d", res);
                }
                break;
            case "-s":
                if (!args[1].equals("-a") && !args[1].equals("-d")) {
                    String[] res = Arrays.copyOfRange(args, 1, args.length);
                    mergeString("a", res);
                } else if (args[1].equals("-a")) {
                    String[] res = Arrays.copyOfRange(args, 2, args.length);
                    mergeString("a", res);
                } else {
                    String[] res = Arrays.copyOfRange(args, 2, args.length);
                    mergeString("d", res);
                }
                break;
            case "-a":
                if (args[1].equals("-i")) {
                    String[] res = Arrays.copyOfRange(args, 2, args.length);
                    mergeInt("a", res);
                }
                if (args[1].equals("-s")) {
                    String[] res = Arrays.copyOfRange(args, 2, args.length);
                    mergeString("a", res);
                }
                break;
            case "-d":
                if (args[1].equals("-i")) {
                    String[] res = Arrays.copyOfRange(args, 2, args.length);
                    mergeInt("d", res);
                }
                if (args[1].equals("-s")) {
                    String[] res = Arrays.copyOfRange(args, 2, args.length);
                    mergeString("d", res);
                }
                break;
            default:
                throw new IllegalArgumentException("Non-correct parameters");
        }
    }
}