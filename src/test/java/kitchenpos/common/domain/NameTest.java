package kitchenpos.common.domain;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class NameTest {

    @ParameterizedTest(name = "\"{0}\" 일 경우 {1} 발생한다.")
    @DisplayName("Name 생성시 유효성 검사를 체크한다.")
    @MethodSource("providerCreateFailCase")
    void createFail(String name, Class<? extends Exception> exception) {
        assertThatExceptionOfType(exception)
            .isThrownBy(() -> new Name(name));
    }

    private static Stream<Arguments> providerCreateFailCase() {
        return Stream.of(
            Arguments.of(null, NullPointerException.class),
            Arguments.of("", IllegalArgumentException.class)
        );
    }

}
