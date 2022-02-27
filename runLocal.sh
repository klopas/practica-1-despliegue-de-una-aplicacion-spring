export AWS_ACCESS_KEY_ID=
export AWS_SECRET_ACCESS_KEY=
export BUCKET_NAME=michel.bucket.eu-west-1
export REGION=eu-west-1

mvn package -DskipTests
java -jar -Dspring.profiles.active=local target/practica_1_cloud_ordinaria-0.0.1-SNAPSHOT.jar