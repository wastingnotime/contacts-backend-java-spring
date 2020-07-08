package com.henriquericcio.contacts.controller;

import com.henriquericcio.contacts.infra.http.NotFoundException;
import com.henriquericcio.contacts.model.Contact;
import com.henriquericcio.contacts.repository.ContactRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/contacts")
public class ContactController {
    static List<Contact> contactList = new ArrayList<>();
    private final ContactRepository contactRepository;

    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @PostMapping
    public ResponseEntity<?> addContact(@RequestBody Contact contact) {
        contact.setId(UUID.randomUUID().toString());
        contactRepository.save(contact);
        return ResponseEntity.created(URI.create("/" + contact.getId())).build();
    }

    @GetMapping
    public Iterable<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    @GetMapping(path = "{id}")
    public Contact getContactById(@PathVariable("id") String id) {
        return contactRepository.findById(id).orElse(null);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> deleteContactById(@PathVariable("id") String id) throws NotFoundException {
        Optional<Contact> optionalContact = contactRepository.findById(id);
        if (optionalContact.isEmpty()) {
            throw new NotFoundException();
        }
        contactRepository.delete(optionalContact.get());
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<?> updateContact(@PathVariable("id") String id, @RequestBody Contact contact) throws NotFoundException {
        Optional<Contact> optionalContact = contactRepository.findById(id);
        if (optionalContact.isEmpty()) {
            throw new NotFoundException();
        }

        Contact currentContact = optionalContact.get();
        currentContact.setFirstName(contact.getFirstName());
        currentContact.setLastName(contact.getLastName());
        currentContact.setPhoneNumber(contact.getPhoneNumber());

        contactRepository.save(currentContact);

        return ResponseEntity.noContent().build();
    }
}