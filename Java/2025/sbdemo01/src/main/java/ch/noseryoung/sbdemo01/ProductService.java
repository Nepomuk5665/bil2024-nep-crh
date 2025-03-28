package ch.noseryoung.sbdemo01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public List<Product> getProducts() {
        return repository.findAll();
    }

    public Product getProduct(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public Product createProduct(Product product) {
        return repository.save(product);
    }

    public Product updateProduct(UUID id, Product product) {
        if (repository.existsById(id)) {
            product.setId(id);
            return repository.save(product);
        }
        return null;
    }

    public void deleteProduct(UUID id) {
        repository.deleteById(id);
    }
}