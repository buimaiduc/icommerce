package com.icommerce.app.shopping.product.service.rest.request;

import java.util.List;
import java.util.Map;

public class FilterCriteriaRequest {

    private Map<String, Object> equalCriteria;
    private Map<String, Object> notEqualCriteria;
    private Map<String, String> likeCriteria;
    private Map<String, List<String>> inCriteria;
    private Map<String, List<String>> notInCriteria;

    public FilterCriteriaRequest() {
        // Default constructor
    }

    public FilterCriteriaRequest(Map<String, Object> equalCriteria,
                                 Map<String, Object> notEqualCriteria,
                                 Map<String, String> likeCriteria,
                                 Map<String, List<String>> inCriteria,
                                 Map<String, List<String>> notInCriteria) {
        this.equalCriteria = equalCriteria;
        this.notEqualCriteria = notEqualCriteria;
        this.likeCriteria = likeCriteria;
        this.notInCriteria = notInCriteria;
        this.inCriteria = inCriteria;
    }

    public Map<String, Object> getEqualCriteria() {
        return equalCriteria;
    }

    public void setEqualCriteria(Map<String, Object> equalCriteria) {
        this.equalCriteria = equalCriteria;
    }

    public Map<String, Object> getNotEqualCriteria() {
        return notEqualCriteria;
    }

    public void setNotEqualCriteria(Map<String, Object> notEqualCriteria) {
        this.notEqualCriteria = notEqualCriteria;
    }

    public Map<String, String> getLikeCriteria() {
        return likeCriteria;
    }

    public void setLikeCriteria(Map<String, String> likeCriteria) {
        this.likeCriteria = likeCriteria;
    }

    public Map<String, List<String>> getNotInCriteria() {
        return notInCriteria;
    }

    public void setNotInCriteria(Map<String, List<String>> notInCriteria) {
        this.notInCriteria = notInCriteria;
    }

    public Map<String, List<String>> getInCriteria() {
        return inCriteria;
    }

    public void setInCriteria(Map<String, List<String>> inCriteria) {
        this.inCriteria = inCriteria;
    }
}
