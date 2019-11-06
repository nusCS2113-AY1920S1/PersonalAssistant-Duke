package duke.logic.command.product;

import duke.logic.parser.product.IngredientItemListParser;
import duke.model.commons.Item;
import duke.model.inventory.Ingredient;
import duke.model.product.IngredientItemList;
import duke.model.product.Product;
import duke.testutil.IngredientBuilder;
import duke.testutil.IngredientItemListBuilder;
import duke.testutil.ProductBuilder;
import duke.testutil.ProductDescriptorBuilder;
import org.junit.jupiter.api.Test;

import static duke.logic.command.product.ProductCommandUtil.getEditedProductFromDescriptor;
import static duke.testutil.TypicalProducts.CHEESE_CAKE;
import static duke.testutil.TypicalProducts.EGG_TART;
import static org.junit.jupiter.api.Assertions.*;

class ProductCommandUtilTest {
    public static final Product.Status STATUS_ACTIVE =  Product.Status.valueOf("ACTIVE");
    public static final Product.Status STATUS_ARCHIVE =  Product.Status.valueOf("ARCHIVE");

    private static final String VALID_NAME_CHEESE_CAKE = "Cheese cake";
    private static final Double VALID_PRICE_CHEESE_CAKE = 5.9;
    private static final Double VALID_COST_CHEESE_CAKE = 3.0;
    private static final Product.Status VALID_STATUS_CHEESE_CAKE_ACTIVE = STATUS_ACTIVE;
    private static final Item<Ingredient> ingredientCreamCheese =
            IngredientBuilder.buildIngredientItemWithQuantity("Cream cheese", 3.0);
    private static final IngredientItemList VALID_INGREDIENT_LIST_CHEESE_CAKE =
            new IngredientItemListBuilder().addAllIngredients(ingredientCreamCheese).build();

    private static final String VALID_NAME_EGG_TART = "Egg tart";
    private static final Double VALID_PRICE_EGG_TART = 1.5;
    private static final Double VALID_COST_EGG_TART = 1.0;
    private static final Product.Status VALID_STATUS_EGG_TART_ACTIVE = STATUS_ACTIVE;
    private static final Item<Ingredient> ingredientEgg =
            IngredientBuilder.buildIngredientItemWithQuantity("Egg", 3.0);
    private static final Item<Ingredient> ingredientFlour =
            IngredientBuilder.buildIngredientItemWithQuantity("Flour", 1.0);
    private static final IngredientItemList VALID_INGREDIENT_LIST_EGG_TART =
            new IngredientItemListBuilder().addAllIngredients(ingredientEgg, ingredientFlour).build();

    public static final ProductDescriptor DESC_CHEESE_CAKE;
    public static final ProductDescriptor DESC_EGG_TART;

    static {
        DESC_CHEESE_CAKE =
                new ProductDescriptorBuilder()
                        .withName(VALID_NAME_CHEESE_CAKE)
                        .withIngredientCost(VALID_COST_CHEESE_CAKE)
                        .withRetailPrice(VALID_PRICE_CHEESE_CAKE)
                        .withIngredients(VALID_INGREDIENT_LIST_CHEESE_CAKE)
                        .withStatus(VALID_STATUS_CHEESE_CAKE_ACTIVE)
                        .build();

        DESC_EGG_TART =
                new ProductDescriptorBuilder()
                        .withName(VALID_NAME_EGG_TART)
                        .withRetailPrice(VALID_PRICE_EGG_TART)
                        .withIngredientCost(VALID_COST_EGG_TART)
                        .withIngredients(VALID_INGREDIENT_LIST_EGG_TART)
                        .withStatus(VALID_STATUS_EGG_TART_ACTIVE)
                        .build();
    }
    @Test
    void getEditedProductFromDescriptorTest() {
        Product toEdit = new ProductBuilder(CHEESE_CAKE).build();
        Product afterEdition = new ProductBuilder(CHEESE_CAKE).build();

        // Change name, name are with different capitalization-> success
        afterEdition.setProductName("Cheese_cake");
        Product edited = getEditedProductFromDescriptor(toEdit, new ProductDescriptorBuilder().withName("Cheese_Cake").build());
        assertTrue(edited.equals(afterEdition));

        //change status -> success
        edited = getEditedProductFromDescriptor(toEdit,
                new ProductDescriptorBuilder().withStatus(STATUS_ACTIVE).build());
        afterEdition = new ProductBuilder(CHEESE_CAKE).build();
        afterEdition.setStatus(STATUS_ARCHIVE);
        assertTrue(edited.equals(afterEdition));

        //change cost -> success
        edited = getEditedProductFromDescriptor(toEdit, new ProductDescriptorBuilder().withIngredientCost(
                1000.0).build());
        afterEdition = new ProductBuilder(CHEESE_CAKE).build();
        afterEdition.setIngredientCost(1000.0);
        assertTrue(edited.equals(afterEdition));

        //change price -> success
        edited = getEditedProductFromDescriptor(toEdit, new ProductDescriptorBuilder().withRetailPrice(
                1000.0).build());
        afterEdition = new ProductBuilder(CHEESE_CAKE).build();
        afterEdition.setRetailPrice(1000.0);
        assertTrue(edited.equals(afterEdition));

        //todo: check model for duplicate product
    }

    /**
     * Tests whether the gotten product has the correct information.
     */
    @Test
    void getAddedProductFromDescriptorTest() {

        //with same Info -> returns true
        assertTrue(EGG_TART.hasSameInfo(ProductCommandUtil.getAddedProductFromDescriptor(DESC_EGG_TART)));
        assertTrue(CHEESE_CAKE.hasSameInfo(ProductCommandUtil.getAddedProductFromDescriptor
         (DESC_CHEESE_CAKE)));

        // different info -> returns false
        assertFalse(CHEESE_CAKE.hasSameInfo(ProductCommandUtil.getAddedProductFromDescriptor(DESC_EGG_TART)));

        // Name with different capitalization -> returns true
        ProductDescriptor descriptor = new ProductDescriptor(DESC_CHEESE_CAKE);
        descriptor.setProductName("cHeeSe CAke");
        assertTrue(CHEESE_CAKE.hasSameInfo(ProductCommandUtil.getAddedProductFromDescriptor(descriptor)));

        // different name -> returns false
        descriptor = new ProductDescriptor(DESC_CHEESE_CAKE);
        descriptor.setProductName("Cheese_cake");
        assertFalse(CHEESE_CAKE.hasSameInfo(ProductCommandUtil.getAddedProductFromDescriptor(descriptor)));


        // Different ingredients -> returns false
        descriptor = new ProductDescriptor(DESC_CHEESE_CAKE);
        descriptor.setIngredientItemList(VALID_INGREDIENT_LIST_EGG_TART);
        assertFalse(CHEESE_CAKE.hasSameInfo(ProductCommandUtil.getAddedProductFromDescriptor(descriptor)));

        //Different status -> returns true. This is because this function neglect Status parameter
        descriptor = new ProductDescriptor(DESC_CHEESE_CAKE);
        descriptor.setStatus(STATUS_ARCHIVE);
        assertTrue(CHEESE_CAKE.hasSameInfo(ProductCommandUtil.getAddedProductFromDescriptor(descriptor)));
    }
}