## Redis Broker

Projeto feito para estudar o funcionamento do padrão Pub/Sub utilizando o ecosistema Spring e Redis. Diferentemente do padrão de *Message Queue* o Pub/Sub não utiliza uma fila para manter as chamadas, cada ponta entre um publisher e um subscriber ocorre de forma direta com sockets no processo de broadcast dos eventos. O publisher apenas envia a mensagem aos seus *n* subscribers, mas o subsciber não avisa ao publisher se ele recebeu ou não o evento. 

<img src="https://res.cloudinary.com/practicaldev/image/fetch/s--Q_RFMIEV--/c_limit%2Cf_auto%2Cfl_progressive%2Cq_auto%2Cw_880/https://miro.medium.com/max/501/1%2ATSLaLllP_DcPQQOTpXbzeA.png" alt="Representação visual do funcionamento da relação Pub/Sub">
<br><br>
Para rodar o projeto, dentro de cada projeto Java compilar com o maven:
<pre><code>mvn clean package</code></pre>
Subindo os containers:
<pre><code>docker-compose up</code></pre>
Requisitando a geração de mensagens por um subscriber:
<pre><code>curl http://localhost:8080/v1/producer</code></pre>
Enviando um JSON por um publisher:
<pre><code>curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"action":"dummy","description":"teste","sent_at":"2020-10-10"}' \
  http://localhost:8080/v1/producer</code></pre>


Tecnologias utilizadas:
- Spring Boot
- Spring MVC
- Spring Redis/Jedis
- Java Faker
- Docker
- Java 11