/* Licensed under MIT 2025-2026. */
package edu.kit.kastel.sdq.lissa.ratlr.cache;

import java.util.concurrent.atomic.AtomicInteger;

import edu.kit.kastel.sdq.lissa.ratlr.context.Context;

/** Cache usage for one evaluation, shared with its parallel workers. */
public final class CacheUsage implements Context {
    public static final String ID = "cache-usage";

    private final AtomicInteger embeddingHits = new AtomicInteger();
    private final AtomicInteger embeddingMisses = new AtomicInteger();
    private final AtomicInteger classificationHits = new AtomicInteger();
    private final AtomicInteger classificationMisses = new AtomicInteger();

    @Override
    public String getId() {
        return ID;
    }

    public void embeddingHit() {
        embeddingHits.incrementAndGet();
    }

    public void embeddingMiss() {
        embeddingMisses.incrementAndGet();
    }

    public void classificationHit() {
        classificationHits.incrementAndGet();
    }

    public void classificationMiss() {
        classificationMisses.incrementAndGet();
    }

    public int embeddingHits() {
        return embeddingHits.get();
    }

    public int embeddingMisses() {
        return embeddingMisses.get();
    }

    public int classificationHits() {
        return classificationHits.get();
    }

    public int classificationMisses() {
        return classificationMisses.get();
    }
}
