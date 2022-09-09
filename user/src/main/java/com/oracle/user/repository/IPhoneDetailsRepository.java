package com.oracle.user.repository;

import com.oracle.user.model.PhoneDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPhoneDetailsRepository extends JpaRepository<PhoneDetails, Integer> {
}
