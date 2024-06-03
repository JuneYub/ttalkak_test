package com.ssafy.happyhouse.repository;

import com.ssafy.happyhouse.entity.store.Store;
import com.ssafy.happyhouse.request.AddressName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, String> {

}
