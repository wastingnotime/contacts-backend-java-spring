package org.wastingnotime.contacts.repository;

import org.wastingnotime.contacts.model.Contact;
import org.springframework.data.repository.CrudRepository;

public interface ContactRepository extends CrudRepository<Contact, String> {
}