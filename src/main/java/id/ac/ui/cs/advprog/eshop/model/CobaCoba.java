package id.ac.ui.cs.advprog.eshop.model;

import java.util.UUID;

public class CobaCoba {
}

@ExtendWith(MockitoExtension.class)

class OderServiceTest {
    @InjectMocks
    OrderServiceImpl orderService;

    @Mock
    OrerRepository orderRepository;

    List<Order> orders;

    @BeforeEach
    void setup() {
        List<>
    }

    @Test
    void testCreateOrder() {
        Order order = orders.get(1);
        doReturn(order).when(orderRepository).save(order);
        Order result = orderService.createOrder(order);
        verify(orderRepository, times(1)).save(order);
        asasert
    }
}


