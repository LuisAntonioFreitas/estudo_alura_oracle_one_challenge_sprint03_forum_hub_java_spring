package net.lanet.forumhub.domain.syssql;

import net.lanet.forumhub.infra.utilities.exportfiles.IHandleExportFile;

import java.util.List;
import java.util.Map;

public interface ISysSqlService extends IHandleExportFile {
    List<Map<String, Object>> executeSql(String sql, String resultSp);
    List<Map<String, Object>> executeStoredProcedure(String spName, String resultSp, Map<String, Object> params);
}