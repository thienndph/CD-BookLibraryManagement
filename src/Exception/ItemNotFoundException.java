package Exception;

public class ItemNotFoundException extends Exception{
    public ItemNotFoundException(String itemId) {
        super("Model.Item not found: " + itemId);
    }
}
