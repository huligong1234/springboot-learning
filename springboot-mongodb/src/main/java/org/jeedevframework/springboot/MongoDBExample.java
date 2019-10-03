package org.jeedevframework.springboot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

/*
 * 使用Mongo java jdk (不使用Spring mongoTemplate) 示例
 * **/
public class MongoDBExample {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 27017;
    private static final String DB_NAME = "jeedev";
    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;
    
    static {
    	mongoClient = new MongoClient(HOST, PORT);
    	mongoDatabase = mongoClient.getDatabase(DB_NAME);
    }
    
    public static void main(String[] args) {
    	MongoCollection<Document> dbCollection = mongoDatabase.getCollection("t_user");
        
        // 查询该数据库所有的集合名
        /*for(String name : mongoClient.listDatabaseNames()){
            System.out.println(name);
        }*/
        
    	
        //addOne(dbCollection);
        //addList(dbCollection);
        //addByJson(dbCollection);
        
        //deleteOne(dbCollection);
        //deleteByIn(dbCollection);
        //deleteAll(dbCollection);
        
        //updateOne(dbCollection);
        //updateMulti(dbCollection);
        
        //queryOne(dbCollection);
        //queryPage(dbCollection);
        //queryRange(dbCollection);
        queryList(dbCollection);
        
    }
    
    
    // ====================================查询开始==============================================
    /**
     * @Title: queryOne
     * @Description: 查询 name为 张三的 一条记录
     * @param dbCollection
     * @return: void
     */
    public static void queryOne(MongoCollection<Document> dbCollection){
    	Document documents = new Document("name","张三");
    	Document result = dbCollection.find(documents).first();
        System.out.println(result);
    }
    
    /**
     * @Title: queryPage
     * @Description: 分页查询  ， 查询 跳过前2条 后的 3条 数据
     * @param dbCollection
     * @return: void
     */
    public static void queryPage(MongoCollection<Document> dbCollection){
    	FindIterable<Document> iter = dbCollection.find().skip(2).limit(3);
        iter.forEach(new Block<Document>() {
        	public void apply(Document _doc) {
        		System.out.println(_doc.toJson());
        	}
    	});
    }
    
    /**
     * @Title: queryRange
     * @Description:
     * @param dbCollection
     * @return: void
     */
    public static void queryRange(MongoCollection<Document> dbCollection) {
        DBObject range = new BasicDBObject();
        range.put("$gte", 50);
        range.put("$lte", 52);
        
        Document queryObj = new Document();
        queryObj.put("age", range);
        FindIterable<Document> iter = dbCollection.find(queryObj);
        iter.forEach(new Block<Document>() {
        	public void apply(Document _doc) {
        		System.out.println(_doc.toJson());
        	}
    	});
    }
    
    /**
     * @Title: queryList
     * @Description: TODO 查询出全部的 记录
     * @param dbCollection
     * @return: void
     */
    public static void queryList(MongoCollection<Document> dbCollection) {
        FindIterable<Document> iter = dbCollection.find();
        iter.forEach(new Block<Document>() {
        	public void apply(Document _doc) {
        		System.out.println(_doc.toJson());
        	}
    	});
    }
    
    // ====================================增加开始==============================================
    /**
     * @Title: addOne
     * @Description: TODO 新增 一条记录
     * @param dbCollection
     * @return: void
     */
    public static void addOne(MongoCollection<Document> dbCollection){
    	Document documents = new Document("name","张三").append("age", 45).append("sex", "男").append("address", 
                new Document("postCode", 100000).append("street", "梧桐大道01号").append("city", "南京"));
        dbCollection.insertOne(documents);
    }
    
