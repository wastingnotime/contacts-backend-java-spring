package com.henriquericcio.contacts.controller;

import com.henriquericcio.contacts.model.Contact;
import com.henriquericcio.contacts.infra.http.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping("/")
@RestController
public class ContactController {
    static List<Contact> contactList = new ArrayList<>();

    public ContactController(){
        contactList.add(new Contact(UUID.randomUUID(), "Albert", "Einstein", "1111-1111"));
        contactList.add(new Contact(UUID.randomUUID(), "Marie", "Curie", "2222-1111"));
    }

    @PostMapping
    public ResponseEntity<?> addContact(@RequestBody Contact contact) {
        contact.setId(UUID.randomUUID());
        contactList.add(contact);
        return ResponseEntity.created(URI.create("/" + contact.getId())).build();
    }

    @GetMapping
    public List<Contact> getAllContacts() {
        return contactList;
    }

    @GetMapping(path = "{id}")
    public Contact getContactById(@PathVariable("id") UUID id) {
        return findContact(id).orElse(null);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> deleteContactById(@PathVariable("id") UUID id) throws NotFoundException {
        Optional<Contact> optionalContact = findContact(id);
        if (optionalContact.isEmpty()) {
            throw new NotFoundException();
        }
        contactList.remove(optionalContact.get());
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<?> updateContact(@PathVariable("id") UUID id, @RequestBody Contact contact) throws NotFoundException {
        Optional<Contact> optionalContact = findContact(id);
        if (optionalContact.isEmpty()) {
            throw new NotFoundException();
        }

        Contact currentContact = optionalContact.get();
        currentContact.setFirstName(contact.getFirstName());
        currentContact.setLastName(contact.getLastName());
        currentContact.setPhoneNumber(contact.getPhoneNumber());

        return ResponseEntity.noContent().build();
    }

    private Optional<Contact> findContact(UUID id) {
        return contactList.stream().filter(contact -> contact.getId().equals(id)).findFirst();
    }

}