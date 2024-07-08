package net.lanet.forumhub.infra.utilities;


public class HandleFindIdOrUuidUtil {
    public static Object[] getIdOrUuid(String id) {
        Long longId = null;
        String uuid = id;
//        try {
//            longId = Long.valueOf(id);
//            uuid = null;
//        } catch (Exception ignored) {}

        return new Object[] { longId, uuid };
    }
}
