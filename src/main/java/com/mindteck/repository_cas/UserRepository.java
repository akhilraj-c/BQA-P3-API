package com.mindteck.repository_cas;


import com.mindteck.models_cas.User;
import com.mindteck.common.modules.user.model.rest.GetApplicationManagerListResponseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT usr FROM User usr WHERE usr.userId IN :userIds")
    List<User> findByUserIds(List<Long> userIds);

    User getByUsername(String username);

    User getByEmailId(String emailId);
    List<User> getUsersByEmailId(String emailId);

    User getByUserId(Long userId);

    @Query(
            value = "SELECT new com.mindteck.common.modules.user.model.rest.GetApplicationManagerListResponseModel" +
                    " ( " +
                    " u.userId, " +
                    " u.username, " +
                    " u.emailId, " +
                    " u.bio," +
                    " u.active" +
                    " ) " +
                    " FROM User u JOIN u.roles r WHERE r.userType = 3 and r.subType IN (1,2)", // WHERE u.userType = 3 and u.subType IN (1,2)
            countQuery = "SELECT COUNT(*) FROM User u JOIN u.roles r WHERE r.userType = 3 and r.subType IN (1,2)" // WHERE u.userType = 3 and u.subType IN (1,2)
    )
    Page<GetApplicationManagerListResponseModel> getApplicationManager(Pageable pageable);

    @Query(
            value = "SELECT DISTINCT u FROM User u JOIN FETCH u.roles r WHERE r.userType NOT IN (1)",
            countQuery = "SELECT COUNT(DISTINCT u) FROM User u JOIN  u.roles r WHERE r.userType NOT IN (1)"
    )
    Page<User> getUserDetails(Pageable pageable);

    @Query(
            value = "SELECT DISTINCT u FROM User u JOIN FETCH u.roles r " +
                    "WHERE r.userType = 1 AND r.subType = 0 " +
                    "AND (:searchValue IS NULL OR LOWER(u.emailId) LIKE LOWER(CONCAT('%', :searchValue, '%')) " +
                    "OR LOWER(u.contactNumber) LIKE LOWER(CONCAT('%', :searchValue, '%')) " +
                    "OR LOWER(u.username) LIKE LOWER(CONCAT('%', :searchValue, '%')))",
            countQuery = "SELECT COUNT(DISTINCT u) FROM User u JOIN u.roles r " +
                    "WHERE r.userType = 1 AND r.subType = 0 " +
                    "AND (:searchValue IS NULL OR LOWER(u.emailId) LIKE LOWER(CONCAT('%', :searchValue, '%')) " +
                    "OR LOWER(u.contactNumber) LIKE LOWER(CONCAT('%', :searchValue, '%')) " +
                    "OR LOWER(u.username) LIKE LOWER(CONCAT('%', :searchValue, '%')))"
    )
    Page<User> getInstituteUserDetails( Pageable pageable , String searchValue);



    @Query(value = "SELECT u FROM User u WHERE u.userId IN (:userIds)")
    List<User> getUserByUserId(List<Long> userIds);

    @Query(value = "SELECT DISTINCT u FROM User u JOIN FETCH u.roles r " +
            "WHERE u.active = :active AND r.userType = :type AND r.subType = :subType",
            countQuery = "SELECT DISTINCT u FROM User u JOIN FETCH u.roles r WHERE u.active = :active AND r.userType = :type AND r.subType = :subType")
    Page<User> getUsersByTypeAndSubType(Integer type, Integer subType, Integer active, Pageable pageable);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.userType = :type AND r.subType = :subType")
    List<User> getByUserTypeAndSubType(Integer type, Integer subType);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.userType = :type")
    List<User> getByUserType(Integer type);

    // User getByUserType(Integer type);
    @Query(
            value = "SELECT DISTINCT u FROM User u JOIN FETCH u.roles r WHERE r.userType IN (3,7)", // WHERE  u.userType IN (3,7)
            countQuery = "SELECT COUNT(DISTINCT u) FROM User u JOIN u.roles r WHERE r.userType IN (3,7)" //WHERE u.userType NOT IN (3,7)
    )
    Page<User> getLimitedUserDetails(Pageable pageable);


    @Query(
            value = "SELECT DISTINCT u FROM User u JOIN FETCH u.roles r WHERE r.userType = 7 AND r.subType = 0 AND (:active IS NULL OR u.active = :active)",
            countQuery = "SELECT COUNT(DISTINCT u) FROM User u JOIN  u.roles r WHERE r.userType = 7 AND r.subType = 0 AND (:active IS NULL OR u.active = :active)"
    )
    Page<User> getILEPUserDetails(Integer active,Pageable pageable);

    @Query(
            value = "SELECT DISTINCT u FROM User u JOIN FETCH " +
                    "u.roles r WHERE NOT (r.userType = 1 AND r.subType = 0 ) " +
                    "AND " +
                    "(:active IS NULL OR u.active = :active) AND u.userId!=1"+
                    "AND " +
                    "(:searchValue IS NULL OR " +
                    "LOWER(u.username) LIKE LOWER(CONCAT('%', :searchValue, '%')) OR " +
                    "LOWER(u.emailId) LIKE LOWER(CONCAT('%', :searchValue, '%')) OR " +
                    "LOWER(u.position) LIKE LOWER(CONCAT('%', :searchValue, '%')) OR " +
                    "u.contactNumber LIKE CONCAT('%', :searchValue, '%'))",
            countQuery = "SELECT COUNT(DISTINCT u) FROM User u JOIN " +
                    "u.roles r WHERE NOT (r.userType = 1 AND r.subType = 0 )" +
                    "AND " +
                    "(:active IS NULL OR u.active = :active) AND u.userId!=1 " +
                    "AND " +
                    "(:searchValue IS NULL OR " +
                    "LOWER(u.username) LIKE LOWER(CONCAT('%', :searchValue, '%')) OR " +
                    "LOWER(u.emailId) LIKE LOWER(CONCAT('%', :searchValue, '%')) OR " +
                    "LOWER(u.position) LIKE LOWER(CONCAT('%', :searchValue, '%')) OR " +
                    "u.contactNumber LIKE CONCAT('%', :searchValue, '%'))"

    )
    Page<User> findNonAdminNonInstituteUsers(Integer active,Pageable pageable,String searchValue);
}