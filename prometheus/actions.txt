http://localhost:8090/actuator/prometheus
http://localhost:8090/api/a
http://localhost:8090/api/b


minikube.exe start
minikube dashboard


helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo update

---- Вариант 1 - устанавливаем только сам прометей

helm show values prometheus-community/prometheus

helm install my-prometheus prometheus-community/prometheus -f prom-values.yaml

проброс порта к серверу
kubectl get pods --namespace default -l "app=prometheus,component=server
kubectl port-forward my-prometheus-server-7f5fff76b-52mvh 9090

..\..\minikube docker-env

docker build -t metrics-demo:v1 .


kubectl apply -f pod.yaml

kubectl port-forward metrics-demo 8090

неск раз
http://localhost:8090/api/b
http://localhost:8090/api/a


---------- Вариант 2 - устанавливаем весь стек - прометей + оператор + графана

helm install stack prometheus-community/kube-prometheus-stack -f c:\data\otus\msa\msa\prometheus\prometheus.yaml 

kubectl apply -f rs.yaml -f service.yaml -f service-monitor.yaml 

minikube.exe service metrics-demo

kubectl port-forward service/prometheus-operated  9090

ab -n 500 -c 50 http://localhost:8090/api/a # поменять урл на реальный (выхлоп minikube.exe service metrics-demo)

FOR /L %N IN () DO c:\data\otus\msa\Apache24\bin\ab.exe -n 50 -c 10 http://localhost:8090/api/a # аналогично поменять урл


----

http_server_requests_seconds_count
http_server_requests_seconds_count{uri=~"/api/a|/api/b"}[1m]

rate(http_server_requests_seconds_count{uri=~"/api/a|/api/b"}[1m])
sum (rate(http_server_requests_seconds_count{uri=~"/api/a|/api/b"}[1m]))
sum (rate(http_server_requests_seconds_count{uri=~"/api/a|/api/b"}[1m])) by (uri)

# квантиль 50
sum by (uri) (rate(http_server_requests_seconds_sum{uri=~"/api/.*"}[1m])) / sum by (uri) (rate(http_server_requests_seconds_count{uri=~"/api/.*"}[1m])) 

http_server_requests_seconds_bucket{uri=~"/api/a"}
rate(http_server_requests_seconds_bucket{uri=~"/api/a|/api/b"}[1m])


sum by(le) (http_server_requests_seconds_bucket{uri=~"/api/a|/api/b"})

sum by(le) (rate(http_server_requests_seconds_bucket{uri=~"/api/a|/api/b"}[1m]))
histogram_quantile(0.95, sum by(le) (rate(http_server_requests_seconds_bucket{uri=~"/api/a|/api/b"}[1m])))

histogram_quantile(0.95, sum by(le, uri) (rate(http_server_requests_seconds_bucket{uri=~"/api/.*"}[1m])))


---------

kubectl port-forward service/stack-grafana  9000:80
admin / prom-operator