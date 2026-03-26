package dk.casfro01.blockBreak.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeConverter {
    private static final Pattern TIME_PATTERN =
            Pattern.compile("(\\d+)\\s*(y|mo|d|h|m|s)", Pattern.CASE_INSENSITIVE);


    public static TimeBreakdown convertMillis(long millis) {
        long seconds = millis / 1000;

        long years = seconds / (60 * 60 * 24 * 365);
        seconds %= (60 * 60 * 24 * 365);

        long months = seconds / (60 * 60 * 24 * 30);
        seconds %= (60 * 60 * 24 * 30);

        long days = seconds / (60 * 60 * 24);
        seconds %= (60 * 60 * 24);

        long hours = seconds / (60 * 60);
        seconds %= (60 * 60);

        long minutes = seconds / 60;
        seconds %= 60;

        return new TimeBreakdown(
                years, months, days, hours, minutes, seconds
        );
    }

    public static long parseToSeconds(String input) {
        long totalSeconds = 0;

        Matcher matcher = TIME_PATTERN.matcher(input);

        while (matcher.find()) {
            long value = Long.parseLong(matcher.group(1));
            String unit = matcher.group(2).toLowerCase();

            switch (unit) {
                case "y":
                    totalSeconds += value * 31536000L;
                    break;
                case "mo":
                    totalSeconds += value * 2592000L;
                    break;
                case "d":
                    totalSeconds += value * 86400L;
                    break;
                case "h":
                    totalSeconds += value * 3600L;
                    break;
                case "m":
                    totalSeconds += value * 60L;
                    break;
                case "s":
                    totalSeconds += value;
                    break;
            }
        }

        return totalSeconds;
    }

    public static class TimeBreakdown {
        public final long years;
        public final long months;
        public final long days;
        public final long hours;
        public final long minutes;
        public final long seconds;

        public TimeBreakdown(long years, long months, long days,
                             long hours, long minutes, long seconds) {
            this.years = years;
            this.months = months;
            this.days = days;
            this.hours = hours;
            this.minutes = minutes;
            this.seconds = seconds;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            if (years > 0) sb.append(years).append(" år, ");
            if (months > 0) sb.append(months).append(" måneder, ");
            if (days > 0) sb.append(days).append(" dage, ");
            if (hours > 0) sb.append(hours).append(" timer, ");
            if (minutes > 0) sb.append(minutes).append(" minutter, ");
            if (seconds > 0 || sb.length() == 0)
                sb.append(seconds).append(" sekunder");

            return sb.toString().trim();
        }
    }


}
