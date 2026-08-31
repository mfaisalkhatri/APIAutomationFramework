package io.github.mfaisalkhatri.logging;

import java.util.Map;
import java.util.stream.Collectors;

public final class SensitiveDataMasker {
    private static final String MASK = "********";

    private SensitiveDataMasker () {

    }

    public static Map<String, String> maskHeaders (final Map<String, String> headers) {
        if (headers == null) {
            return null;
        }
        return headers.entrySet ()
            .stream ()
            .collect (Collectors.toMap (Map.Entry::getKey,
                entry -> isSensitiveHeader (entry.getKey ()) ? MASK : entry.getValue ()));
    }

    private static boolean isSensitiveHeader (final String headerName) {
        return headerName.equalsIgnoreCase ("Authorization") || headerName.equalsIgnoreCase (
            "Cookie") || headerName.equalsIgnoreCase ("Set-Cookie") || headerName.equalsIgnoreCase ("X-API-Key");
    }

}
