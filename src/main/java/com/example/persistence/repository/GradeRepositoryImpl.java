package com.example.persistence.repository;

import static org.springframework.data.relational.core.query.Query.query;

import com.example.persistence.entity.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class GradeRepositoryImpl implements GradeRepository {

  private final R2dbcEntityTemplate r2dbcEntityTemplate;

  @Autowired
  public GradeRepositoryImpl(DatabaseClient databaseClient) {
    this.r2dbcEntityTemplate = new R2dbcEntityTemplate(databaseClient);
  }

  @Override
  public Mono<Grade> upsert(Grade grade) {
    return checkIfGradeExistsForGivenId(grade.getId())
        .flatMap(exists -> {
          if (exists) {
            return r2dbcEntityTemplate.update(grade);
          } else {
            return r2dbcEntityTemplate.insert(grade);
          }
        });
  }

  @Override
  public Flux<Grade> getAll() {
    return r2dbcEntityTemplate.select(query(Criteria.empty()), Grade.class);
  }

  @Override
  public Mono<Boolean> checkIfGradeExistsForGivenId(String gradeId) {
    return r2dbcEntityTemplate.exists(query(Criteria.where("id").is(gradeId)), Grade.class);
  }
}
