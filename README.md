# Terraform Demo

Sample Terraform project demonstrating how to use following tools together:

* [Gradle](https://docs.gradle.org/current/userguide/userguide.html) as build tool
* [Terraform](https://developer.hashicorp.com/terraform/docs) for managing cloud resources
* [Terraform Gradle Plugin](https://plugins.gradle.org/plugin/org.ysb33r.terraform) for downloading Terraform client and running Terraform commands using Gradle tasks
* [Terraform Kuberentes Provider](https://registry.terraform.io/providers/hashicorp/kubernetes/2.15.0) for declaring K8s resources with HCL
* [Spock](https://spockframework.org/spock/docs/2.3/index.html) for Terraform scripts testing
* [Spock Reports Extension](https://github.com/renatoathaydes/spock-reports) for generating comprehensive test reports
* [Docker Desktop](https://www.docker.com/products/docker-desktop/) as local K8s cluster provider

Please consider using [K9s](https://k9scli.io) to navigate and observe K8s cluster shipped by Docker Desktop.

### How to run deployment

First, pull the [Nginx Helloworld image](https://hub.docker.com/r/dockerbogo/docker-nginx-hello-world) from DockerHub:

```shell
docker pull dockerbogo/docker-nginx-hello-world
```

Subsequently, add `g` script to your `Path` env variable for convenience and run following Gradle tasks:

```shell
g tfLocalInit
g tfLocalPlan
g tfLocalApply
```

### How to run tests

```shell
g test
```

Please note tests will fail for demonstration. 
Check Spock report generated and fix them as per TODO instructions throughout the project to go from red to green.
