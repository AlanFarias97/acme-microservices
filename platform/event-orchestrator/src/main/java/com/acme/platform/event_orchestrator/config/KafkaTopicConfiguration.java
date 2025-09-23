package com.acme.platform.event_orchestrator.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Configuración centralizada de topics de Kafka
 * Este servicio se encarga de crear automáticamente todos los topics necesarios
 */
@Configuration
@Slf4j
public class KafkaTopicConfiguration {
    
    private static final int DEFAULT_PARTITIONS = 3;
    private static final int DEFAULT_REPLICAS = 1; // Solo para desarrollo
    
    // ==================== HR EVENTS ====================
    
    @Bean
    public NewTopic hrEmployeeCreatedTopic() {
        return TopicBuilder.name("hr.employee.created")
                .partitions(DEFAULT_PARTITIONS)
                .replicas(DEFAULT_REPLICAS)
                .config("retention.ms", "604800000") // 7 días
                .config("compression.type", "snappy")
                .build();
    }
    
    @Bean
    public NewTopic hrEmployeeUpdatedTopic() {
        return TopicBuilder.name("hr.employee.updated")
                .partitions(DEFAULT_PARTITIONS)
                .replicas(DEFAULT_REPLICAS)
                .config("retention.ms", "604800000") // 7 días
                .config("compression.type", "snappy")
                .build();
    }
    
    @Bean
    public NewTopic hrEmployeeDeletedTopic() {
        return TopicBuilder.name("hr.employee.deleted")
                .partitions(DEFAULT_PARTITIONS)
                .replicas(DEFAULT_REPLICAS)
                .config("retention.ms", "2592000000") // 30 días (importante para auditoría)
                .config("compression.type", "snappy")
                .build();
    }
    
    @Bean
    public NewTopic hrDepartmentTopic() {
        return TopicBuilder.name("hr.department.events")
                .partitions(2) // Menos particiones, menos departments
                .replicas(DEFAULT_REPLICAS)
                .config("retention.ms", "604800000")
                .config("compression.type", "snappy")
                .build();
    }
    
    // ==================== BILLING EVENTS ====================
    
    @Bean
    public NewTopic billingInvoiceCreatedTopic() {
        return TopicBuilder.name("billing.invoice.created")
                .partitions(DEFAULT_PARTITIONS)
                .replicas(DEFAULT_REPLICAS)
                .config("retention.ms", "31536000000") // 1 año (legal)
                .config("compression.type", "snappy")
                .build();
    }
    
    @Bean
    public NewTopic billingInvoicePaidTopic() {
        return TopicBuilder.name("billing.invoice.paid")
                .partitions(DEFAULT_PARTITIONS)
                .replicas(DEFAULT_REPLICAS)
                .config("retention.ms", "31536000000") // 1 año
                .config("compression.type", "snappy")
                .build();
    }
    
    @Bean
    public NewTopic billingInvoiceCancelledTopic() {
        return TopicBuilder.name("billing.invoice.cancelled")
                .partitions(DEFAULT_PARTITIONS)
                .replicas(DEFAULT_REPLICAS)
                .config("retention.ms", "31536000000") // 1 año
                .config("compression.type", "snappy")
                .build();
    }
    
    // ==================== IDENTITY EVENTS ====================
    
    @Bean
    public NewTopic identityUserCreatedTopic() {
        return TopicBuilder.name("identity.user.created")
                .partitions(2)
                .replicas(DEFAULT_REPLICAS)
                .config("retention.ms", "2592000000") // 30 días
                .config("compression.type", "snappy")
                .build();
    }
    
    // ==================== DEAD LETTER TOPICS ====================
    
    @Bean
    public NewTopic deadLetterTopic() {
        return TopicBuilder.name("acme.dead-letter")
                .partitions(1)
                .replicas(DEFAULT_REPLICAS)
                .config("retention.ms", "2592000000") // 30 días para debugging
                .config("compression.type", "snappy")
                .build();
    }
}
