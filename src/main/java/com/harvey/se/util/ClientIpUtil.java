package com.harvey.se.util;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IP解析工具
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-08 14:20
 */
public class ClientIpUtil {
    private static final String[] HEADERS_TO_TRY = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };

    private ClientIpUtil() {
    }

    public static String get(HttpServletRequest request) {
        for (String header : HEADERS_TO_TRY) {
            String ip = request.getHeader(header);
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                return getFirst(ip);
            }
        }

        String remoteAddr = request.getRemoteAddr();
        return handleLocal(remoteAddr);
    }

    private static String getFirst(String ipString) {
        if (ipString != null && ipString.contains(",")) {
            String[] ips = ipString.split(",");
            for (String ip : ips) {
                String trimmed = ip.trim();
                if (!"unknown".equalsIgnoreCase(trimmed) &&
                    !isInternal(trimmed)) {
                    return trimmed;
                }
            }
            // 如果都是内网IP，返回第一个
            return ips[0].trim();
        }
        return ipString;
    }

    private static String handleLocal(String ip) {
        if ("0:0:0:0:0:0:0:1".equals(ip) || "127.0.0.1".equals(ip)) {
            try {
                return InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                return "127.0.0.1";
            }
        }
        return ip;
    }

    private static boolean isInternal(String ip) {
        // 简单判断是否为内网IP
        return ip.startsWith("10.") ||
               ip.startsWith("192.168.") ||
               (ip.startsWith("172.") && is172Internal(ip)) ||
               "127.0.0.1".equals(ip);
    }

    private static boolean is172Internal(String ip) {
        try {
            String[] parts = ip.split("\\.");
            if (parts.length >= 2) {
                int second = Integer.parseInt(parts[1]);
                return second >= 16 && second <= 31;
            }
        } catch (Exception e) {
            // 忽略解析异常
        }
        return false;
    }
}
