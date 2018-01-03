package http

import java.nio.charset.Charset
import java.util

import com.google.gson.{JsonArray, JsonObject}
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.{HttpGet, HttpPost}
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.message.BasicNameValuePair


/**
  * Created by btw on 2017/12/7.
  */
object HttpPostTester {

  def postJson(): Unit = {

    val httpPost = new HttpPost("http://mvnrepository.com/artifact/org.apache.httpcomponents/httpcore/4.0-alpha6")
    httpPost.addHeader("Content-type", "application/json; charset=utf-8");
    httpPost.setHeader("Accept", "application/json");

    val arr = new JsonArray()
    val j = new JsonObject()
    j.addProperty("orderId", "中文");
    j.addProperty("createTimeOrder", "2015-08-11");
    arr.add(j);
    httpPost.setEntity(new StringEntity(arr.toString, Charset.forName("UTF-8")))

    val httpClient = new DefaultHttpClient()
    val response = httpClient.execute(httpPost);
    val entity = response.getEntity()
    var content = ""
    if (entity != null) {
      val inputStream = entity.getContent()
      content = scala.io.Source.fromInputStream(inputStream).getLines.mkString
      inputStream.close
    }
    httpClient.getConnectionManager().shutdown()
    println(content)

  }

  def post(): Unit = {

    val httpPost = new HttpPost("http://mvnrepository.com/artifact/org.apache.httpcomponents/httpcore/4.0-alpha6")
    val list = new util.ArrayList[BasicNameValuePair]()
    list.add(new BasicNameValuePair("A1", "1"))
    list.add(new BasicNameValuePair("A2", "2"))
    list.add(new BasicNameValuePair("A3", "3"))
    val entitySend = new UrlEncodedFormEntity(list, "utf-8")
    httpPost.setEntity(entitySend)

    val httpClient = new DefaultHttpClient()
    val response = httpClient.execute(httpPost)
    val entity = response.getEntity()
    var content = ""
    if (entity != null) {
      val inputStream = entity.getContent()
      content = scala.io.Source.fromInputStream(inputStream).getLines.mkString
      inputStream.close
    }
    httpClient.getConnectionManager().shutdown()
    println(content)

  }


  def main(args: Array[String]): Unit = {

    post()
    postJson()
  }


}
