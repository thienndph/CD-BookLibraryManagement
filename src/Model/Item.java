package Model;

public class Item {
    private String id;
    private String title;
    private String publisher;
    private int year;
    private boolean status;

    public Item() {
    }

    public Item(String id, String title, String publisher, int year, boolean status) {
        setId(id);
        setTitle(title);
        setPublisher(publisher);
        setYear(year);
        this.status = status;
    }

    @Override
    public String toString() {
        String statusString = status ? "Có sẵn" : "Đang mượn";
        return
                "Mã='" + id + '\'' +
                ", Tiêu đề='" + title + '\'' +
                ", Tác giả='" + publisher + '\'' +
                ", Năm=" + year +
                ", Trạng thái=" + statusString ;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã không được để trống.");
        }
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Tiêu đề không được để trống.");
        }
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        if (publisher == null || publisher.trim().isEmpty()) {
            throw new IllegalArgumentException("Nhà xuất bản không được để trống.");
        }
        this.publisher = publisher;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if (year <= 0) {
            throw new IllegalArgumentException("Năm xuất bản phải là số nguyên dương.");
        }
        this.year = year;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
