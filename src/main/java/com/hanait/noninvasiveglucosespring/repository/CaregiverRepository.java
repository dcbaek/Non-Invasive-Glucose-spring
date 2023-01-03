package com.hanait.noninvasiveglucosespring.repository;

import com.hanait.noninvasiveglucosespring.model.Caregiver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaregiverRepository extends JpaRepository<Caregiver, Long> {

    boolean existsByPhoneNumber(String phoneNumber);
    Caregiver findByPhoneNumber(String phoneNumber);
}
