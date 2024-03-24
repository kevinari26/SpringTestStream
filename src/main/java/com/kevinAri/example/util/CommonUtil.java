package com.kevinAri.example.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import oracle.sql.TIMESTAMP;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class CommonUtil {
    // generate
    public static String generateUuid() {
        StringBuilder uuid = new StringBuilder();
        uuid.append(UUID.randomUUID());
        uuid.replace(23, 24, "");
        uuid.replace(18, 19, "");
        uuid.replace(13, 14, "");
        uuid.replace(8, 9, "");
        uuid.insert(0, System.currentTimeMillis());
        return uuid.toString();
    }
    public static BigDecimal generateRandomBigDecimal() {
        Random random = new Random();
        return new BigDecimal(random.nextInt(1000000));
    }
    public static String generateRandomAlphanumeric() {
        StringBuilder randomAlphanumeric = new StringBuilder(UUID.randomUUID().toString());
        randomAlphanumeric.replace(8, 9, "");
        return randomAlphanumeric.substring(0, 9);
    }
    public static Date generateRandomDate() {
        // 05-04-1974 sampai 23-03-2024
        long random = ThreadLocalRandom.current().nextLong(134368801770L, 1711168801770L);
        return new Date(random);
    }


    // date
    // parse
    public static Date parseStringDateToDate(String dateStr, String dateFormat) {
        try {
            if (dateStr==null) return null;
            return new SimpleDateFormat(dateFormat).parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }
    // format
    public static String oracleTimestampToStringPreserveNull(TIMESTAMP timestampInput, String dateFormat) throws Exception {
        try {
            if (timestampInput==null) return null;
            return new SimpleDateFormat(dateFormat).format(timestampInput.dateValue());
        } catch (Exception e) {
            return null;
        }
    }
    public static String formatDateToStringPreserveNull(Date dateInput, String dateFormat) {
        try {
            if (dateInput==null) return null;
            return new SimpleDateFormat(dateFormat).format(dateInput);
        } catch (Exception e) {
            return null;
        }
    }

    // number
    // casting
    public static Long bigDecimalObjectToLong(Object input) {
        try {
            return ((BigDecimal) input).longValue();
        } catch (Exception e) {
            return null;
        }
    }
    // parsing
    public static Long stringToLong(Object input) {
        try {
            if (input==null) return null;
            else return new Long(input.toString());
        } catch (Exception e) {
            return null;
        }
    }
    public static BigDecimal stringToBigDecimal (String input) {
        try {
            if (input==null || "".equals(input)) return null;
            else return new BigDecimal(input);
        } catch (Exception e) {
            return null;
        }
    }
    public static BigDecimal stringToBigDecimal (String input, BigDecimal defaultValue) {
        try {
            if (input==null || "".equals(input)) return defaultValue;
            else return new BigDecimal(input);
        } catch (Exception e) {
            return defaultValue;
        }
    }


    // jsonNode
    public static boolean isJsonNodeNull(JsonNode jsonNode) {
        if (jsonNode == null || jsonNode instanceof NullNode) return true;
        else return false;
    }
    public static String jsonNodeToStringAndTrim(JsonNode input) {
        try {
            if (input==null || input instanceof NullNode) return "";
            else return input.asText().trim();
        } catch (Exception e) {
            return "";
        }
    }
    public static Date parseJsonNodeStringToDate(JsonNode jsonNode, String parseFormat) {
        try {
            if (jsonNode==null || "".equals(jsonNode.asText().trim())) return null;
            return new SimpleDateFormat(parseFormat).parse(jsonNode.asText());
        } catch (Exception e) {
            return null;
        }
    }
    public static BigDecimal jsonNodeStringToBigDecimal(JsonNode jsonNode, BigDecimal defaultValue) {
        try {
            if (jsonNode==null || "".equals(jsonNode.asText().trim())) return defaultValue;
            return new BigDecimal(jsonNode.asText());
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
