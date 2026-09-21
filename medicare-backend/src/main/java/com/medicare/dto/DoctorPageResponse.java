package com.medicare.dto;

import java.util.List;

public class DoctorPageResponse {

    private List<DoctorResponse> doctors;

    private int pageNumber;
    private int pageSize;

    private long totalElements;
    private int totalPages;

    private boolean last;

    public DoctorPageResponse(
            List<DoctorResponse> doctors,
            int pageNumber,
            int pageSize,
            long totalElements,
            int totalPages,
            boolean last) {

        this.doctors = doctors;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
    }

    public List<DoctorResponse> getDoctors() {
        return doctors;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public boolean isLast() {
        return last;
    }
}