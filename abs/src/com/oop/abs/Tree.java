package com.oop.abs;

public interface Tree<E extends Comparable<E>>
{ int size();
    int depth();
    boolean member(E elem);
    Tree<E> insert(E elem);


}