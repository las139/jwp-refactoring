package kitchenpos.menu.dto;

import java.math.BigDecimal;
import kitchenpos.domain.Price;
import kitchenpos.domain.Quantity;
import kitchenpos.menu.domain.MenuProduct;

public class MenuProductRequest {

    private final Long productId;

    private final BigDecimal price;

    private final Long quantity;

    public MenuProductRequest(final Long productId, final BigDecimal price, final Long quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public static MenuProductRequest of(
        final Long productId,
        final BigDecimal price,
        final Long quantity
    ) {
        return new MenuProductRequest(productId, price, quantity);
    }

    public MenuProduct toMenuProduct() {
        return new MenuProduct(productId, new Price(price), new Quantity(quantity));
    }

    public Long getProductId() {
        return productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getQuantity() {
        return quantity;
    }
}