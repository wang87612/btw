import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


/**
 * Created by btw on 2017/2/17.
 */
public class Json2Excel {

    public Json2Excel() {
    }



    public static void build(OutputStream output, List<String> listJson, String[] columnsNames) throws Exception {


        HSSFWorkbook libro = new HSSFWorkbook();
        HSSFSheet hoja = libro.createSheet();
        HSSFRow header = hoja.createRow(0);


        for (int e = 0; e < columnsNames.length; ++e) {
            String object = columnsNames[e];
            HSSFCell row = header.createCell(e);
            HSSFRichTextString j = new HSSFRichTextString(object);
            row.setCellValue(j);
        }


        for (int i = 0; i < listJson.size(); i++) {
            JSONObject jsonObject = JSONObject.parseObject(listJson.get(i));
            HSSFRow hojaRow = hoja.createRow(1 + i);

            for (int j = 0; j < columnsNames.length; j++) {
                String string = columnsNames[j];
                HSSFCell cell = hojaRow.createCell(j);
                try {
                    HSSFRichTextString text = new HSSFRichTextString(jsonObject.get(string).toString());
                    cell.setCellValue(text);
                } catch (Exception e) {
                    e.printStackTrace();
//                    HSSFRichTextString text = new HSSFRichTextString(var16.getDouble(string) + "");
//                    cell.setCellValue(text);
                }

            }
        }

        try {
            libro.write(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            List<String> l = new ArrayList<>();
            l.add("{\"name\":\"sortTime\",\"type\":\"long\",\"value\":1482137872000}");
            FileOutputStream xlsStream = new FileOutputStream("/Users/btw/Desktop/document.xls");
            Json2Excel.build(xlsStream, l, new String[]{"name", "type", "value"});
            xlsStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
