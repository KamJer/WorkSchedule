package com.my.WorkSchedule.service;

import com.my.WorkSchedule.entity.Contact;
import com.my.WorkSchedule.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    /**
     * Retrieves all contacts from the contact repository.
     *
     * @return List of contacts
     */
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    /**
     * Retrieves a contact by its ID from the contact repository.
     *
     * @param id ID of the contact
     * @return Optional containing the contact if found, empty otherwise
     */
    public Optional<Contact> getContactById(long id) {
        return contactRepository.findById(id);
    }

    /**
     * Adds a new contact to the contact repository.
     *
     * @param contact Contact to be added
     * @return The added contact
     */
    public Contact addContact(Contact contact) {
        return contactRepository.save(contact);
    }

    /**
     * Updates an existing contact in the contact repository.
     *
     * @param contact Contact to be updated
     * @return The updated contact
     */
    public Contact updateContact(Contact contact) {
        return contactRepository.save(contact);
    }

    /**
     * Deletes a contact by its ID from the contact repository.
     * Also deletes any associated task contacts.
     *
     * @param id ID of the contact to be deleted
     */
    public void deleteContact(long id) {
        contactRepository.deleteTaskContactByContactId(id);
        contactRepository.deleteById(id);
    }
}
