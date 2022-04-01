import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
//        String fileName = args[0];

//        String fileName = "./data/random.csv";
       String fileName = "./data/WhataburgerData.csv";
//        String fileName = "./data/StarbucksData.csv";

        // mock query for testing
//        Query query = new Query(3, 29.5827351, -98.621094);


        String queriesFile = "./data/Queries.csv";
        List<Query> queries = getQueries(queriesFile);

        for (Query query : queries) {
            List<Store> stores = getStores(fileName);
            stores.forEach((s) -> s.computeDistanceFromQuery(query.getQlat(), query.getQlong()));
            selectNthClosestStore(stores, 0, stores.size() - 1, query.getNumber() - 1);
            ArrayList<Store> newStores = new ArrayList<>();
            int i = 0;
            while (i < query.getNumber()) {
                newStores.add(stores.get(i));
                i++;
            }
            insertionSort(newStores);
            printQueryResults(newStores, query);
        }
    }

    public static void insertionSort(List<Store> stores)
    {
        int n = stores.size();
        for (int i=1; i<n; ++i)
        {
            Store key = stores.get(i);
            int j = i - 1;

            while (j>=0 && stores.get(j).getDistanceFromQuery() > key.getDistanceFromQuery())
            {
                stores.set(j+1, stores.get(j));
                j = j - 1;
            }
            stores.set(j + 1, key);
        }
    }

    public static int getRandomNumber(int min, int max) {
        int it = (int) ((Math.random() * (max - min)) + min);
        System.out.printf("Random number %d generated in range %d - %d\n", it, min, max);
        return it;
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
     * 
     * problem was line 63 (int k = z - l - 1)
     * solution was int k = z -l
     */
    private static Store selectNthClosestStore(List<Store> stores, int l, int r, int i) {
        if (l >= r) {
//            System.out.printf("l (%d) is greater than or equal to r (%d)\n", l, r);
            return l > r ? stores.get(r) : stores.get(l);
        }
        int z = randomPartition(stores, l, r);
        int k = z - l;
//        System.out.printf("i = %d, l = %d, r = %d, z = %d, k = %d\n", i, l, r, z, k);
        if (i == k) {
            return stores.get(z);
        }
        else if (i < k) {
//            System.out.println("i < k");
            return selectNthClosestStore(stores, l, z - 1, i);
        }
        else {
//            System.out.println("i > k");
            return selectNthClosestStore(stores, z + 1, r, i - k);   
        }
    }

    /**
     * This function does work correctly, it is written exactly like he did in lecture #11
     * We need to change the pivot selection before submission to a random number,
     * but for now it's set to p for consistency
     * 
     * problem was j < q in for loop
     * solution was j <= q in for loop
     */
    private static int partition(List<Store> stores, int p, int q) {
        Store pivot = stores.get(p);
        int i = p; // divide the <= pivot and > pivot portion
        for (int j = p + 1; j <= q; ++j) {
            if (stores.get(j).getDistanceFromQuery() <= pivot.getDistanceFromQuery()) {
                i++;
                Collections.swap(stores, j, i);
            }
        }
        Collections.swap(stores, p, i);
        return i;
    }

    private static int randomPartition(List<Store> stores, int p, int q) {
        int n = q - 1 + 1;
        int pivot = (int) Math.random() * (n - 1);
        Collections.swap(stores, 1 + pivot, q);
        return partition(stores, p, q);
    }

    /**
     * Prints the query results in the format he wants
     */
    private static void printQueryResults(List<Store> stores, Query query) {
        System.out.printf("The %d closest stores to (%f, %f):\n", stores.size(), query.getQlat(), query.getQlong());
        System.out.println(stores.stream().map(Store::toString).collect(Collectors.joining("\n")));
        System.out.println("");
    }

    private static List<Store> getStores(String fileName) {
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
