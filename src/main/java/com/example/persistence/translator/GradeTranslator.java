package com.example.persistence.translator;

import com.example.controller.contract.GradeRequestContract;
import com.example.persistence.entity.Grade;
import com.example.utils.BaseTranslator;

public class GradeTranslator extends BaseTranslator<GradeRequestContract, Grade> {
  @Override
  public Grade translateTo(GradeRequestContract gradeRequestContract) {
    if (gradeRequestContract == null) {
      return null;
    }
    Grade grade = new Grade();
    grade.setId(gradeRequestContract.getId());
    grade.setRole(gradeRequestContract.getRole());
    grade.setSalary(gradeRequestContract.getSalary());
    return grade;
  }
}
