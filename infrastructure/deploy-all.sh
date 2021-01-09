kubectl apply -f secrets/registry-credentials.yaml

kubectl apply -f deployments/MTADroneService-auth-deployment.yaml
kubectl apply -f deployments/MTADroneService-frontend-deployment.yaml

kubectl apply -f configmaps/nginx-ingress-controller-configmap.yaml

kubectl apply -f roles/nginx-ingress-controller-account.yaml

kubectl apply -f services/MTADroneService-auth-service.yaml
kubectl apply -f services/MTADroneService-frontend-service.yaml

kubectl apply -f ingresses/MTADroneService-auth-ingresses.yaml
kubectl apply -f ingresses/MTADroneService-frontend-ingresses.yaml

kubectl apply -f deployments/nginx-ingress-controller.yaml

kubectl apply -f public-ingress.yaml
