
resource "kubernetes_service_account" "nginx_helloworld_service_account" {
  metadata {
    name = "nginx-helloworld-service-account"
  }
  automount_service_account_token = true
}

resource "kubernetes_deployment" "nginx_helloworld_deployment" {
  metadata {
    name = "nginx-helloworld-deployment"
  }
  spec {
    replicas = var.num_of_nginx_replicas
    selector {
      match_labels = {
        app = "nginx-helloworld"
      }
    }
    template {
      metadata {
        labels = {
          app = "nginx-helloworld"
        }
      }
      spec {
        service_account_name="default"
        # TODO: uncomment following line to fix TF test
#        service_account_name = kubernetes_service_account.nginx_helloworld_service_account.metadata[0].name
        container {
          name = "nginx-helloworld-server"
          image = "dockerbogo/docker-nginx-hello-world:latest"
          port {
            container_port = 80
          }
        }
      }
    }
  }
}