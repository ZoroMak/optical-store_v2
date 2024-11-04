package org.example.project.database.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.project.database.model.Customer;
import org.example.project.database.model.Order;
import org.example.project.database.model.Product;
import org.example.project.database.repo.OrderRepository;
import org.example.project.database.repo.ProductRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    public void createOrder(Customer customer, String shoppingCart){

        Map<Integer, Integer> cart = getMap(shoppingCart);

        assert cart != null;

        for (Map.Entry<Integer, Integer> entry : cart.entrySet()){
            int productId = entry.getKey();
            int quantity = entry.getValue();
            Product product = productRepository.findById(productId);

            if (product == null) {
                throw new IllegalArgumentException("Product with ID " + productId + " not found");
            }

            Order order = new Order();
            order.setCustomer(customer);
            order.setProductId(productId);
            order.setQuantity(quantity);
            order.setTotalPrice(quantity * product.getCost());

            save(order);
        }
    }

    private Map<Integer, Integer> getMap(String shoppingCart) {
        try {
            return objectMapper.readValue(shoppingCart, new TypeReference<Map<Integer, Integer>>() {});
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to parse shopping cart data", e);
        }
    }

    private void save(Order order){
        orderRepository.save(order);
    }
}
