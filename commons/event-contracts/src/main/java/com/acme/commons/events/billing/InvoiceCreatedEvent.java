package com.acme.commons.events.billing;

import com.acme.commons.events.BaseEvent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InvoiceCreatedEvent extends BaseEvent {
    
    public static final String EVENT_TYPE = "billing.invoice.created";
    
    @NotBlank
    private String invoiceId;
    
    @NotBlank
    private String employeeId;
    
    @NotBlank
    private String clientName;
    
    @Positive
    private BigDecimal amount;
    
    @NotNull
    private LocalDate dueDate;
    
    @NotBlank
    private String status; // PENDING, PAID, CANCELLED
    
    private String description;
    
    public InvoiceCreatedEvent(String invoiceId, String employeeId, String clientName, 
                              BigDecimal amount, LocalDate dueDate, String description) {
        this.invoiceId = invoiceId;
        this.employeeId = employeeId;
        this.clientName = clientName;
        this.amount = amount;
        this.dueDate = dueDate;
        this.status = "PENDING";
        this.description = description;
        this.setEventType(EVENT_TYPE);
        this.setSource("billing-svc");
        this.enrichEvent();
    }
}
