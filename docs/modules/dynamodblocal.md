# DynamoDB Local

Testcontainers module for [DynamoDB Local](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.html), the downloadable version of Amazon DynamoDB.

## Usage example

```java
try (DynamoDBLocalContainer container = new DynamoDBLocalContainer("amazon/dynamodb-local:2.5.4")) {
    container.start();

    DynamoDbClient client = DynamoDbClient.builder()
        .endpointOverride(container.getEndpointOverride())
        .credentialsProvider(
            StaticCredentialsProvider.create(
                AwsBasicCredentials.create("dummy", "dummy")
            )
        )
        .region(Region.US_EAST_1)
        .build();
}
```

## Adding this module to your project dependencies

```groovy
testImplementation "org.testcontainers:dynamodb:{{latest_version}}"
```

```xml
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>dynamodb</artifactId>
    <version>{{latest_version}}</version>
    <scope>test</scope>
</dependency>
```
