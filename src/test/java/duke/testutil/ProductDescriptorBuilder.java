package duke.testutil;

import duke.logic.command.product.ProductDescriptor;
import duke.model.product.IngredientItemList;
import duke.model.product.Product;

public class ProductDescriptorBuilder {
    private ProductDescriptor descriptor;

    public  ProductDescriptorBuilder() {
        descriptor = new ProductDescriptor();
    }

    public ProductDescriptorBuilder(ProductDescriptor descriptor) {
        this.descriptor = new ProductDescriptor(descriptor);
    }

    public ProductDescriptorBuilder(Product product) {
        descriptor = new ProductDescriptor();
        descriptor.setProductName((product.getProductName()));
        descriptor.setIngredientCost(product.getIngredientCost());
        descriptor.setRetailPrice(product.getRetailPrice());
        descriptor.setIngredientItemList(product.getIngredients());
        descriptor.setStatus(product.getStatus());
    }

    public ProductDescriptorBuilder withName(String name) {
        descriptor.setProductName(name);
        return this;
    }

    public ProductDescriptorBuilder withIngredientCost(Double cost) {
        descriptor.setIngredientCost(cost);
        return this;
    }

    public ProductDescriptorBuilder withRetailPrice(Double price) {
        descriptor.setRetailPrice(price);
        return this;
    }

    public ProductDescriptorBuilder withIngredients(IngredientItemList ingredients) {
        descriptor.setIngredientItemList(ingredients);
        return this;
    }

    public ProductDescriptorBuilder withStatus(Product.Status status) {
        descriptor.setStatus(status);
        return this;
    }

    public ProductDescriptor build() {
        return descriptor;
    }
}
