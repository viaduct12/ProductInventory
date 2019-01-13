import org.junit.Assert;
import org.junit.Test;

import javax.naming.InsufficientResourcesException;

public class InventoryTests {
    @Test
    public void addApples() {
        Inventory inventory = new Inventory();
        inventory.addProduct("apple", .6, 2);

        Assert.assertEquals(2, inventory.getProduct("apple").getQuantity());
    }

    @Test
    public void addMoreApples() {
        Inventory inventory = new Inventory();
        inventory.addProduct("apple", .6, 1);
        inventory.addProduct("apple", .6, 2);

        Assert.assertEquals(3, inventory.getProduct("apple").getQuantity());
    }

    @Test
    public void updatePrice() {
        Inventory inventory = new Inventory();
        inventory.addProduct("apple", .6, 1);
        inventory.addProduct("apple", .7, 2);

        Assert.assertEquals(.7, inventory.getProduct("apple").getPrice(), .0001);
    }

    @Test
    public void removeSneakyApples() throws InsufficientInventory{
        Inventory inventory = new Inventory();
        inventory.addProduct("apple", .6, 10);
        inventory.removeProduct("apple",2);

        Assert.assertEquals(8,8,.000001);
    }

    @Test(expected = InsufficientInventory.class)
    public void removeApples() throws InsufficientInventory {
        Inventory inventory = new Inventory();
        inventory.addProduct("apple", .6, 10);
        inventory.removeProduct("apple", 12);
    }

    @Test(expected = InsufficientInventory.class)
    public void removeFakeProduct() throws InsufficientInventory{
        Inventory inventory = new Inventory();
        inventory.removeProduct("fakeProduct", 12);
    }

    @Test
    public void addTrueApples() {
        Inventory inventory = new Inventory();
        inventory.addProduct("apple", .6, 10);

        Assert.assertTrue(inventory.inStock("apple"));
    }

    @Test
    public void falseApples() {
        Inventory inventory = new Inventory();

        Assert.assertFalse(inventory.inStock("apple"));
    }

    @Test
    public void checkFakeProduct() {
        Inventory inventory = new Inventory();

        Assert.assertFalse(inventory.inStock("fakeProduct"));
    }

    @Test
    public void checkTotalInventory(){
        Inventory inventory = new Inventory();
        inventory.addProduct("apple", .6, 10);
        inventory.addProduct("milk", 1.2, 5);
        inventory.addProduct("carrots", .3, 2);
        inventory.addProduct("lettuce", .7, 4);

        Assert.assertEquals(15.4, inventory.totalInventoryValue(),.00001);
    }

}
