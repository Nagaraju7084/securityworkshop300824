/**
 * 
 */
package com.medilab.preclinic.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.medilab.preclinic.model.Address;

/**
 * @author nkailasa
 *
 */
public interface AddressRepo extends JpaRepository<Address, Integer> {
	@Query("from Address d where d.address=:address")
	public List<Address> findAddressByName(@Param("address") String address);
}
