package com.myways.automatedtestexecutionframework.repository;

import com.myways.automatedtestexecutionframework.entity.TestCase;
import com.myways.automatedtestexecutionframework.entity.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, Long> {

}
