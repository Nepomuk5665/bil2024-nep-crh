import java.util.Scanner;

public class Book extends Medium {

    private String autor;
    private int numberOfPages;
    private String isbn;

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public static Book book() {
        Scanner scanner = new Scanner(System.in);
        Book book = new Book();
        
        Medium medium = Medium.medium();
        book.setTitel(medium.getTitel());
        book.setYear(medium.getYear());
        book.setLanguage(medium.getLanguage());
        
        System.out.println("Autor: ");
        book.setAutor(scanner.nextLine());
        
        System.out.println("Number of Pages: ");
        book.setNumberOfPages(scanner.nextInt());
        scanner.nextLine();
        
        System.out.println("ISBN: ");
        book.setIsbn(scanner.nextLine());
        
        return book;
    }
    
    @Override
    public void printMedium() {
        super.printMedium();
        System.out.println("Autor: " + autor);
        System.out.println("Number of Pages: " + numberOfPages);
        System.out.println("ISBN: " + isbn);
    }
}
