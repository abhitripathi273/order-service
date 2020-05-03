package com.demo.orderservice.exception;


import java.time.LocalDate;

public class ExceptionResponse {

    private String message;
    private String details;
    private LocalDate date;

    protected ExceptionResponse() {
    }

    /**
     * @param message
     * @param details
     * @param date
     */
    public ExceptionResponse(String message, String details, LocalDate date) {
        super();
        this.message = message;
        this.details = details;
        this.date = date;
    }

    /**
     * @return the messgae
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return the details
     */
    public String getDetails() {
        return details;
    }

    /**
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "ExceptionResponse [message=" + message + ", details=" + details + ", date=" + date + "]";
    }

}
