package com.my.WorkSchedule;

import com.my.WorkSchedule.controller.ContactController;
import com.my.WorkSchedule.entity.Contact;
import com.my.WorkSchedule.service.ContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ContactControllerTest {

    @Mock
    private ContactService contactService;

    private ContactController contactController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        contactController = new ContactController(contactService);
    }

    @Test
    void getAllContacts_ShouldReturnListOfContacts() {
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact());
        contacts.add(new Contact());

        when(contactService.getAllContacts()).thenReturn(contacts);

        ResponseEntity<List<Contact>> response = contactController.getAllContacts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(contacts, response.getBody());
        verify(contactService, times(1)).getAllContacts();
    }

    @Test
    void getContactById_ExistingId_ShouldReturnContact() {
        long contactId = 1L;
        Contact contact = new Contact();
        contact.setId(contactId);

        when(contactService.getContactById(contactId)).thenReturn(Optional.of(contact));

        ResponseEntity<Contact> response = contactController.getContactById(contactId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(contact, response.getBody());
        verify(contactService, times(1)).getContactById(contactId);
    }

    @Test
    void getContactById_NonExistingId_ShouldReturnNotFound() {
        long contactId = 1L;

        when(contactService.getContactById(contactId)).thenReturn(Optional.empty());

        ResponseEntity<Contact> response = contactController.getContactById(contactId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(contactService, times(1)).getContactById(contactId);
    }

    @Test
    void addContact_ValidContact_ShouldReturnCreated() {
        Contact contact = new Contact();

        when(contactService.addContact(any(Contact.class))).thenReturn(contact);

        ResponseEntity<Contact> response = contactController.addContact(contact);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(contact, response.getBody());
        verify(contactService, times(1)).addContact(any(Contact.class));
    }

    @Test
    void updateContact_ValidContact_ShouldReturnUpdatedContact() {
        long contactId = 1L;
        Contact contact = new Contact();
        contact.setId(contactId);

        when(contactService.updateContact(any(Contact.class))).thenReturn(contact);

        ResponseEntity<Contact> response = contactController.updateContact(contact);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(contact, response.getBody());
        verify(contactService, times(1)).updateContact(any(Contact.class));
    }

    @Test
    void deleteContact_ExistingId_ShouldReturnNoContent() {
        long contactId = 1L;

        ResponseEntity<Void> response = contactController.deleteContact(contactId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(contactService, times(1)).deleteContact(contactId);
    }
}

