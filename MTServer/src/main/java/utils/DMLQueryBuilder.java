package utils;

import exceptions.DMLBuildException;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class DMLQueryBuilder {
    private String tableName;
    private final List<String> queryWords = new ArrayList<>();
    private final List<String> columns = new ArrayList<>();
    private final List<List<Object>> rows = new ArrayList<>();
    private final List<String> predicates = new ArrayList<>();

    public DMLQueryBuilder query(String query) {
        this.queryWords.addAll(List.of(query.split(" ")));
        return this;
    }

    public DMLQueryBuilder table(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public DMLQueryBuilder columns(List<String> columns) {
        this.columns.addAll(columns);
        return this;
    }

    public DMLQueryBuilder column(String column) {
        columns.add(column);
        return this;
    }

    public DMLQueryBuilder row(Object... objSequence) {
        rows.add(List.of(objSequence));
        return this;
    }

    public DMLQueryBuilder predicate(String column, String sign, Object value) {
        predicates.add(column + sign + value);
        return this;
    }

    public String build() throws DMLBuildException {
        StringBuilder sql = new StringBuilder(queryWords.get(0) + " ");
        switch (queryWords.get(0)) {
            case "INSERT" -> {
                return buildInsert(sql);
            }
            case "UPDATE" -> {
                return buildUpdate(sql);
            }
            case "DELETE" -> {
                return buildDelete(sql);
            }
            default -> throw new DMLBuildException();
        }

    }

    private String buildInsert(StringBuilder sql) {
        sql.append(queryWords.get(1)).append(" ").append(tableName).append(" (");
        StringJoiner sj = new StringJoiner(", ");
        for (String column : columns) {
            sj.add(column);
        }
        sql.append(sj).append(") ").append(queryWords.get(2)).append(" ");
        for (List<Object> row : rows) {
            sj = new StringJoiner(", ", "(", ")");
            for (Object value : row) {
                sj.add(value.toString());
            }
            sql.append(sj).append(",");
        }

        int lastCommaIndex = sql.length() - 1;
        sql.replace(lastCommaIndex, lastCommaIndex + 1, ";");

        return String.valueOf(sql);
    }

    private String buildUpdate(StringBuilder sql) {
        sql.append(tableName).append(" ").append(queryWords.get(1)).append(" ");
        StringJoiner sj = new StringJoiner(", ");
        if (!rows.isEmpty()) {
            List<Object> values = rows.get(0);
            for (int i = 0; i < columns.size(); ++i) {
                sj.add(columns.get(i) + "=" + values.get(i));
            }
        }
        sql.append(sj).append(" ").append(queryWords.get(2)).append(" ");

        sj = new StringJoiner(" " + queryWords.get(3) + " ");
        for (String predicate : predicates) {
            sj.add(predicate);
        }
        sql.append(sj).append(";");

        return String.valueOf(sql);
    }

    private String buildDelete(StringBuilder sql) {
        sql.append(queryWords.get(1)).append(" ").append(tableName).append(" ");

        if (!predicates.isEmpty()) {
            StringJoiner sj = new StringJoiner(" " + queryWords.get(3) + " ");
            for (String predicate : predicates) {
                sj.add(predicate);
            }
            sql.append(queryWords.get(2)).append(" ").append(sj).append(" ");
        }

        sql.append(";");

        return String.valueOf(sql);
    }
}
