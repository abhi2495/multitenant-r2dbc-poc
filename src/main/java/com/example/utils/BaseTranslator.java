package com.example.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class BaseTranslator<F, T> {

  public final List<T> translateTo(Collection<? extends F> froms) {
    List<T> tos = new ArrayList<>();
    if (froms != null) {
      for (F from : froms) {
        tos.add(translateTo(from));
      }
    }
    return tos;
  }

  public final List<T> translateTo(Iterator<? extends F> froms) {
    List<T> tos = new ArrayList<>();
    if (froms != null) {
      while (froms.hasNext()) {
        tos.add(translateTo(froms.next()));
      }
    }
    return tos;
  }

  public final List<T> translateTo(F[] froms) {
    List<T> tos = new ArrayList<>();
    if (froms != null) {
      for (F from : froms) {
        tos.add(translateTo(from));
      }
    }
    return tos;
  }

  public abstract T translateTo(F from);

  public final List<F> translateFrom(Collection<? extends T> tos) {
    List<F> froms = new ArrayList<>();
    if (tos != null) {
      for (T to : tos) {
        froms.add(translateFrom(to));
      }
    }
    return froms;
  }

  public final List<F> translateFrom(Iterator<? extends T> tos) {
    List<F> froms = new ArrayList<>();
    if (tos != null) {
      while (tos.hasNext()) {
        froms.add(translateFrom(tos.next()));
      }
    }
    return froms;
  }

  public final List<F> translateFrom(T[] tos) {
    List<F> froms = new ArrayList<>();
    if (tos != null) {
      for (T to : tos) {
        froms.add(translateFrom(to));
      }
    }
    return froms;
  }

  public F translateFrom(T to) {
    throw new IllegalStateException("The method translateFrom must be overridden.");
  }
}
