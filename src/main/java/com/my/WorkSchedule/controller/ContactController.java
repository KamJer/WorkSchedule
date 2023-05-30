package com.my.WorkSchedule.controller;

import com.my.WorkSchedule.entity.Contact;
import com.my.WorkSchedule.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping("/contacts")
public class ContactController {
    private final ContactService contactService;
    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public ResponseEntity<List<Contact>> getAllContacts() {
        logger.info("GET /contacts - Getting all contacts");
        List<Contact> contacts = contactService.getAllContacts();
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id) {
        logger.info("GET /contacts/{} - Getting contact by ID: {}", id);
        return contactService.getContactById(id)
                .map(contact -> new ResponseEntity<>(contact, HttpStatus.OK))
                .orElseGet(() -> {
                    logger.warn("GET /contacts/{} - Contact not found for ID: {}", id);
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                });
    }

    @PostMapping
    public ResponseEntity<Contact> addContact(@RequestBody Contact contact) {
        logger.info("POST /contacts - Adding new contact: {}", contact);
        Contact savedContact = contactService.addContact(contact);
        return new ResponseEntity<>(savedContact, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@RequestBody Contact contact) {
        logger.info("PUT /contacts/{} - Updating contact with ID: {}", contact.getId());
        Contact updatedContact = contactService.updateContact(contact);
        return new ResponseEntity<>(updatedContact, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        logger.info("DELETE /contacts/{} - Deleting contact with ID: {}", id);
        contactService.deleteContact(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
