import java.util.ArrayList;
import java.util.List;

class InsufficientInventory extends Exception {
    public InsufficientInventory(int currentInventory, int requestedInventory) {
        super("There is not enough inventory for this product. " +
                "Current Inventory: " + currentInventory + " Needed: " + requestedInventory);
    }
}

public class Inventory {
    private List<Product> products = new ArrayList<>();

    private int getProductIndex(String productId) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId().equals(productId)) {
                return i;
            }
        }

        return -1;
    }

    void addProduct(String productId, double price, int quantity) {
        int index = getProductIndex(productId);
        if (index >= 0) {
            Product product = products.get(index);
            product.addStock(quantity);
            if (product.getPrice() != price) {
                product.setPrice(price);
            }

        } else {
            products.add(new Product(productId, price, quantity));
        }
    }

    void removeProduct(String productId, int quantity) throws InsufficientInventory {
        int index = getProductIndex(productId);
        if (index >= 0){
            Product product = getProduct(productId);
            int total = product.getQuantity() - quantity;
            if(total == 0){
                products.remove(quantity);
            }else if (total < 0){
                throw new InsufficientInventory(
                        products.get(index).getQuantity(), quantity
                );
            }
        }else
            throw new InsufficientInventory(0, quantity);
    }

    double totalInventoryValue(){
        double sum = 0;
        for (Product p : products) {
            sum += (p.getPrice() * p.getQuantity());
        }
        return sum;
    }

    boolean inStock(String productId){

        Product product = getProduct(productId);
        if (getProduct(productId) == null){
            return false;
        }

        if (product.getQuantity() >= 1) {
            return true;
        }else
            return false;

    }

    Product getProduct(String productId) {
        int index = getProductIndex(productId);
        if (index >= 0) {
            return products.get(index);
        } else {
            return null;
        }
    }

    String getAllProductNames() {
        List<String> productIds = new ArrayList<>();
        for (Product product : products) {
            productIds.add(product.getProductId());
        }

        return String.join(", ", productIds);
    }

    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        inventory.addProduct("milk", 3.5, 1);
        inventory.addProduct("banana", .6, 1);

        System.out.println(inventory.getAllProductNames());
    }
}

