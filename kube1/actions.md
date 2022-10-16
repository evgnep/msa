https://kubernetes.io/ru/docs/setup/learning-environment/minikube

`minikube start --vm-driver=virtualbox`

`minikube dashboard`

```
kubectl create namespace kub1
kubectl config set-context --current --namespace=kub1
```

# Поды

Все поды 
`kubectl get pods -A`

Поды в текущем namespace
`kubectl get pods`

`minikube.exe docker-env`

в папке c:\data\otus\msa\arch-labs\pod\hello-py (скорректируйте путь к репу)
`docker build -t hello-py:v1 .`

в папке c:\data\otus\msa\arch-labs\pod
`kubectl apply -f pod.yaml`

## Как посмотреть - вариант 1

`kubectl port-forward hello-demo 8000:80`
открываем браузер на localhost:8000

## Как посмотреть - вариант 2
```
minikube ssh
curl ip
```

# replicaset
`kubectl apply -f c:\data\otus\msa\arch-labs\replicaset\rs.yaml`

меняем кол-во подов через ui, смотрим результаты

убиваем rs

## deployment
```
cd c:\data\otus\msa\arch-labs\deployment\hello-py
docker build -t hello-py:v2 .
```

меняем `c:\data\otus\msa\arch-labs\deployment\deployment.yaml` 
`image: hello-py:v1`

`kubectl apply -f c:\data\otus\msa\arch-labs\deployment\deployment.yaml` 

меняем версию
`kubectl set image deployment/hello-deployment hello-py=hello-py:v2 --record`
смотрим поды

смотрим историю
`kubectl rollout history deployment/hello-deployment`
