package com.trusohamn.demo;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
class MonitorController {

  private final ServiceRepository repository;

  MonitorController(ServiceRepository repository) {
    this.repository = repository;
  }


  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/services")
  List<Service> all() {
    return repository.findAll();
  }
  // end::get-aggregate-root[]

  @PostMapping("/services")
  Service newService(@RequestBody Service newService) {
    return repository.save(newService);
  }

  // Single item
  
  @GetMapping("/services/{id}")
  Service one(@PathVariable Long id) {
    
    return repository.findById(id)
      .orElseThrow(() -> new RuntimeException());
  }


  @DeleteMapping("/Services/{id}")
  void deleteService(@PathVariable Long id) {
    repository.deleteById(id);
  }
}