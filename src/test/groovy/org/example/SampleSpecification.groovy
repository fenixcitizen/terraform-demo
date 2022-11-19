package org.example

import spock.lang.Narrative
import spock.lang.Title

@Narrative("""As DevOps engineer
I want following specification to be true
so that deployments run with Terraform are reliable.
""")
@Title('Suite of features Terraform plan should have')
class SampleSpecification extends SpecificationForTerraformPlan {

    def "example_config_map specifies api_host on port 443"() {
        given: "example_config_map Kubernetes ConfigMap resource"
        def configMap = resourceByName('  example_config_map')

        and: "evidence record"
        recordText("API host of interest", api_host) // TODO: move api_host definition up from here
        recordJson("Kubernetes ConfigMap resource", configMap.toString())

        expect: "that it defines port 443 for api_host"
        def api_host = spec(configMap, 'data/api_host').textValue()
        api_host.endsWith('443')
    }

    def "nginx_helloworld_deployment uses latest image version"() {
        given: "Nginx Helloworld Kubernetes Deployment resource"
        def deployment = resourceByName('nginx-helloworld-deployment')
        def image = spec(deployment, 'spec/0/template/0/spec/0/container/0/image').textValue()

        and: "evidence record"
        recordText("Image URI", image)
        recordJson("Kubernetes Deployment resource", deployment.toString())

        expect: "that the image used in template spec is the latest one"
        image.endsWith('latest')
    }

    def "pods in nginx_helloworld_deployment use nginx-helloworld-service-account"() {
        given: "Nginx Helloworld Kubernetes Deployment resource"
        def deployment = resourceByName('nginx-helloworld-deployment')
        def sa = spec(deployment, 'spec/0/template/0/spec/0/service_account_name').textValue()

        and: "evidence record"
        recordText("Pod service account name", sa)
        recordJson("Kubernetes Deployment resource", deployment.toString())

        expect: "that the service account used in template spec is correct"
        sa.equals('nginx-helloworld-service-account')
    }

}
