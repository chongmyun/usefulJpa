package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue
    @Column(name="order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    @JsonIgnore
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    @JsonIgnore
    private Item item;

    @Column(name="order_price")
    private int orderPrice;

    private int count;

    /*생성 메서드*/
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();

        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);
        
        item.removeStock(count);
        return orderItem;
    }

    /**
     * 주문아이템 취소
     */
    public void cancel() {
        getItem().addStock(count);
    }

    /**
     * 주문상품 전체 가겨 조회
     */
    public int getTotalPrice() {
        return getOrderPrice()*getCount();
    }
}
