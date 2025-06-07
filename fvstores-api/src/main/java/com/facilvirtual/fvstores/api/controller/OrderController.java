package com.facilvirtual.fvstores.api.controller;

import com.facilvirtual.fvstoresdesk.model.Customer;
import com.facilvirtual.fvstoresdesk.model.Order;
import com.facilvirtual.fvstoresdesk.model.OrderLine;
import com.facilvirtual.fvstoresdesk.model.Product;
import com.facilvirtual.fvstoresdesk.service.CustomerService;
import com.facilvirtual.fvstoresdesk.service.OrderService;
import com.facilvirtual.fvstoresdesk.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;
    private final CustomerService customerService;

    @Autowired
    public OrderController(OrderService orderService, ProductService productService, CustomerService customerService) {
        this.orderService = orderService;
        this.productService = productService;
        this.customerService = customerService;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        // OrderService.getOrder(Long orderId) is equivalent to findById
        Order order = orderService.getOrder(id);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Order order, @RequestParam(required = false) Long customerId) {
        try {
            // Basic validation and setup
            if (customerId != null) {
                Customer customer = customerService.getCustomer(customerId);
                if (customer == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer not found with ID: " + customerId);
                }
                order.setCustomer(customer);
            } else if (order.getCustomer() == null || order.getCustomer().getId() == null) {
                 // Default to a generic customer or handle as error if customer is mandatory
                 // For now, let's assume a customer must be identified one way or another.
                 // This might require a getCustomerByBarCode("1") or similar for "Cliente Ocasional"
                 // or ensure the client sends a valid customer object.
                 // For this example, we'll fetch a default "Cliente Ocasional" if no customer info.
                Customer occasionalCustomer = customerService.getCustomer(1L); // Assuming ID 1 is "Cliente Ocasional"
                if (occasionalCustomer == null) {
                     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Default customer not found.");
                }
                order.setCustomer(occasionalCustomer);
            }


            // Further order setup (dates, status, etc.) would typically be done here or in service
            if (order.getCreationDate() == null) {
                order.setCreationDate(new Date());
            }
            if (order.getSaleDate() == null) {
                order.setSaleDate(new Date());
            }
            order.setStatus("PENDING_API_CONFIRMATION"); // Example status

            // Validate and link products in order lines
            if (order.getOrderLines() != null) {
                for (OrderLine line : order.getOrderLines()) {
                    if (line.getProduct() == null || line.getProduct().getId() == null) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product ID missing in an order line.");
                    }
                    Product product = productService.findById(line.getProduct().getId());
                    if (product == null) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product not found with ID: " + line.getProduct().getId());
                    }
                    line.setProduct(product); // Use the fetched product
                    line.setOrder(order); // Set back-reference
                }
            }

            orderService.saveOrder(order);
            return new ResponseEntity<>(order, HttpStatus.CREATED);

        } catch (Exception e) {
            // Log exception e.getMessage(), etc.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating order: " + e.getMessage());
        }
    }
}
