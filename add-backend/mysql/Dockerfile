FROM mysql:5.7

COPY ./init/init.sql /docker-entrypoint-initdb.d/init.sql
# COPY ./conf/mycustom.cnf /etc/mysql/conf.d/custom.cnf
EXPOSE 3306
# RUN chown -R mysql:mysql /docker-entrypoint-initdb.d/

CMD  ["mysqld"]