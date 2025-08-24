// Abstractions (Interfaces)
interface Printer {
    void print(Invoice invoice);
}

interface Storage {
    void save(Invoice invoice);
}

// Low-level modules (implementations)
class ConsolePrinter implements Printer {
    @Override
    public void print(Invoice invoice) {
        System.out.println("Invoice for book: " + invoice.getBook().getName());
        System.out.println("Author: " + invoice.getBook().getAuthor());
        System.out.println("Quantity: " + invoice.getQuantity());
        System.out.println("Total price: " + invoice.getTotal());
    }
}

class DatabaseStorage implements Storage {
    @Override
    public void save(Invoice invoice) {
        // Simulate saving to a database
        System.out.println("Saving invoice for book: " + invoice.getBook().getName() + " to the database.");
    }
}


// Book and Invoice classes remain the same
class Book{
    private String name;
    private String author;
    private int price;
    private int year;
    private String isbn;

    public Book(String name, String author, int price, int year, String isbn){
        this.name = name;
        this.price = price;
        this.year = year;
        this.author = author;
        this.isbn = isbn;
    }


    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }
}

class Invoice{

    private Book book;
    private int quantity;
    private double discount;
    private double tax;
    private double total;

    public Invoice(Book book, int quantity, double discount, double tax){
        this.book = book;
        this.quantity = quantity;
        this.discount = discount;
        this.tax = tax;
        this.total = this.Calculate_Price_Of_The_Book();
    }

    public double Calculate_Price_Of_The_Book(){
        double price = book.getPrice() * quantity;
        double priceAfterDiscount = price * (1 - discount);
        double finalPrice = priceAfterDiscount * (1 + tax);
        return finalPrice;
    }

    public Book getBook() {
        return book;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotal() {
        return total;
    }
}


// High-level module
class InvoiceSystem {
    public static void main(String[] args) {
        // Create a book
        Book book = new Book("The Lord of the Rings", "J.R.R. Tolkien", 20, 1954, "978-0-618-64015-7");

        // Create an invoice
        Invoice invoice = new Invoice(book, 2, 0.1, 0.08);

        // --- Dependency Injection ---
        // The high-level logic now depends on the abstractions (Printer, Storage)
        // The concrete implementations are created here and "injected".
        Printer printer = new ConsolePrinter();
        Storage storage = new DatabaseStorage();

        // The high-level logic uses the abstractions
        printer.print(invoice);
        storage.save(invoice);
    }
}
