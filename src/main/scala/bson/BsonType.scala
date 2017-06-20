package bson

import com.mongodb.{BasicDBList, BasicDBObject}

/**
  * Created by btw on 2017/5/15.
  */
object BsonType extends App {


  //嵌套 list
  val mongoJson = "{ \"_id\" : \"102201705127409063#bd4d15d2-6ab6-46c7-9542-9d4074cb60cc\" , \"className\" : \"com.puhui.dc.store.original.openline.OpenLineStore\" , \"customerAnnexStatuss\" : [ { \"creditAppCustomerId\" : 7409063 , \"type\" : 4 , \"status\" : 3 , \"invalidTime\" : { \"$date\" : \"2017-05-12T02:26:41.859Z\"} , \"cuk\" : \"1494556001859\" , \"vc\" : true , \"idNum\" : \"342201198911123276\" , \"updateTime\" : { \"$date\" : \"2017-05-12T02:26:41.859Z\"}}] , \"ct\" : { \"$date\" : \"2017-05-12T02:26:42.115Z\"} , \"idCardNum\" : \"342201198911123276\" , \"channel\" : \"102\" , \"applyNo\" : \"102201705127409063\" , \"batchNo\" : { \"$date\" : \"2017-05-12T02:26:41.862Z\"} , \"opResult\" : 1}"

  //嵌套 Object
  //val mongoJson = "{ \"_id\" : \"30001801$ECC$20170418$1492522416880\", \"_class\" : \"com.puhui.holmes.index.entity.CreditCard\", \"userId\" : \"30001801\", \"requestId\" : \"106201704180012371\", \"idNo\" : \"41122119751115651X\", \"schemaVersion\" : \"1\", \"ct\" : { \"$date\" : \"2017-04-18T13:33:36.880\" }, \"indexes\" : { \"ECCTRNS005\" : 2, \"ECCTRNS004\" : 2, \"ECCSTAT001\" : true, \"ECCTRNS007\" : 9.0, \"ECCSTAT002\" : true, \"ECCTRNS006\" : -5148.26, \"ECCTRNS001\" : 0.685028, \"ECCTRNS022\" : 0, \"ECCTRNS003\" : 12, \"ECCTRNS002\" : 0.0, \"ECCTRNS021\" : 6148.0, \"ECCTRNS020\" : 30000.0, \"ECCCRVL001\" : 0, \"ECCCRVL003\" : 0, \"ECCCRVL002\" : 0, \"ECCCRVL005\" : 0, \"ECCCRVL004\" : 11, \"ECCCRVL007\" : 0, \"ECCTRNS009\" : 1712.9, \"ECCCRVL006\" : 0, \"ECCTRNS008\" : 1.0, \"ECCCRVL009\" : 0, \"ECCCRVL008\" : 0, \"ECCTRNS016\" : 17000.0, \"ECCTRNS015\" : 2151.138746, \"ECCTRNS018\" : null, \"ECCTRNS017\" : 5000.0, \"ECCTRNS012\" : 6, \"ECCTRNS011\" : 0, \"ECCTRNS014\" : 8137.15, \"ECCTRNS013\" : 12, \"ECCBCIF003\" : \"15\", \"ECCBCIF004\" : \"交通银行,中信银行\", \"ECCBCIF005\" : \"3080436262@qq.com\", \"ECCTRNS010\" : 0.0, \"ECCBCIF006\" : 7000, \"ECCCRVL010\" : 0, \"ECCCRVL012\" : 23, \"ECCBCIF001\" : \"上官朝民\", \"ECCCRVL011\" : 0, \"ECCBCIF002\" : \"5138,1196\", \"ECCCRVL014\" : 0, \"ECCCRVL013\" : 0, \"ECCCRVL016\" : 23, \"ECCCRVL015\" : 0, \"ECCTRNS019\" : 7197.92 }, \"channelCode\" : \"qianzhan\" }"


  val data = com.mongodb.BasicDBObject.parse(mongoJson)

  val outer = data.keySet().toArray.map(k => {
    if (data.get(k).getClass.getSimpleName != "BasicDBList" && data.get(k).getClass.getSimpleName != "BasicDBObject") {
      k
    } else null
  }).filter(_ != null)

  outer.foreach(k => println(k + " == " + data.get(k).getClass.getSimpleName))

  data.keySet().toArray.foreach(k => {
    val typeName = data.get(k).getClass.getSimpleName

    if (typeName == "BasicDBList") {
      data.get(k).asInstanceOf[BasicDBList].toArray.foreach(l => {
        val sub = l.asInstanceOf[BasicDBObject]
        sub.keySet().toArray.foreach(k => {
          println(k + " == " + sub.get(k).getClass.getSimpleName)
        })
      })
    }
    else if (typeName == "BasicDBObject") {
      println("key == String")
      println("value == String")
    }
  })

}
