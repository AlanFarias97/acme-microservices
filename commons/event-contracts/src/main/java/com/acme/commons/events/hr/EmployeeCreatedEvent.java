package com.acme.commons.events.hr;

import com.acme.commons.events.BaseEvent;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EmployeeCreatedEvent extends BaseEvent {
    
    public static final String EVENT_TYPE = "hr.employee.created";
    
    @NotBlank
    private String employeeId;
    
    @NotBlank
    private String fullName;
    
    @Email
    @NotBlank
    private String email;
    
    @NotBlank
    private String department;
    
    @NotBlank
    private String position;
    
    @Positive
    private BigDecimal salary;
    
    private boolean isActive = true;
    
    @NotBlank
    private String managerId; // puede ser null para CEOs
    
    public EmployeeCreatedEvent(String employeeId, String fullName, String email, 
                               String department, String position, BigDecimal salary, String managerId) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.email = email;
        this.department = department;
        this.position = position;
        this.salary = salary;
        this.managerId = managerId;
        this.setEventType(EVENT_TYPE);
        this.setSource("hr-svc");
        this.enrichEvent();
    }
}
