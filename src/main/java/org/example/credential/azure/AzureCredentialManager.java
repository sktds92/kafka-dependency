package org.example.credential.azure;

import com.azure.core.credential.TokenCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import lombok.Getter;

@Getter
public class AzureCredentialManager {
    private static AzureCredentialManager azureCredentialManager;
    private TokenCredential tokenCredential;

    public static synchronized AzureCredentialManager getInstance(){
        if(azureCredentialManager == null) azureCredentialManager = new AzureCredentialManager();
        return azureCredentialManager;
    }

    private AzureCredentialManager(){
        String clientId = System.getenv("AZURE_CLIENT_ID");
        String tenantId = System.getenv("AZURE_TENANT_ID");
        String clientSecret = System.getenv("AZURE_CLIENT_SECRET");

        this.tokenCredential = new ClientSecretCredentialBuilder()
                .tenantId(tenantId)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();
    }
}
