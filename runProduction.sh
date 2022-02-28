mvn package -DskipTests

export BUCKET_NAME=practica-1.cloud.aquesadas2016
export RDS_ENDPOINT=mca-db.c2mssszavdcr.eu-west-1.rds.amazonaws.com

export REGION=eu-west-1
export RDS_DATABASE=events_db
export RDS_PASS=password
export RDS_USER=admin

java -jar -Dspring.profiles.active=production target/practica_1_cloud_ordinaria-0.0.1-SNAPSHOT.jar