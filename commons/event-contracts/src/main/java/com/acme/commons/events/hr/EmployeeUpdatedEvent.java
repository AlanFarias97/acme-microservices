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
import java.util.Map;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EmployeeUpdatedEvent extends BaseEvent {
    
    public static final String EVENT_TYPE = "hr.employee.updated";
    
    @NotBlank
    private String employeeId;
    
    // Datos actuales
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
    
    private boolean isActive;
    
    private String managerId;
    
    // Qué campos cambiaron (útil para consumers)
    private Map<String, Object> changedFields;
    
    // Valores anteriores (opcional, para audit)
    private Map<String, Object> previousValues;
    
    public EmployeeUpdatedEvent(String employeeId, String fullName, String email, 
                               String department, String position, BigDecimal salary, 
                               boolean isActive, String managerId, 
                               Map<String, Object> changedFields) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.email = email;
        this.department = department;
        this.position = position;
        this.salary = salary;
        this.isActive = isActive;
        this.managerId = managerId;
        this.changedFields = changedFields;
        this.setEventType(EVENT_TYPE);
        this.setSource("hr-svc");
        this.enrichEvent();
    }
}
