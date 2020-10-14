unset DB_HOST
unset DB_PORT
unset DB_USER
unset DB_PASSWORD
unset EMAIL_LOGIN
unset EMAIL_PASSWORD
unset PROFILE

export DB_HOST=localhost
export DB_PORT=5433
export DB_USER=root
export DB_PASSWORD=root

mvn spring-boot:run -Dspring-boot.run.profiles=dev
