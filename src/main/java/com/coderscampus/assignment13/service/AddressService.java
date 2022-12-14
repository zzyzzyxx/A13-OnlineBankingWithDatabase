package com.coderscampus.assignment13.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.assignment13.domain.Address;
import com.coderscampus.assignment13.repository.AddressRepository;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    public Address save(Address address) {
        return addressRepository.save(address);
    }

    public Address findById(Long addressId) {
        Optional<Address> optAddress = addressRepository.findById(addressId);
        if (optAddress.isPresent()) {
            return optAddress.get();
        } else{
            throw new RuntimeException("Did not find ID" + addressId);
        }

    }
}
