package bson

import com.alibaba.fastjson.serializer.SerializerFeature
import com.mongodb.DBObject

import scala.util.parsing.json.JSONObject

/**
  * Created by btw on 2017/3/29.
  *
  * 通过export倒出mongo数据的json其实还不是真正的json里面有一些mongo特有的格式需要转换
  *
  * 通过以下方式可以把mongo版本的json转换成通用的json
  *
  */

object MongoJsonToJson extends App {
  val monggoJson = "{ \"_id\" : { \"$oid\" : \"58b8109d26f1cc00019ecb41\" }, \"_class\" : \"com.puhui.decision.entity.mongo.ProcessLogEntity\", \"pid\" : \"M01WF01_170302194340318\", \"time\" : { \"$date\" : \"2017-03-02T12:31:25.759\" }, \"clientId\" : \"puhui-holmes-risk-assess\", \"traceId\" : \"62db3206-6089-4c5b-af40-fd1ae3699229\", \"input\" : { \"MPOBCIF003\" : null, \"ECCTRNS007\" : null, \"ECCSTAT002\" : null, \"ECCTRNS006\" : null, \"MPOSMSG001\" : null, \"CIRCRDI010\" : null, \"MPOSMSG004\" : null, \"MPOSMSG002\" : null, \"MPOVOIC011\" : null, \"MPOVOIC010\" : null, \"MPOVOIC013\" : null, \"MPOVOIC012\" : null, \"TBACTIF001\" : 4, \"CIRCRDI014\" : null, \"CIRCRDI013\" : null, \"CIRCRDI012\" : null, \"CIRCRDI011\" : null, \"ECCTRNS009\" : null, \"ECCCRVL007\" : null, \"ECCTRNS008\" : null, \"CIRCRDI015\" : null, \"ECCTRNS016\" : null, \"CIRSTAT002\" : null, \"ECCTRNS015\" : null, \"ECCTRNS018\" : null, \"ECCTRNS017\" : null, \"ECCTRNS012\" : null, \"MPOCRVL001\" : null, \"ECCTRNS011\" : null, \"ECCTRNS014\" : null, \"MPOCRVL003\" : null, \"ECCTRNS013\" : null, \"ECCTRNS010\" : null, \"MPOSMSD003\" : null, \"CIRCRDI009\" : null, \"CIRCRDI008\" : null, \"CIRCRDI003\" : null, \"CIRCRDI007\" : null, \"CIRCRDI006\" : null, \"ECCTRNS019\" : null, \"limit_actual_test\" : null, \"TBACRVL001\" : 0, \"TBACRVL002\" : 0, \"limit_display_last\" : 0, \"TBATRNS004\" : 73.062857, \"TBASTAT002\" : true, \"TBATRNS001\" : 395.25, \"CIRQURY003\" : null, \"CIRQURY004\" : null, \"CIRQURY001\" : null, \"CIRQURY002\" : null, \"USRBCIF001\" : null, \"score_credit_tba_mpo\" : null, \"USRBCIF002\" : null, \"USRBCIF004\" : null, \"rule_r_temp_test_2\" : null, \"TBATRNS007\" : 0.933333, \"MPOVOIC006\" : null, \"MPOVOIC008\" : null, \"MPOVOIC007\" : null, \"MPOVOIC009\" : null, \"MPOSTAT002\" : null }, \"models\" : { \"M01T01_170217104758395\" : { \"result\" : 543.0, \"extra_input\" : { \"score_credit_tba\" : 543.0, \"score_credit_ecc\" : 582.0, \"score_credit_mpo\" : 469.0, \"score_credit_cir\" : 368.0 } }, \"M01T01_170116144836114\" : { \"result\" : 20000.0, \"extra_input\" : { \"score_credit_tba_mpo\" : 543.0 } }, \"M01R01_170119181731682\" : { \"result\" : [  ], \"extra_input\" : { \"score_credit_tba\" : 543.0, \"score_credit_tba_mpo\" : 543.0, \"score_credit_ecc\" : 582.0, \"score_credit_mpo\" : 469.0, \"score_credit_cir\" : 368.0 } }, \"M01R01_170119181824294\" : { \"result\" : [  ], \"extra_input\" : { \"score_credit_tba\" : 543.0, \"score_credit_tba_mpo\" : 543.0, \"score_credit_ecc\" : 582.0, \"score_credit_mpo\" : 469.0, \"score_credit_cir\" : 368.0 } }, \"M01T01_170216210557633\" : { \"result\" : 469.0, \"extra_input\" : {  } }, \"M01T01_170117182930462\" : { \"result\" : 0.0, \"extra_input\" : { \"rule_r_temp_test_2\" : [  ], \"limit_decisiontree_tba_mpo\" : 20000.0 } }, \"M01T01_170118112205872\" : { \"result\" : \"3:562,6:563,9:564,12:565,18:566,24:567,36:568\", \"extra_input\" : { \"score_credit_tba_mpo\" : 543.0 } }, \"M01T01_170214215058988\" : { \"result\" : 543.0, \"extra_input\" : {  } }, \"M01T01_170216230344589\" : { \"result\" : 368.0, \"extra_input\" : {  } }, \"M01T01_170216220721171\" : { \"result\" : 582.0, \"extra_input\" : {  } }, \"M01T01_170117183046443\" : { \"result\" : 0.0, \"extra_input\" : { \"limit_actual_test\" : 0.0 } } }, \"output\" : { \"DeProcesstId\" : \"62db3206-6089-4c5b-af40-fd1ae3699229\", \"code\" : \"00000\", \"msg\" : \"操作成功\", \"result\" : { \"score_credit_tba\" : 543.0, \"product\" : \"3:562,6:563,9:564,12:565,18:566,24:567,36:568\", \"rule_l_test\" : [  ], \"limit_display_now\" : 0.0, \"score_credit_tba_mpo\" : 543.0, \"score_credit_mpo\" : 469.0, \"score_credit_cir\" : 368.0, \"limit_decisiontree_tba_mpo\" : 20000.0, \"limit_actual_test\" : 0.0, \"rule_r_temp_test_2\" : [  ] } } }"
  val dbo = com.mongodb.BasicDBObject.parse(monggoJson)
  val json = new com.alibaba.fastjson.JSONObject(dbo)
  json.put("_id",dbo.getString("_id"))
  println(json)
}
