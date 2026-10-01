/* Licensed under MIT 2026. */
package edu.kit.kastel.sdq.lissa.ratlr.configuration;

import java.util.Map;
import java.util.function.Function;

import org.jspecify.annotations.Nullable;

import edu.kit.kastel.sdq.lissa.ratlr.context.Context;
import edu.kit.kastel.sdq.lissa.ratlr.context.ContextStore;
import edu.kit.kastel.sdq.lissa.ratlr.utils.Environment;

/** Valores de conexión por evaluación, separados de la configuración serializable del pipeline. */
public final class ProviderConfiguration implements Context {
    public static final String ID = "providerConfiguration";
    private final Function<String, @Nullable String> values;

    /** Copia los valores explícitos; una clave ausente nunca se busca en el entorno. */
    public ProviderConfiguration(Map<String, String> values) {
        this.values = Map.copyOf(values)::get;
    }

    private ProviderConfiguration(Function<String, @Nullable String> values) {
        this.values = values;
    }

    /** Compatibilidad con los puntos de entrada autónomos de LiSSA. */
    public static ProviderConfiguration from(ContextStore contextStore) {
        var configuration = contextStore.getContext(ID, ProviderConfiguration.class);
        return configuration == null ? new ProviderConfiguration(Environment::getenv) : configuration;
    }

    public @Nullable String get(String key) {
        return values.apply(key);
    }

    public String require(String key) {
        String value = get(key);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Falta configuración del proveedor: " + key);
        }
        return value;
    }

    @Override
    public String getId() {
        return ID;
    }
}
