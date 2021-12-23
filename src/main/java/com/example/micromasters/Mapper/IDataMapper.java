package com.example.micromasters.Mapper;

public interface IDataMapper<T> {
    public String serialize(T entity);

    public T deserialize(String data);
}
