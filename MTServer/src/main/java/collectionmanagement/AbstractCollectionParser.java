package collectionmanagement;

import receivers.TextReceiver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractCollectionParser<T> {
    final TextReceiver receiver = new TextReceiver();

    public ConcurrentHashMap<Integer, T> parseToMap(ResultSet resultSet, List<String> columnNames) {
        List<String[]> dataList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                String[] row = new String[columnNames.size()];
                for (int i = 0; i < row.length; ++i) {
                    row[i] = resultSet.getString(columnNames.get(i));
                }
                dataList.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            receiver.printToLog("ERROR", "Parsing failed");
        }
        return convertMapData(dataList);
    }

    abstract ConcurrentHashMap<Integer, T> convertMapData(List<String[]> dataList);
}
