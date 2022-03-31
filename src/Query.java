
public class Query {
	
	private int number;
    private double qlat;
    private double qlong;
    
    public Query(int number, double qlat, double qlong) {
        this.number = number;
        this.qlat = qlat;
        this.qlong = qlong;
    }
    
    @Override
    public String toString() {
        return String.format("%d, %f, %f \n", number,qlat,qlong);
    }
    
    public static Query fromTokens(String[] tokens) throws IllegalArgumentException {
        if (tokens.length < 3) throw new IllegalArgumentException("Expecting 3 tokens to create a store object");

        int number = Integer.parseInt(tokens[2]);
        double qlat = Double.parseDouble(tokens[0]);
        double qlong = Double.parseDouble(tokens[1]);

        // redact extra quotation marks from address
        return new Query(number, qlat, qlong);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getQlat() {
        return qlat;
    }

    public void setQlat(double qlat) {
        this.qlat = qlat;
    }

    public double getQlong() {
        return qlong;
    }

    public void setQlong(double qlong) {
        this.qlong = qlong;
    }
}
