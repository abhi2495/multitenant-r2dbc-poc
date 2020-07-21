package com.example.persistence.repository;

import com.example.persistence.entity.Grade;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GradeRepository {

  Mono<Grade> upsert(Grade grade);

  Flux<Grade> getAll();

  Mono<Boolean> checkIfGradeExistsForGivenId(String gradeId);
}
