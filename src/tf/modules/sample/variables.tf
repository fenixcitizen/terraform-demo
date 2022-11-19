variable "num_of_nginx_replicas" {
  description = "Specifies number of pods each Nginx deployment should have."
  type        = number
  validation {
    condition     = try(var.num_of_nginx_replicas > 1)
    error_message = "It is required to have more than one Nginx replica for higher availability."
  }
}