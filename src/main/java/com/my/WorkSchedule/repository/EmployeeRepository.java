package com.my.WorkSchedule.repository;

import com.my.WorkSchedule.entity.Employee;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM TASK_EMPLOYEE WHERE ID = :employeeId", nativeQuery = true)
    void deleteTaskEmployeeByEmployeeId(@Param("employeeId") long employeeId);
}
