package com.henriquericcio.contacts.repository;

import com.henriquericcio.contacts.model.Contact;
import org.springframework.data.repository.CrudRepository;

public interface ContactRepository extends CrudRepository<Contact, String> {
}