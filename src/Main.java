import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
//        String fileName = args[0];

        // using small input data for testing
        String fileName = "./data/small_data.csv";

        List<Store> stores = getStores(fileName);

        // with mocked query latitude and longitude...
        printQueryResults(stores, 29.5827351, -98.621094);
    }

    private static void printQueryResults(List<Store> stores, double queryLatitude, double queryLongitude) {
        System.out.printf("The %d closest stores to (%f, %f):\n", stores.size(), queryLatitude, queryLongitude);
        System.out.println(stores.stream().map(Store::toString).collect(Collectors.joining("\n")));
    }

    private static List<Store> getStores(String fileName) {
        ArrayList<Store> stores = new ArrayList<>();
        try {
            File file = new File(fileName);

            Scanner sc = new Scanner(file);

            boolean firstLine = true;
            int lineNum = 2;
            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                if (firstLine) { firstLine = false; continue; }

                String[] tokens = line.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)", -1);

                try {
                    stores.add(Store.fromTokens(tokens));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // For testing regex...
//                System.out.printf(
//                        "Line #%d has tokens: %s\n",
//                        lineNum,
//                        String.join(" | ", tokens)
//                );
//                lineNum++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return stores;
    }
}
