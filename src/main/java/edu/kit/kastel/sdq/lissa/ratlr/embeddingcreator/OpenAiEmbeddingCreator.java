/* Licensed under MIT 2025-2026. */
package edu.kit.kastel.sdq.lissa.ratlr.embeddingcreator;

import edu.kit.kastel.sdq.lissa.ratlr.configuration.ModuleConfiguration;
import edu.kit.kastel.sdq.lissa.ratlr.context.ContextStore;

import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;

/**
 * An embedding creator that uses OpenAI's embedding models for generating embeddings.
 * This class provides integration with OpenAI's embedding API, supporting high-throughput
 * embedding generation through parallel processing.
 * <p>
 * Valores requeridos de ProviderConfiguration (entorno solo en el modo autónomo):
 * <ul>
 *     <li>{@code OPENAI_ORGANIZATION_ID}: Your OpenAI organization ID</li>
 *     <li>{@code OPENAI_API_KEY}: Your OpenAI API key</li>
 * </ul>
 *
 * The default model used is "text-embedding-ada-002", but this can be overridden
 * through the configuration. The creator uses 40 threads by default for parallel
 * processing of embedding requests.
 */
public class OpenAiEmbeddingCreator extends CachedEmbeddingCreator {
    /** Default number of threads for parallel processing */
    private static final int THREADS = 40;

    /**
     * Creates a new OpenAI embedding creator with the specified configuration.
     * The configuration can specify a custom model name, otherwise the default
     * "text-embedding-ada-002" is used.
     *
     * @param configuration The configuration containing model settings
     * @param contextStore The shared context store for pipeline components
     */
    public OpenAiEmbeddingCreator(ModuleConfiguration configuration, ContextStore contextStore) {
        super(contextStore, configuration.argumentAsString("model", "text-embedding-ada-002"), THREADS);
    }

    /**
     * Creates an OpenAI embedding model instance with the specified parameters.
     * Usa el organization ID y la API key de la configuración de proveedores.
     *
     * @param model The name of the OpenAI model to use
     * @param params Additional parameters (not used in this implementation)
     * @return A configured OpenAI embedding model instance
     * @throws IllegalArgumentException si falta OPENAI_ORGANIZATION_ID u OPENAI_API_KEY
     */
    @Override
    protected EmbeddingModel createEmbeddingModel(String model, String... params) {
        String openAiOrganizationId = providers.require("OPENAI_ORGANIZATION_ID");
        String openAiApiKey = providers.require("OPENAI_API_KEY");
        return new OpenAiEmbeddingModel.OpenAiEmbeddingModelBuilder()
                .modelName(model)
                .organizationId(openAiOrganizationId)
                .apiKey(openAiApiKey)
                .maxRetries(0)
                .build();
    }
}
