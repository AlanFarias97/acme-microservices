package com.acme.commons.kafka;

import com.acme.commons.events.BaseEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventPublisher {
    
    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    /**
     * Publica un evento de forma asíncrona
     * 
     * @param topic Kafka topic
     * @param key Partition key (null para round-robin)
     * @param event Evento a publicar
     * @return Future con resultado
     */
    public CompletableFuture<SendResult<String, Object>> publishEvent(String topic, String key, BaseEvent event) {
        // Enriquecer evento con metadata
        event.enrichEvent();
        
        log.info("Publishing event {} to topic {} with key {}", 
                event.getEventType(), topic, key);
        
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, key, event);
        
        future.thenAccept(result -> {
            log.info("Event {} published successfully to topic {} partition {} offset {}", 
                    event.getEventId(), 
                    result.getRecordMetadata().topic(),
                    result.getRecordMetadata().partition(),
                    result.getRecordMetadata().offset());
        }).exceptionally(ex -> {
            log.error("Failed to publish event {} to topic {}: {}", 
                    event.getEventId(), topic, ex.getMessage(), ex);
            return null;
        });
        
        return future;
    }
    
    /**
     * Publica evento con key automática basada en el contenido del evento
     */
    public CompletableFuture<SendResult<String, Object>> publishEvent(String topic, BaseEvent event) {
        String key = extractPartitionKey(event);
        return publishEvent(topic, key, event);
    }
    
    /**
     * Extrae la key de particionado del evento
     * Por defecto usa el source del evento, pero puede ser overrideado
     */
    protected String extractPartitionKey(BaseEvent event) {
        // Estrategia por defecto: usar el source
        return event.getSource();
    }
}
