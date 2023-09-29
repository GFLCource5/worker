package executor.service.service.impl;

import executor.service.config.properties.PropertiesConfig;
import executor.service.config.properties.PropertiesConstants;
import executor.service.model.ProxyConfigHolder;
import executor.service.service.ProxyValidator;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.Properties;

public class ProxyValidatorImpl implements ProxyValidator {
    private final Properties properties;

    public ProxyValidatorImpl(PropertiesConfig propertiesConfig) {
        this.properties = propertiesConfig.getProperties(PropertiesConstants.PROXY_VALIDATOR_PROPERTIES);
    }

    public Boolean isValid(ProxyConfigHolder proxyConfigHolder) {
        int responseCode = 0;

        try {
            CredentialsProvider credentialsProvider = getCredentialsProvider(proxyConfigHolder);
            CloseableHttpClient httpClient = getHttpClient(proxyConfigHolder, credentialsProvider);


            HttpGet httpGet = new HttpGet(properties.getProperty(PropertiesConstants.PROXY_VALIDATOR_TARGET_URL));
            CloseableHttpResponse response = httpClient.execute(httpGet);
            responseCode = response.getStatusLine().getStatusCode();

            response.close();
            httpClient.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return responseCode == HttpStatus.SC_OK;
    }

    private CredentialsProvider getCredentialsProvider(ProxyConfigHolder proxyConfig) {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                new AuthScope(proxyConfig.getProxyNetworkConfig().getHostname(),
                        proxyConfig.getProxyNetworkConfig().getPort()),
                new UsernamePasswordCredentials(proxyConfig.getProxyCredentials().getUsername(),
                        proxyConfig.getProxyCredentials().getPassword()));
        return credentialsProvider;
    }

    private CloseableHttpClient getHttpClient(ProxyConfigHolder proxyConfig, CredentialsProvider credentials) {
        int timeout = Integer.parseInt(properties.getProperty(PropertiesConstants.PROXY_VALIDATOR_CONNECTION_TIMEOUT));
        return HttpClients.custom()
                .setDefaultCredentialsProvider(credentials)
                .setProxy(new HttpHost(proxyConfig.getProxyNetworkConfig().getHostname(),
                        proxyConfig.getProxyNetworkConfig().getPort()))
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(timeout)
                        .setSocketTimeout(timeout)
                        .build())
                .build();
    }
}
