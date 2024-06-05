
import Model.Item;
import java.io.IOException;
import java.util.concurrent.Callable;

public class DataProcessor<T extends Item> implements Callable<String> {
    private LibraryManagement<T> library;
    private String bookFilePath;
    private String cdFilePath;
    private boolean isRead;

    public DataProcessor(LibraryManagement<T> library, String bookFilePath, String cdFilePath, boolean isRead) {
        this.library = library;
        this.bookFilePath = bookFilePath;
        this.cdFilePath = cdFilePath;
        this.isRead = isRead;
    }

    @Override
    public String call() {
        try {
            if (isRead) {
                library.readDataFromFile(bookFilePath, cdFilePath);
                return "Dữ liệu đã được đọc từ các tập tin thành công.";
            } else {
                library.writeDataToFile(bookFilePath, cdFilePath);
                return "Dữ liệu đã được ghi vào các tập tin thành công.";
            }
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
    }
}
