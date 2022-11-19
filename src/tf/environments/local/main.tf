// Specify backend and providers needed
terraform {
  backend "local" {}
  required_providers {
    kubernetes = {
      source  = "hashicorp/kubernetes"
      version = "2.15.0"
    }
  }
}

// Configure Terraform Kubernetes Provider
provider "kubernetes" {
  config_path     = "~/.kube/config"
  config_context  = "docker-desktop" // there are alternatives to Docker Desktop
}

// Example resource created outside any TF module
resource "kubernetes_config_map_v1" "example_config_map" {
  metadata {
    name = "my-config"
  }
  data = {
    api_host = "myhost:443"
  }
}

// Sample module definition (it may contain other modules)
module "nginx-helloworld" {
  source = "../../modules/sample"
  // TODO: try reducing num of replicas to 1 and run TF apply
  num_of_nginx_replicas = 2
}