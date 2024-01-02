package com.nagarro.repositories;

import java.util.List;  

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nagarro.entities.UserTable;

@Repository
public interface UserTableRepo extends JpaRepository<UserTable, Long> {

	@Query(value = "SELECT COUNT(id) FROM usertable", nativeQuery = true)
    long getTotalUserCount();
	
    @Query(value = "SELECT * FROM (SELECT * FROM USERTABLE LIMIT :limit OFFSET :offset) AS subquery ORDER BY MOD(age,2),age", nativeQuery = true)
    List<UserTable> findAllEvenAgesWithLimitAndOffset(int limit, int offset);

    @Query(value = "SELECT * FROM (SELECT * FROM USERTABLE LIMIT :limit OFFSET :offset) AS subquery ORDER BY MOD(age,2) DESC,age", nativeQuery = true)
    List<UserTable> findAllOddAgesWithLimitAndOffset(int limit, int offset);

    @Query(value = "SELECT * FROM (SELECT * FROM USERTABLE LIMIT :limit OFFSET :offset) AS subquery ORDER BY MOD(CHAR_LENGTH(name),2),CHAR_LENGTH(name)", nativeQuery = true)
    List<UserTable> findAllNamesWithEvenCharacterCount(int limit, int offset);

    @Query(value = "SELECT * FROM (SELECT * FROM USERTABLE LIMIT :limit OFFSET :offset) AS subquery ORDER BY MOD(CHAR_LENGTH(name),2) DESC,CHAR_LENGTH(name)", nativeQuery = true)
    List<UserTable> findAllNamesWithOddCharacterCount(int limit, int offset);
}

