package org.example

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Shared

class SpecificationForTerraformPlan extends SpecificationWithEvidence {

    private static final String CHILD_MODULES = 'child_modules'
    private static final String RESOURCES = 'resources'
    private static final ObjectMapper MAPPER = new ObjectMapper()
    private static final JsonNode EMPTY_NODE = MAPPER.createObjectNode()

    @Shared
    private JsonNode plan = MAPPER.readTree(new File('build/reports/tf/local/local.tf.plan.json'))

    JsonNode resourceByName(String resourceName) {
        return resourceByName(resourceName, plan.at('/planned_values/root_module'))
    }

    JsonNode resourceByName(String resourceName, JsonNode node) {
        if (node.isArray()) {
            for (JsonNode module : node) {
                if (module?.has(CHILD_MODULES)) {
                    def ret = resourceByName(resourceName, module.get(CHILD_MODULES))
                    if (ret != EMPTY_NODE) {
                        return ret
                    }
                }
                if (module?.has(RESOURCES)) {
                    for (JsonNode resource : module.get(RESOURCES)) {
                        if (resource.at('/values/metadata/0/name')?.textValue()?.equals(resourceName)) {
                            return resource
                        }
                    }
                }
            }
        }
        if (node?.has(CHILD_MODULES)) {
            def ret = resourceByName(resourceName, node.get(CHILD_MODULES))
            if (ret != EMPTY_NODE) {
                return ret
            }
        }
        if (node?.has(RESOURCES)) {
            for (JsonNode resource : node.get(RESOURCES)) {
                if (resource.at('/name')?.textValue()?.equals(resourceName)) {
                    return resource
                }
            }
        }
        return EMPTY_NODE
    }

    static JsonNode spec(JsonNode resource, String path) {
        resource.at('/values/' + path)
    }

}
