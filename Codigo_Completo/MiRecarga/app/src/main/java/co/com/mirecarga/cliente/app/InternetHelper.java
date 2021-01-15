package co.com.mirecarga.cliente.app;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * Utilitarios de red.
 */
public final class InternetHelper {

    /**
     * Constructor privado para evitar intanscias.
     */
    private InternetHelper() {

    }

    /**
     * Get IP address from first non-localhost interface.
     *
     * @param useIPv4 true=return ipv4, false=return ipv6
     * @return address or empty string
     */
    public static String getIPAddress(final boolean useIPv4) {
        try {
            final List<NetworkInterface> interfaces =
                    Collections.list(NetworkInterface.getNetworkInterfaces());

            for (final NetworkInterface interf : interfaces) {

                for (final InetAddress inetAddress : Collections.list(interf.getInetAddresses())) {

                    /* a loopback address would be something like 127.0.0.1 (the device
                       itself). we want to return the first non-loopback address. */
                    if (!inetAddress.isLoopbackAddress()) {
                        String ipAddr = inetAddress.getHostAddress();
                        final boolean isIPv4 = ipAddr.indexOf(':') < 0;

                        if (isIPv4 && !useIPv4) {
                            continue;
                        }
                        if (useIPv4 && !isIPv4) {
                            final int delim = ipAddr.indexOf('%'); // drop ip6 zone suffix
                            if (delim < 0) {
                                ipAddr = ipAddr.toUpperCase();
                            } else {
                                ipAddr = ipAddr.substring(0, delim).toUpperCase();
                            }
                        }
                        return ipAddr;
                    }
                }

            }
        } catch (final Exception ignored) { } // if we can't connect, just return empty string
        return "";
    }

    /**
     * Get IPv4 address from first non-localhost interface.
     *
     * @return address or empty string
     */
    public static String getIPAddress() {
        return getIPAddress(true);
    }

}
