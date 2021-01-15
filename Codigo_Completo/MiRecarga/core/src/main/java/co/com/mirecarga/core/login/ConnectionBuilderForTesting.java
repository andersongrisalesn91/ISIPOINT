/*
 * Copyright 2016 The AppAuth for Android Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package co.com.mirecarga.core.login;

import android.annotation.SuppressLint;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;

import net.openid.appauth.Preconditions;
import net.openid.appauth.connectivity.ConnectionBuilder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * An example implementation of {@link ConnectionBuilder} that permits connecting to http
 * links, and ignores certificates for https connections. *THIS SHOULD NOT BE USED IN PRODUCTION
 * CODE*. It is intended to facilitate easier testing of AppAuth against development servers
 * only.
 */
public final class ConnectionBuilderForTesting implements ConnectionBuilder {
    /**
     * Instancia de la clase.
     */
    public static final ConnectionBuilderForTesting INSTANCE = new ConnectionBuilderForTesting();

    /**
     * Mensaje de log.
     */
    private static final String TAG = "ConnBuilder";

    /**
     * Timeout por defecto.
     */
    private static final int CONNECTION_TIMEOUT_MS = (int) TimeUnit.SECONDS.toMillis(15);
    /**
     * Timeout por defecto.
     */
    private static final int READ_TIMEOUT_MS = (int) TimeUnit.SECONDS.toMillis(10);

    /**
     * Confía en todos los certificados.
     */
    @SuppressLint("TrustAllX509TrustManager")
    private static final TrustManager[] ANY_CERT_MANAGER = new TrustManager[] {
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                public void checkClientTrusted(final X509Certificate[] certs, final String authType) {
                    // Intencionalmente vacío
                }

                public void checkServerTrusted(final X509Certificate[] certs, final String authType) {
                    // Intencionalmente vacío
                }
            }
    };

    /**
     * Confía en todos los nombres de servidor.
     */
    @SuppressLint("BadHostnameVerifier")
    private static final HostnameVerifier ANY_HOSTNAME_VERIFIER = (hostname, session) -> true;

    /**
     * El contexto de ssl.
     */
    @Nullable
    private static final SSLContext TRUSTING_CONTEXT;

    static {
        SSLContext context;
        try {
            context = SSLContext.getInstance("SSL");
        } catch (final NoSuchAlgorithmException e) {
            Log.e(TAG, "Unable to acquire SSL context");
            context = null;
        }

        SSLContext initializedContext = null;
        if (context != null) {
            try {
                context.init(null, ANY_CERT_MANAGER, new java.security.SecureRandom());
                initializedContext = context;
            } catch (final KeyManagementException e) {
                Log.e(TAG, "Failed to initialize trusting SSL context");
            }
        }

        TRUSTING_CONTEXT = initializedContext;
    }

    /**
     * Constructor privado para evitar instancias.
     */
    private ConnectionBuilderForTesting() {
        // no need to construct new instances
    }

    @NonNull
    @Override
    public HttpURLConnection openConnection(@NonNull final Uri uri) throws IOException {
        Preconditions.checkNotNull(uri, "url must not be null");
        Preconditions.checkArgument("http".equals(uri.getScheme()) || "https".equals(uri.getScheme()),
                "scheme or uri must be http or https");
        final HttpURLConnection conn = (HttpURLConnection) new URL(uri.toString()).openConnection();
        conn.setConnectTimeout(CONNECTION_TIMEOUT_MS);
        conn.setReadTimeout(READ_TIMEOUT_MS);
        conn.setInstanceFollowRedirects(false);

        if (conn instanceof HttpsURLConnection && TRUSTING_CONTEXT != null) {
            final HttpsURLConnection httpsConn = (HttpsURLConnection) conn;
            httpsConn.setSSLSocketFactory(TRUSTING_CONTEXT.getSocketFactory());
            httpsConn.setHostnameVerifier(ANY_HOSTNAME_VERIFIER);
        }

        return conn;
    }
}
