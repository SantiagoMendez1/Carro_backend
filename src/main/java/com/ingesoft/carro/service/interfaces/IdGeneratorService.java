package com.ingesoft.carro.service.interfaces;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;

@Service
public class IdGeneratorService {
    private AtomicLong cartIdSequence = new AtomicLong(1);
    private AtomicLong itemIdSequence = new AtomicLong(1);
    
    public Long generateCartId() {
        return cartIdSequence.getAndIncrement();
    }
    
    public Long generateItemId() {
        return itemIdSequence.getAndIncrement();
    }
}