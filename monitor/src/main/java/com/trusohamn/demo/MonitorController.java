package com.trusohamn.demo;


import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
class MonitorController {

  private final ServiceRepository repository;

  MonitorController(ServiceRepository repository) {
    this.repository = repository;
  }

  private static final Logger log = LoggerFactory.getLogger(
    ScheduledTask.class
  );

  @Scheduled(fixedRate = 5000)
  public void getServicesStatus() {
    repository
      .findAll()
      .forEach(
        service -> {
          service.getStatus().add(Math.random() < 0.5);
          if (service.getStatus().size() > 10) service.getStatus().remove(0);
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
}
