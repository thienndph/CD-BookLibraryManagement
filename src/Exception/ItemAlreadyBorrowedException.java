package Exception;

public class ItemAlreadyBorrowedException extends Exception{
    public ItemAlreadyBorrowedException(String itemId) {
        super("Model.Item already borrowed: " + itemId);
    }
}
