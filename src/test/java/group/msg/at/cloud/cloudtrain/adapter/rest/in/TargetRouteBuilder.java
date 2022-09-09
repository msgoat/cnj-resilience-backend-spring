package group.msg.at.cloud.cloudtrain.adapter.rest.in;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public final class TargetRouteBuilder {

    private URI parsedTargetRoute;
    private final Map<String, Integer> portsByServiceName = new HashMap<>();

    public TargetRouteBuilder withTargetRoute(@NotNull String targetRouteUrl) {
        if (targetRouteUrl.isEmpty()) {
            throw new IllegalArgumentException("Given targetRouteUrl must not be empty!");
        }
        parsedTargetRoute = URI.create(targetRouteUrl);
        return this;
    }

    public TargetRouteBuilder withLocalPortBinding(@NotNull String serviceName, int port) {
        this.portsByServiceName.put(serviceName, port);
        return this;
    }

    @NotNull
    public String buildFor(@NotNull String serviceName) {
        String result = null;
        if (isLocalTarget()) {
            result = buildLocalUri(serviceName);
        } else {
            result = buildRemoteUri(serviceName);
        }
        return result;
    }

    private boolean isLocalTarget() {
        return "localhost".equals(parsedTargetRoute.getHost());
    }

    private boolean isRemoteTarget() {
        return !"localhost".equals(parsedTargetRoute.getHost());
    }

    private String buildLocalUri(String serviceName) {
        Integer port = portsByServiceName.get(serviceName);
        if (port == null) {
            throw new IllegalStateException(String.format("Expected port binding for service [{}] but found none!", serviceName));
        }
        URI result = null;
        try {
            result = new URI(parsedTargetRoute.getScheme(),
                    parsedTargetRoute.getUserInfo(),
                    parsedTargetRoute.getHost(),
                    port,
                    null,
                    null,
                    null);
        } catch (URISyntaxException e) {
            throw new IllegalStateException(String.format("Unable to build local URI for service [%s] from target route [%s] and port [%d]", serviceName, parsedTargetRoute, port));
        }
        return result.toString();
    }

    private String buildRemoteUri(String serviceName) {
        URI result = null;
        String path = parsedTargetRoute.getPath();
        if (path != null && !path.isEmpty()) {
            String[] pathComponents = path.split("/");
            pathComponents[pathComponents.length - 1] = serviceName;
            path = String.join("/", pathComponents);
            try {
                result = new URI(parsedTargetRoute.getScheme(),
                        parsedTargetRoute.getUserInfo(),
                        parsedTargetRoute.getHost(),
                        parsedTargetRoute.getPort(),
                        path,
                        null,
                        null);
            } catch (URISyntaxException e) {
                throw new IllegalStateException(String.format("Unable to build remote URI for service [%s] from target route [%s] and path [%s]", serviceName, parsedTargetRoute, path));
            }
        } else {
            throw new IllegalStateException(String.format("Expected path of target route [%s] to be non-empty when building remote URI for service [%s]!", parsedTargetRoute, serviceName));
        }
        return result.toString();
    }
}
