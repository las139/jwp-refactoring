package kitchenpos.dto.table;


import kitchenpos.table.OrderTable;

public class OrderTableResponse {

    private Long id;

    private int numberOfGuests;

    private boolean empty;

    public OrderTableResponse() {
    }

    public OrderTableResponse(Long id, int numberOfGuests, boolean empty) {
        this.id = id;
        this.numberOfGuests = numberOfGuests;
        this.empty = empty;
    }

    public static OrderTableResponse from(OrderTable orderTable) {
        return new OrderTableResponse(orderTable.getId(), orderTable.getNumberOfGuests().getValue(),
            orderTable.isEmpty());
    }

    public Long getId() {
        return id;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public boolean isEmpty() {
        return empty;
    }
}