/* Licensed under MIT 2025-2026. */
package edu.kit.kastel.sdq.lissa.ratlr.cache;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import edu.kit.kastel.sdq.lissa.ratlr.context.ContextStore;

class CacheUsageTest {
    @Test
    void sharesCountersAcrossParallelWorkers() {
        var contexts = new ContextStore();
        var usage = new CacheUsage();
        contexts.createContext(usage);

        IntStream.range(0, 1000).parallel().forEach(i -> {
            var shared = contexts.getContext(CacheUsage.ID, CacheUsage.class);
            shared.embeddingHit();
            shared.classificationMiss();
        });

        assertSame(usage, contexts.getContext(CacheUsage.ID, CacheUsage.class));
        assertEquals(1000, usage.embeddingHits());
        assertEquals(0, usage.embeddingMisses());
        assertEquals(0, usage.classificationHits());
        assertEquals(1000, usage.classificationMisses());
    }
}
