package com.techelevator.custom.dao;

import com.techelevator.custom.model.Type;

import java.util.List;

public interface TypeDao {
    Type getTypeById(int typeId);
    List<Type> getTypes();
}
