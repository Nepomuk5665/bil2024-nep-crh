package ch.noseryoung.sbdemo01;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @GetMapping("/baum")
    public String baum() {
        return "monkey";
    }
    
    @GetMapping
    public ResponseEntity<String> getGreeting() {
        return ResponseEntity.status(HttpStatus.OK).body("Hello again!");
    }

    @GetMapping("/say/{requestedText}")
    public ResponseEntity<String> say(@PathVariable String requestedText) {
        return ResponseEntity.status(HttpStatus.OK).body(requestedText);
    }

    @PostMapping("/Product/{id}")
    public ResponseEntity<Product> createProduct(@PathVariable("id") String id, @RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
}