package Exception;

public class ItemNotAvailableException extends Exception {
    public ItemNotAvailableException(String itemId) {
        super("Model.Item not available: " + itemId);
    }
}
