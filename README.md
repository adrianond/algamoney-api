# algamoney-api

# run docker redis: docker run -it --name redis -p 6379:6379 redis:5.0.3

# postman autorização 
# gerar token JWT para acessar end points da API:
# Post: http://localhost:8093/oauth/token
# Authorization Basic: Username:angular e Password:@ngul@r0 
# Body: username:admin@algamoney.com - password:admin e grant_type:password

# gerar novo acess token com refresh token: 
# usar mesma URL do login, mesma Authorization Basic, porém altera o Body-> grant_type:refresh_token