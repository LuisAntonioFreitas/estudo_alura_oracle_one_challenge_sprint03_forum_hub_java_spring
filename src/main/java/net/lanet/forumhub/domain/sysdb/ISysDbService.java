package net.lanet.forumhub.domain.sysdb;

import java.time.LocalDateTime;

public interface ISysDbService {
    LocalDateTime findDateFirstTableCreated(String databaseName);
    LocalDateTime findDateTable(String tableName);
}
