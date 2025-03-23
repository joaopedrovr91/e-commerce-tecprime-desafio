package com.e_commerce_tecprime_service.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final RestTemplate restTemplate;
    private static final String FAKE_STORE_API_URL = "https://fakestoreapi.com/products";

    @Autowired
    public ProductService(ProductRepository productRepository, RestTemplate restTemplate) {
        this.productRepository = productRepository;
        this.restTemplate = restTemplate;
    }

    public List<FakeStoreProduct> getProductsFromFakeStoreApi() {
        FakeStoreProduct[] products = restTemplate.getForObject(FAKE_STORE_API_URL, FakeStoreProduct[].class);
        List<FakeStoreProduct> productList = Arrays.asList(products);
        System.out.println("Number of products retrieved from FakeStore API: " + productList.size());  // Add this line
        return productList;
    }

    public void saveProduct(FakeStoreProduct fakeStoreProduct) {
        Product product = new Product();
        product.setTitle(fakeStoreProduct.getTitle());

        String description = fakeStoreProduct.getDescription();
        int maxLength = 255; // Choose a maximum length that fits your database column
        if (description != null && description.length() > maxLength) {
            description = description.substring(0, maxLength); // Truncate
        }
        product.setDescription(description);

        product.setPrice(fakeStoreProduct.getPrice());
        product.setImage(fakeStoreProduct.getImage());
        product.setCategory(fakeStoreProduct.getCategory());
        product.setDataRegistration(new Timestamp(System.currentTimeMillis()));

        System.out.println("Saving product: " + product.getTitle());
        productRepository.save(product);
        System.out.println("Product saved: " + product.getId());
    }


    public void populateDatabase() {

        long count = productRepository.count();
        System.out.println("Number of products in database: " + count);

        if (count > 0) {
            // Database is already populated
            System.out.println("Database is already populated. Skipping data import.");
            return;
        }

        List<FakeStoreProduct> products = getProductsFromFakeStoreApi();
        products.forEach(this::saveProduct);
    }


    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
}