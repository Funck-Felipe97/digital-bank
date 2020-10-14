unset DB_HOST
unset DB_PORT
unset DB_USER
unset DB_PASSWORD
unset EMAIL_LOGIN
unset EMAIL_PASSWORD

export DB_HOST=localhost
export DB_PORT=5432
export DB_USER=root
export DB_PASSWORD=root

export EMAIL_LOGIN=seu-email@gmail.com
export EMAIL_PASSWORD=seu-senha

mvn spring-boot:run -Dspring-boot.run.profiles=prod