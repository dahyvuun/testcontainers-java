package org.testcontainers.dynamodb;

import org.junit.jupiter.api.Test;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.ListTablesResponse;

import static org.assertj.core.api.Assertions.assertThat;

class DynamoDBLocalContainerTest {

    private static final DockerImageName DYNAMODB_IMAGE =
        DockerImageName.parse("amazon/dynamodb-local:2.5.4");

    @Test
    void testListTables() {
        try (DynamoDBLocalContainer container = new DynamoDBLocalContainer(DYNAMODB_IMAGE)) {
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

            ListTablesResponse response = client.listTables();
            assertThat(response.tableNames()).isEmpty();
        }
    }
}
