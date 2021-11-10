package com.trusohamn.demo;
import org.springframework.data.jpa.repository.JpaRepository;

interface ServiceRepository extends JpaRepository<Service, Long> {

}