package org.example.project.database.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.project.database.model.Customer;
import org.example.project.database.model.Product;
import org.example.project.database.service.CustomerService;
import org.example.project.database.service.OrderService;
import org.example.project.database.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Log4j2
public class DataController {

    private final OrderService orderService;
    private final CustomerService customerService;
    private final ProductService productService;

    @PostMapping(value = "/createOrder")
    public ResponseEntity<Void> sendData(@RequestBody Customer customer, Principal principal){
        if (principal == null){
            log.info("User not authorized");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        customerService.save(customer);
        orderService.createOrder(customer, customer.getShoppingCart());
        log.info("Create order");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getData")
    public ResponseEntity<Page<Product>> getData(@RequestParam("page_number") int page_number,
                                                 @RequestParam("countPerPage") int countPerPage,
                                                 @RequestParam("search_value") String searchValue){
        Page<Product> productList = productService.findProductsByPage(page_number, countPerPage, searchValue);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/getSortedData")
    public ResponseEntity<Page<Product>> getSortedData(@RequestParam("page_number") int page_number,
                                                       @RequestParam("countPerPage") int countPerPage,
                                                       @RequestParam boolean value,
                                                       @RequestParam("search_value") String searchValue){
        Page<Product> productList = productService.getSortedProduct(page_number, countPerPage, value, searchValue);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/getLength")
    public ResponseEntity<Long> getLength(@RequestParam("search_value") String searchValue){
        System.out.println(searchValue);
        Long length = productService.getTotalProductCount(searchValue);
        return new ResponseEntity<>(length, HttpStatus.OK);
    }
}
