package com.trusohamn.demo;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
class MonitorController {

  private final ServiceRepository repository;
  int limitStatusHold = 10;

  MonitorController(ServiceRepository repository) {
    this.repository = repository;
  }

  @Autowired
  private RestTemplate restTemplate;

  @Scheduled(fixedRate = 5000)
  public void getServicesStatus() {
    repository
      .findAll()
      .forEach(
        service -> {
          String response = "FAIL";
          String URI = service.getUrl();
          try {
            response = restTemplate.getForObject(URI, String.class);
          } catch (RestClientException e) {
            e.printStackTrace();
          }
          Boolean status = response.equals("OK");

          service.getStatus().add(status);
          if (service.getStatus().size() > limitStatusHold) service.getStatus().remove(0);
          repository.save(service);
        }
      );
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
    return repository.findById(id).orElseThrow(() -> new RuntimeException());
  }

  @DeleteMapping("/services/{id}")
  void deleteService(@PathVariable Long id) {
    repository.deleteById(id);
  }

  // Mock endpoints

  @GetMapping("/mock/healthy")
  String healthyService() {
    return "OK";
  }

  @GetMapping("/mock/unhealthy")
  String unhealthyService() {
    return Math.random() < 0.1 ? "OK" : "FAIL";
  }

  @GetMapping("/mock/unstable")
  String unstableService() {
    return Math.random() < 0.5 ? "OK" : "FAIL";
  }
}
