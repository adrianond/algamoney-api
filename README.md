# algamoney-api

# run docker redis: docker run -it --name redis -p 6379:6379 redis:5.0.3

# postman autorização 
# gerar token JWT para acessar end points da API:
# Post: http://localhost:8093/oauth/token
# Authorization Basic: Username:angular e Password:@ngul@r0 
# Body: username:admin@algamoney.com - password:admin e grant_type:password

# gerar novo acess token com refresh token: 
# usar mesma URL do login, mesma Authorization Basic, porém altera o Body-> grant_type:refresh_token

# habilitar interface rabbit: rabbitmq-plugins.bat enable rabbitmq_management (executar na pasta C:\Program Files\RabbitMQ Server\rabbitmq_server-3.13.3\sbin)
# iniciar servico: rabbitmq-server.bat (executar na pasta C:\Program Files\RabbitMQ Server\rabbitmq_server-3.13.3\sbin)
# URL interface: http://localhost:15672
# usuario padrao:
# user: guest
# senha: guest

# REDIS - usando Redis do tporadowski
# Iniciar redis: redis-server.exe (descompactado em C:\develop\redis)
# testar: digitar no CMD "ping" deve retornar "pong"
# verificar key na memoria do redis: redis-cli.exe
# listar todas keys: keys *
# ver valores de keys: get minhaChave


# subir API no EC2 
java -jar /home/ec2-user/algamoney-api-0.0.1-SNAPSHOT.jar --spring.profiles.active=oauth-security