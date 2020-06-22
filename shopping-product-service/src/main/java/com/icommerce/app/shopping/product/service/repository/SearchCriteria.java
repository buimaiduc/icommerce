package com.icommerce.app.shopping.product.service.repository;

import java.util.List;
import java.util.Map;

public class SearchCriteria {

    private String key;
    private Object value;
    private List<Object> values;
    private Map<String, Object> keyValuePair;
    private SearchOperation operation;

    public SearchCriteria() {
        // default constructor
    }

    public SearchCriteria(String key, Object value, SearchOperation operation) {
        this.key = key;
        this.value = value;
        this.operation = operation;
    }

    public SearchCriteria(String key, List<Object> values, SearchOperation operation) {
        this.key = key;
        this.values = values;
        this.operation = operation;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public SearchOperation getOperation() {
        return operation;
    }

    public void setOperation(SearchOperation operation) {
        this.operation = operation;
    }

    public List<Object> getValues() {
        return values;
    }

    public void setValues(List<Object> values) {
        this.values = values;
    }

    public Map<String, Object> getKeyValuePair() {
        return keyValuePair;
    }

    public void setKeyValuePair(Map<String, Object> keyValuePair) {
        this.keyValuePair = keyValuePair;
    }
}
