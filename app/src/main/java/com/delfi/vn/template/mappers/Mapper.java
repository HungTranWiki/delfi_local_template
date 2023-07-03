package com.delfi.vn.template.mappers;

import java.util.ArrayList;
import java.util.List;

public abstract class Mapper<K,T> {

    public abstract T map(K element);
    public abstract K reverseMap(T element);

    public List<T> mapList(List<K> list) {
        List<T> returnedList = new ArrayList<>();
        for (K element : list) {
            returnedList.add(map(element));
        }
        return returnedList;
    }

    public List<K> reverseMapList(List<T> list) {
        List<K> returnedList = new ArrayList<>();
        for (T element : list) {
            returnedList.add(reverseMap(element));
        }
        return returnedList;
    }
}
