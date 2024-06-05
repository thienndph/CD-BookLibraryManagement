package Model;

public class Book extends Item {
    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên tác giả không được để trống.");
        }
        this.author = author;
    }

    public Book(String id, String title, String publisher, int year, boolean status, String author) {
        super(id, title, publisher, year, status);
        setAuthor(author);
    }

    @Override
    public String toString() {
        return "Sách :" +
                "Tác giả='" + author +" "+ super.toString();
    }
}
