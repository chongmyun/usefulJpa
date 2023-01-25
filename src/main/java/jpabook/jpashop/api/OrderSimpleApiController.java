package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * xToOne(ManyToOne,OneToOne)
 * Order
 * Order -> Member
 * Order -> Deliver
 * */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    //JSON 으로 변경시 양쪽을 서로 호출하면서 무한루프를 돈다
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());

        for(Order order : all){
            order.getMember().getName();
            order.getDelivery().getAddress();
        }
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<simpleOrderDto> orderV2(){
        //ORDER 2개
        //1 + N * (참조하는 엔티티 갯수)
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());

        List<simpleOrderDto> result = orders.stream()
                .map(o -> new simpleOrderDto(o))
                .collect(Collectors.toList());

        return result;
    }
    @GetMapping("/api/v3/simple-orders")
    public List<simpleOrderDto> orderV3(){
        List<Order> orders = orderRepository.findAllWithMemberDelivery();

        List<simpleOrderDto> result = orders.stream().map(o -> new simpleOrderDto(o)).collect(Collectors.toList());

        return result;
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> orderV4(){
        return orderSimpleQueryRepository.findOrderDtos();
    }

    @Data
    static class simpleOrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public simpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }


}
