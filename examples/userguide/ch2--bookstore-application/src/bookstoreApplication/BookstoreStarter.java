package bookstoreApplication;

public class Starter {

    public static void main(String[] args) {
        Bookstore bookstore = new Bookstore();
        for (int i = 0; i < 5; i++) {
            System.out.println("Bookstore.main: Starting request " + i);
            bookstore.searchBook();
        }
    }
}