    /**
     * @Title: addList 
     * @Description: TODO 批量新增 记录    , 增加的记录 中 可以使用各种数据类型
     * @param dbCollection
     * @return: void
     */
    public static void addList(MongoCollection<Document> dbCollection){
        List<Document> documents = new ArrayList<Document>();
        Document docObj = new Document();
        docObj.put("name", "李四");
        // 可以直接保存List类型
        List<String> list = new ArrayList<String>();
        list.add("一个书生");
        docObj.put("remark", list);

        // 可以直接保存map
        Map<String,List<String>> map = new HashMap<String,List<String>>();
        List<String> hobbys = new ArrayList<String>();
        hobbys.add("下棋");
        hobbys.add("看书");
        map.put("爱好", hobbys);
        docObj.put("hobby", map);
        documents.add(docObj);
        
        docObj = new Document();
        docObj.put("name", "王五");
        docObj.put("age", 45);
        docObj.put("job", "教师");
        docObj.put("remark", new Document("address", "江苏省无锡市").append("street", "水浒三国路01号"));
        documents.add(docObj);
        
        dbCollection.insertMany(documents);
    }
    
    /**
     * @Title: addByJson
     * @Description: TODO json转对象后 ，执行新增
     * @param dbCollection
     * @return: void
     */
    public static void addByJson(MongoCollection<Document> dbCollection){
        String json = "{ \"name\" : \"徐生\" , \"age\" : 18 , \"job\" : \"无\" , \"remark\" : { \"address\" : \"江苏南京市\" , \"street\" : \"南京中山路01号\"}}";
        Document docObj = (Document) Document.parse(json);
        dbCollection.insertOne(docObj);
    }
    
    // ====================================修改开始==============================================
    /**
     * @Title: update
     * @Description: TODO 修改指定记录
     * @param dbCollection
     * @return: void
     */
    public static void updateOne(MongoCollection<Document> dbCollection) {
    	 Document oldObj = new Document();
    	 oldObj.put("_id", new ObjectId("5d9567f58df0247e1c4eb7c8"));
    	 
    	 Document newObj = new Document();
    	 newObj.put("$set", new BasicDBObject("age",52));
    	Document updatedObj = dbCollection.findOneAndUpdate(oldObj,newObj);
    }
    
    /**
     * @Title: updateMulti
     * @Description: TODO 修改 多条记录
     * @param dbCollection
     * @return: void
     */
    public static void updateMulti(MongoCollection<Document> dbCollection) {
    	Document newObj = new Document();
    	newObj.put("name", "张三");
        newObj.put("address", "江苏南京");
        newObj.put("remark", "张三remark");
  
        Document oldObj = new Document();
        oldObj.put("name", "张三");
        dbCollection.updateMany(oldObj, new Document().append("$set", newObj), new UpdateOptions().upsert(true));
    }
    
    // ====================================删除开始==============================================
    /**
     * @Title: deleteFirst
     * @Description: TODO 删除第一个
     * @param 
     * @return: void
     */
    public static void deleteFirst(MongoCollection<Document> dbCollection){
    	Document deletedObj = dbCollection.findOneAndDelete(new Document());
    }
    
    /**
     * @Title: deleteOne
     * @Description: TODO 删除指定的一条记录
     * @param dbCollection
     * @return: void
     */
    public static void deleteOne(MongoCollection<Document> dbCollection){
    	Document doc = new Document();
    	doc.put("_id", new ObjectId("5d9563818df0246820749e73"));
        dbCollection.deleteOne(doc);
    }
    
    /**
     * @Title: deleteByIn
     * @Description: TODO 删除多条记录      例如：select * from tb where name in('12','34')
     * @param dbCollection
     * @return: void
     */
    public static void deleteByIn(MongoCollection<Document> dbCollection) {
        List<String> list = new ArrayList<String>();
        list.add("李四");
        list.add("王五");
        Document dbObject = new Document("$in", list);
        
        Document delObject = new Document();
        delObject.put("name", dbObject);
        dbCollection.deleteMany(delObject);
    }
    
    /**
     * @Title: deleteAll
     * @Description: TODO 删除全部的记录
     * @param dbCollection
     * @return: void
     */
    public static void deleteAll(MongoCollection<Document> dbCollection){
    	FindIterable<Document> iter = dbCollection.find();
        iter.forEach(new Block<Document>() {
        	public void apply(Document _doc) {
        		dbCollection.deleteOne(_doc);
        	}
    	});
    }
}