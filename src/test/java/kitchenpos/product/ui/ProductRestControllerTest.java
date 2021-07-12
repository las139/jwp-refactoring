package kitchenpos.product.ui;

import com.fasterxml.jackson.core.type.TypeReference;
import kitchenpos.MockMvcTestHelper;
import kitchenpos.common.Price;
import kitchenpos.product.application.ProductService;
import kitchenpos.product.domain.Product;
import kitchenpos.product.dto.ProductRequest;
import kitchenpos.product.dto.ProductResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductRestController.class)
class ProductRestControllerTest extends MockMvcTestHelper {

    @MockBean
    private ProductService productService;

    @Autowired
    private ProductRestController productRestController;

    @Override
    protected Object controller() {
        return productRestController;
    }

    @DisplayName("상품 생성 요청")
    @Test
    void createTest() throws Exception {
        // given
        ProductRequest productRequest = new ProductRequest("불고기", BigDecimal.valueOf(1000L));
        Product product = new Product("불고기", Price.of(BigDecimal.valueOf(1000L)));
        ProductResponse expected = ProductResponse.of(product);
        Mockito.when(productService.create(any())).thenReturn(expected);

        // when
        ResultActions resultActions = 상품_생성_요청(productRequest);

        // then
        MvcResult mvcResult = 상품_생성_성공(resultActions);
        ProductResponse responseBody = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                              ProductResponse.class);
        assertAll(() -> {
            assertThat(responseBody.getName()).isEqualTo(product.getName());
            assertThat(responseBody.getPrice()).isEqualTo(product.getPrice().getValue());
        });
        Mockito.verify(productService).create(any());

    }

    @DisplayName("전체 상품 조회 요청")
    @Test
    void listTest() throws Exception {
        // then
        Product 불고기 = new Product("불고기", Price.of(BigDecimal.valueOf(1000L)));
        Product 삼겹살 = new Product("삼겹살", Price.of(BigDecimal.valueOf(1500L)));
        Mockito.when(productService.list()).thenReturn(Arrays.asList(ProductResponse.of(불고기),
                                                                     ProductResponse.of(삼겹살)));

        // when
        ResultActions resultActions = 전체_상품_조회_요청();

        // then
        MvcResult mvcResult = 전체_상품_조회_성공(resultActions);
        List<ProductResponse> products = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                        new TypeReference<List<ProductResponse>>(){});
        assertThat(products).isNotEmpty().hasSize(2);

    }

    private ResultActions 상품_생성_요청(final ProductRequest productRequest) throws Exception {
        return postRequest("/api/products", productRequest);
    }

    private MvcResult 상품_생성_성공(final ResultActions resultActions) throws Exception {
        return resultActions.andExpect(status().isCreated()).andReturn();
    }

    private ResultActions 전체_상품_조회_요청() throws Exception {
        return getRequest("/api/products");
    }

    private MvcResult 전체_상품_조회_성공(final ResultActions resultActions) throws Exception {
        return resultActions.andExpect(status().isOk()).andReturn();
    }
}