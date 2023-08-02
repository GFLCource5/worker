package executor.service.model;

import java.util.Objects;

public class ProxyNetworkConfig {
    private String hostname;
    private Integer port;

    public ProxyNetworkConfig() {
    }

    public ProxyNetworkConfig(String hostname, Integer port) {
        this.hostname = hostname;
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProxyNetworkConfig that = (ProxyNetworkConfig) o;
        return Objects.equals(hostname, that.getHostname()) &&
                Objects.equals(port, that.getPort());
    }

    @Override
    public int hashCode() {
        return Objects.hash(hostname, port);
    }
}
