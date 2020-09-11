package com.svmc.footballMatching.util;

import com.google.firebase.Timestamp;
import com.svmc.footballMatching.data.model.Photo;
import com.svmc.footballMatching.data.model.Team;
import com.svmc.footballMatching.data.model.user.Player;

import java.util.Map;

import javax.annotation.Nullable;

public class MapUtils {
    public static String valueToStringOrNull(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) return null;
        return (String) value;
    }

    public static boolean valueToBooleanOrFalse(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) return false;
        if (value instanceof Boolean) return (boolean) value;
        return false;
    }

    public static int valueToIntOrZero(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) return 0;
        if (value instanceof Integer) return (int) value;
        return 0;
    }

    public static int valueToIntOrNegativeOne(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) return -1;
        if (value instanceof Integer) return (int) value;
        return -1;
    }

    public static Timestamp mapToTimestamp(Map<String, Object> map) {
        int seconds = valueToIntOrZero(map, "_seconds");
        int nanoseconds = valueToIntOrZero(map, "_nanoseconds");
        return new Timestamp(seconds, nanoseconds);
    }

    public static double valueToDoubleOrZero(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) return 0;
        if (value instanceof Double) return (double) value;
        if (value instanceof Integer) {
            return (double) (int) value;
        }
        return 0;
    }

    @Nullable
    public static Double valueToDoubleOrNull(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) return null;
        if (value instanceof Double) return (double) value;
        if (value instanceof Integer) {
            return (double) (int) value;
        }
        return null;
    }

    public static Player.JoinedTeam mapToJoinedTeam(Map<String, Object> map) {
        Team team = new Team();
        String teamId = valueToStringOrNull(map, "team");
        team.setId(teamId);
        Map<String, Object> joinedTimestampMap = (Map<String, Object>) map.get("joinedTimestamp");
        Timestamp joinedTimestamp = mapToTimestamp(joinedTimestampMap);
        String status = valueToStringOrNull(map, "status");
        if (status.equals("joined")) {
            return new Player.JoinedTeam(team, joinedTimestamp, status, null, null);
        } else if (status.equals("left")) {
            Map<String, Object> leftTimestampMap = (Map<String, Object>) map.get("joinedTimestamp");
            Timestamp leftTimestamp = mapToTimestamp(leftTimestampMap);
            return new Player.JoinedTeam(team, joinedTimestamp, status, leftTimestamp, null);
        } else {
            Map<String, Object> kickedTimestampMap = (Map<String, Object>) map.get("joinedTimestamp");
            Timestamp kickedTimestamp = mapToTimestamp(kickedTimestampMap);
            return new Player.JoinedTeam(team, joinedTimestamp, status, null, kickedTimestamp);
        }
    }

    public static Player.LikedTeam mapToLikedTeam(Map<String, Object> map) {
        Team team = new Team();
        String teamId = valueToStringOrNull(map, "team");
        team.setId(teamId);
        boolean liked = valueToBooleanOrFalse(map, "liked");
        Map<String, Object> actionTimestampMap = (Map<String, Object>) map.get("actionTimestamp");
        Timestamp actionTimestamp = mapToTimestamp(actionTimestampMap);
        return new Player.LikedTeam(team, liked, actionTimestamp);
    }

    public static Photo mapToPhoto(Map<String, Object> map) {
        String id = valueToStringOrNull(map, "id");
        String photoUrl = valueToStringOrNull(map, "photoUrl");
        int showOrder = valueToIntOrZero(map, "showOrder");
        return new Photo(id, photoUrl, showOrder);
    }

    public static Team.TeamMember mapToTeamMember(Map<String, Object> map) {
        Team.TeamMember teamMember = new Team.TeamMember();
        Map<String, Object> playerMap = (Map<String, Object>) map.get("player");
        return null;
    }

    public static Player mapToPlayer(Map<String, Object> map) {
        Player player = new Player();
        return null;
    }
}
