package kitchenpos.menu.dto;

import java.util.List;
import java.util.stream.Collectors;

import kitchenpos.menu.domain.Menu;
import kitchenpos.menugroup.domain.MenuGroup;
import kitchenpos.product.domain.Price;

public class MenuRequest {
	private String name;
	private long price;
	private Long menuGroupId;
	private List<MenuProductRequest> menuProducts;

	public MenuRequest() {
	}

	private MenuRequest(final String name, final long price, final Long menuGroupId,
		final List<MenuProductRequest> menuProducts) {
		this.name = name;
		this.price = price;
		this.menuGroupId = menuGroupId;
		this.menuProducts = menuProducts;
	}

	public static MenuRequest of(final String name, final long price, final Long menuGroupId,
		final List<MenuProductRequest> menuProducts) {
		return new MenuRequest(name, price, menuGroupId, menuProducts);
	}

	public List<Long> productIds() {
		return menuProducts.stream()
			.map(MenuProductRequest::getProductId)
			.collect(Collectors.toList());
	}

	public Menu toEntity(MenuGroup menuGroup) {
		return Menu.of(this.name, Price.of(this.price), menuGroup);
	}

	public String getName() {
		return name;
	}

	public long getPrice() {
		return price;
	}

	public Long getMenuGroupId() {
		return menuGroupId;
	}

	public List<MenuProductRequest> getMenuProducts() {
		return menuProducts;
	}
}
