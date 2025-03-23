package com.e_commerce_tecprime_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import com.github.javafaker.Faker;


import com.e_commerce_tecprime_service.entity.Product;
import com.e_commerce_tecprime_service.entity.User;
import com.e_commerce_tecprime_service.populateFakeStore.FakeStoreProduct;
import com.e_commerce_tecprime_service.populateFakeStore.FakeStoreUser;
import com.e_commerce_tecprime_service.repository.ProductRepository;
import com.e_commerce_tecprime_service.repository.UserRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private static final String FAKE_STORE_API_URL = "https://fakestoreapi.com/products";
    private static final String FAKE_STORE_API_USERS_URL = "https://fakestoreapi.com/users";

    public ProductService(ProductRepository productRepository, UserRepository userRepository, RestTemplate restTemplate) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    // Métodos para Products

    public List<FakeStoreProduct> getProductsFromFakeStoreApi() {
        FakeStoreProduct[] products = restTemplate.getForObject(FAKE_STORE_API_URL, FakeStoreProduct[].class);
        return Arrays.asList(products);
    }

    public void saveProduct(FakeStoreProduct fakeStoreProduct) {
        Product product = new Product();
        product.setId(fakeStoreProduct.getId());
        product.setTitle(fakeStoreProduct.getTitle());
        product.setDescription(fakeStoreProduct.getDescription());
        product.setPrice(fakeStoreProduct.getPrice());
        product.setCategory(fakeStoreProduct.getCategory());
        product.setImage(fakeStoreProduct.getImage());
        product.setDataRegistration(new Timestamp(System.currentTimeMillis()));

        try {
            productRepository.save(product);
        }  catch (ObjectOptimisticLockingFailureException e) {
             logger.error("Erro ao salvar produto com ObjectOptimisticLockingFailureException {}",product.getId(), e.getMessage());
        } catch (DataIntegrityViolationException e) {
           logger.error("Erro de integridade ao salvar produto {}: {}", product.getId(), e.getMessage());
        }
    }

    // Métodos para Users

    public List<FakeStoreUser> getUsersFromFakeStoreApi() {
        FakeStoreUser[] users = restTemplate.getForObject(FAKE_STORE_API_USERS_URL, FakeStoreUser[].class);
        return Arrays.asList(users);
    }

    public List<FakeStoreProduct> generateFakeProducts(int quantity) {
    Faker faker = new Faker();
    List<FakeStoreProduct> products = new ArrayList<>();

    for (int i = 0; i < quantity; i++) {
        FakeStoreProduct product = new FakeStoreProduct();
        product.setId((long) (i + 100)); // IDs maiores para evitar conflitos
        product.setTitle(faker.commerce().productName());
        product.setDescription(faker.lorem().paragraph());
        product.setPrice(faker.number().randomDouble(2, 10, 200)); // Preços de 10 a 200
        product.setCategory(faker.commerce().department());
        product.setImage("https://via.placeholder.com/150"); // Imagens placeholder
        products.add(product);
    }
    return products;
}

public List<FakeStoreUser> generateFakeUsers(int quantity) {
    Faker faker = new Faker();
    List<FakeStoreUser> users = new ArrayList<>();

    for (int i = 0; i < quantity; i++) {
        FakeStoreUser user = new FakeStoreUser();
        FakeStoreUser.Name name = new FakeStoreUser.Name();
        FakeStoreUser.Address address = new FakeStoreUser.Address();
        FakeStoreUser.Geolocation geolocation = new FakeStoreUser.Geolocation();
        user.setId(i + 100);
        user.setEmail(faker.internet().emailAddress());
        user.setUsername(faker.name().username());
        user.setPassword("password");
        name.setFirstname(faker.name().firstName());
        name.setLastname(faker.name().lastName());
        user.setName(name);
        user.setPhone(faker.phoneNumber().phoneNumber());
        address.setCity(faker.address().city());
        address.setStreet(faker.address().streetName());
        address.setNumber(faker.number().numberBetween(1, 1000));
        address.setZipcode(faker.address().zipCode());
        geolocation.setLat(faker.address().latitude());
        geolocation.setLongitude(faker.address().longitude());
        address.setGeolocation(geolocation);
        user.setAddress(address);
        users.add(user);

    }
    return users;
}

   public void saveUser(FakeStoreUser fakeStoreUser) {
     User user = new User();
        user.setId(fakeStoreUser.getId()); // DEFINE O ID DA API
       Optional<User> existingUser = userRepository.findById(user.getId());
        if (existingUser.isPresent()) {
            logger.warn("Usuário com ID {} já existe. Pulando.", fakeStoreUser.getId());
            return;
        }
        user.setEmail(fakeStoreUser.getEmail());
        user.setUsername(fakeStoreUser.getUsername());
        user.setPassword(fakeStoreUser.getPassword());
        if (fakeStoreUser.getName() != null) {
            user.setName(fakeStoreUser.getName().getFirstname() + " " + fakeStoreUser.getName().getLastname());
        }
        user.setPhone(fakeStoreUser.getPhone());
        if (fakeStoreUser.getAddress() != null) {
            user.setCity(fakeStoreUser.getAddress().getCity());
            user.setStreet(fakeStoreUser.getAddress().getStreet());
            if (fakeStoreUser.getAddress().getNumber() != null) {
                user.setNumber(fakeStoreUser.getAddress().getNumber());
            }
            user.setZipcode(fakeStoreUser.getAddress().getZipcode());
            if (fakeStoreUser.getAddress().getGeolocation() != null) {
                user.setLat(fakeStoreUser.getAddress().getGeolocation().getLat());
                user.setLongitude(fakeStoreUser.getAddress().getGeolocation().getLongitude());
            }
        }
        try {
               userRepository.save(user);
        } catch (ObjectOptimisticLockingFailureException e) {
             logger.error("Erro ao salvar usuario com ObjectOptimisticLockingFailureException {}",user.getId(), e.getMessage());
        } catch (DataIntegrityViolationException e) {
            logger.error("Erro de integridade ao salvar usuário com ID {}: {}", user.getId(), e.getMessage());
             }
    }


    public void cleanDatabase() {
        userRepository.deleteAll();
        productRepository.deleteAll();
    }

    public void populateProducts() {
        List<FakeStoreProduct> products = getProductsFromFakeStoreApi();
        products.forEach(this::saveProduct);
    }

    public void populateUsers() {
        List<FakeStoreUser> users = getUsersFromFakeStoreApi();
        users.forEach(this::saveUser);
    }

    @Transactional(readOnly = true)
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
}