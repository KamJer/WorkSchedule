package com.my.WorkSchedule.repository;

import com.my.WorkSchedule.entity.Contact;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM CONTACT_TASK WHERE ID = :contactId", nativeQuery = true)
    void deleteTaskContactByContactId(@Param("contactId") long contactId);
}
