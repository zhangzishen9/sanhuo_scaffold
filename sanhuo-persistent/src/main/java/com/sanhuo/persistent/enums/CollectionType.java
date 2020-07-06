package com.sanhuo.persistent.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 列表类型
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/6/13:14:26
 */
@AllArgsConstructor
public enum CollectionType {

    LIST(List.class.getName(), LinkedList.class),
    SET(Set.class.getName(), LinkedHashSet.class);


    public final String type;

    public final Class value;

}
