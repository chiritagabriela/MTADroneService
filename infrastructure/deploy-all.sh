kubectl apply -f secrets/registry-credentials.yaml

kubectl apply -f deployments/MTADroneService-server-deployment.yaml
kubectl apply -f deployments/MTADroneService-interface-deployment.yaml

kubectl apply -f configmaps/nginx-ingress-controller-configmap.yaml

kubectl apply -f roles/nginx-ingress-controller-account.yaml

kubectl apply -f services/MTADroneService-server-service.yaml
kubectl apply -f services/MTADroneService-interface-service.yaml

kubectl apply -f ingresses/MTADroneService-server-ingresses.yaml
kubectl apply -f ingresses/MTADroneService-interface-ingresses.yaml

kubectl apply -f deployments/nginx-ingress-controller.yaml

kubectl apply -f public-ingress.yaml
