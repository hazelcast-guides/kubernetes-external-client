function(request) {
  local pod = request.object,
  local labelKey = pod.metadata.annotations["service-per-pod-label"],

  // Create a service for each Pod, with a selector on the given label key.
  attachments: [
    {
      apiVersion: "v1",
      kind: "Service",
      metadata: {
        name: pod.metadata.name,
        labels: {app: "service-per-pod"}
      },
      spec: {
        type: "LoadBalancer",
        selector: {
          [labelKey]: pod.metadata.name
        },
        publishNotReadyAddresses: true,
        ports: [
          {
            port: 5701,
            targetPort: 5701
          }
        ]
      }
    }

  ]
}
