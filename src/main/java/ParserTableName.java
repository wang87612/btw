
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by btw on 2018/8/7.
 */
public class ParserTableName {

    public static List<String> getTableNameBySql(String sql) throws JSQLParserException {
        CCJSqlParserManager parser = new CCJSqlParserManager();
        StringReader reader = new StringReader(sql);
        List<String> list = new ArrayList<String>();
        Statement stmt = parser.parse(new StringReader(sql));
        if (stmt instanceof Select) {
            Select selectStatement = (Select) stmt;
            TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
            List tableList = tablesNamesFinder.getTableList(selectStatement);
            for (Iterator iter = tableList.iterator(); iter.hasNext(); ) {
                String tableName = iter.next().toString();
                list.add(tableName);
            }
        }
        return list;
    }
}
