package com.my.WorkSchedule.repository;

import com.my.WorkSchedule.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
