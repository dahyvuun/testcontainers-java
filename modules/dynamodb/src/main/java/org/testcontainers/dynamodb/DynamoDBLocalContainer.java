package org.testcontainers.dynamodb;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import java.net.URI;

/**
 * Testcontainers implementation for DynamoDB Local.
 *
 * <p>Supported image: {@code amazon/dynamodb-local}
 *
 * <p>Exposed ports:
 * <ul>
 *     <li>HTTP: 8000</li>
 * </ul>
 */
public class DynamoDBLocalContainer extends GenericContainer<DynamoDBLocalContainer> {

    private static final DockerImageName DEFAULT_IMAGE_NAME =
        DockerImageName.parse("amazon/dynamodb-local");

    private static final int DYNAMODB_PORT = 8000;

    public DynamoDBLocalContainer(String dockerImageName) {
        this(DockerImageName.parse(dockerImageName));
    }

    public DynamoDBLocalContainer(DockerImageName dockerImageName) {
        super(dockerImageName);
        dockerImageName.assertCompatibleWith(DEFAULT_IMAGE_NAME);
        withExposedPorts(DYNAMODB_PORT);
        waitingFor(Wait.forHttp("/").forStatusCode(400));
    }

    /**
     * Returns the endpoint URI to use with the AWS SDK client.
     */
    public URI getEndpointOverride() {
        return URI.create("http://" + getHost() + ":" + getMappedPort(DYNAMODB_PORT));
    }
}
