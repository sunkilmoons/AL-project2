import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
//        String fileName = args[0];

        String fileName = "./data/random.csv";
//        String fileName = "./data/StarbucksData.csv";

        // mock query for testing
        Query query = new Query(3, 29.5827351, -98.621094);

        List<Store> stores = getStores(fileName, query);

//        fileName = "./data/Queries.csv";
//        List<Query> queries = getQueries(fileName);

        System.out.printf("Before partitioning stores are: %s\n", Store.listIdsWithDistance(stores));

        Store unoptimized = getNthClosestStoreUnoptimized(stores, query.getNumber() - 1);

        Store nthClosestStore = selectNthClosestStore(stores, 0, stores.size() - 1, query.getNumber() - 1);

        System.out.printf("After partitioning stores are: %s\n", Store.listIdsWithDistance(stores));

        /**
         * Test the function against the unoptimized one to ensure it's working correctly
         */
        if (nthClosestStore.getId() != unoptimized.getId()) {
            System.out.printf("Stores are not equal...\nUnoptimized: %s\nYours: %s\n", unoptimized, nthClosestStore);
        }
        else {
            System.out.println("Good job, the stores are equal");
        }

//        printQueryResults(stores, query);
    }

    /**
     * Gets the nth closes store correctly everytime, but doesn't use the algorithm he wants us to use.
     */
    private static Store getNthClosestStoreUnoptimized(List<Store> stores, int i) {
        return stores
                .stream()
                .sorted(Comparator.comparingDouble(Store::getDistanceFromQuery))
                .collect(Collectors.toList())
                .get(i);
    }

    /**
     * This function is not working correctly even though it is written exactly like he did in lecture #14
     */
    private static Store selectNthClosestStore(List<Store> stores, int l, int r, int i) {
        if (l == r) return stores.get(l);
        int z = partition(stores, l, r);
        int k = z - l - 1;
        System.out.printf("i = %d, l = %d, r = %d, z = %d, k = %d\n", i, l, r, z, k);
        if (i == k) {
            return stores.get(z);
        }
        else if (i < k)
            return selectNthClosestStore(stores, l, z - 1, i);
        else return selectNthClosestStore(stores, z + 1, r, i - k);
    }

    /**
     * This function does work correctly, it is written exactly like he did in lecture #11
     * We need to change the pivot selection before submission to a random number,
     * but for now it's set to p for consistency
     */
    private static int partition(List<Store> stores, int p, int q) {
        Store pivot = stores.get(p);
        int i = p; // divide the <= pivot and > pivot portion
        for (int j = p + 1; j < q; ++j) {
            if (stores.get(j).getDistanceFromQuery() <= pivot.getDistanceFromQuery()) {
                i++;
                Collections.swap(stores, j, i);
            }
        }
        Collections.swap(stores, p, i);
        return i;
    }

    /**
     * Prints the query results in the format he wants
     */
    private static void printQueryResults(List<Store> stores, Query query) {
        System.out.printf("The %d closest stores to (%f, %f):\n", stores.size(), query.getQlat(), query.getQlong());
        System.out.println(stores.stream().map(Store::toString).collect(Collectors.joining("\n")));
    }

    private static List<Store> getStores(String fileName, Query query) {
        ArrayList<Store> stores = new ArrayList<>();
        try {
            File file = new File(fileName);

            Scanner sc = new Scanner(file);

            boolean firstLine = true;
            int lineNum = 2;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] tokens = line.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)", -1);

                try {
                    Store store = Store.fromTokens(tokens);
                    store.computeDistanceFromQuery(query.getQlat(), query.getQlong());
                    stores.add(store);
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
    
    private static List<Query> getQueries(String fileName) {
        ArrayList<Query> queries = new ArrayList<>();
        try {
            File file = new File(fileName);

            Scanner sc = new Scanner(file);

            boolean firstLine = true;
            int lineNum = 2;
            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                if (firstLine) { firstLine = false; continue; }

                String[] tokens = line.split(",");

                try {
                    queries.add(Query.fromTokens(tokens));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return queries;
    }
}
