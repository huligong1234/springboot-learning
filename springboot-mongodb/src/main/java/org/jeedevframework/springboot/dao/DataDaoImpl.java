package org.jeedevframework.springboot.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.jeedevframework.springboot.common.dao.BaseMongoDaoImpl;
import org.jeedevframework.springboot.entity.DataEntity;
import org.jeedevframework.springboot.utils.MongoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.SessionSynchronization;
import org.springframework.data.mongodb.core.ScriptOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.script.ExecutableMongoScript;
import org.springframework.data.mongodb.core.script.NamedMongoScript;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

/**
 * 已审核数据持久化实现
 */
@Repository("dataDao")
public class DataDaoImpl extends BaseMongoDaoImpl<DataEntity> implements DataDao {
	
    @Autowired
    private Mongo mongo;
    
	public Object findReport () {
		String mongoQL = "function() {\r\n" + 
				"    var result = [];\r\n" + 
				"    db.de_xy052_data.find({\r\n" + 
				"        'props.projectCode.value': 'P232'\r\n" + 
				"    }).forEach(function(row) {\r\n" + 
				"        db.de_xy052_data_history.find({\r\n" + 
				"            'props.projectCode.value': 'P232'\r\n" + 
				"        }).sort({\r\n" + 
				"            'dataVersion': 1\r\n" + 
				"        }).forEach(function(historyRow) {\r\n" + 
				"            row.props.totalUseArea[historyRow.props.phase.value] = historyRow.props.totalUseArea.value;\r\n" + 
				"        });\r\n" + 
				"\r\n" + 
				"        row.props.totalUseArea[row.props.phase.value] = row.props.totalUseArea.value;\r\n" + 
				"        row.businessTypePropTables = [];\r\n" + 
				"        db.de_xy055_data.find({\r\n" + 
				"            'props.projectVersionId.value': 'CuCEuI6LZP5Ix3QU6hoWVp'\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"            'props.projectCode.value': 1,\r\n" + 
				"            'props.phase.value': 1,\r\n" + 
				"            'props.firstBusinessTypeName.value': 1,\r\n" + 
				"            'props.firstBusinessTypeCode.value': 1,\r\n" + 
				"            'props.businessTypeName.value': 1,\r\n" + 
				"            'props.businessTypeCode.value': 1,\r\n" + 
				"            'props.totalBuildingArea.value': 1\r\n" + 
				"        }).forEach(function(btRow) {\r\n" + 
				"            db.de_xy055_data_history.find({\r\n" + 
				"                'props.projectVersionId.value': 'CuCEuI6LZP5Ix3QU6hoWVp'\r\n" + 
				"            }).sort({\r\n" + 
				"                'dataVersion': 1\r\n" + 
				"            }).forEach(function(historyRow) {\r\n" + 
				"				var curdata = btRow.props.totalBuildingArea[historyRow.props.phase.value] || 0;\r\n" + 
				"                btRow.props.totalBuildingArea[historyRow.props.phase.value] = curdata + historyRow.props.totalBuildingArea.value;\r\n" + 
				"            });\r\n" + 
				"\r\n" + 
				"			var curdata = btRow.props.totalBuildingArea[btRow.props.phase.value] || 0;\r\n" + 
				"            btRow.props.totalBuildingArea[btRow.props.phase.value] = curdata + btRow.props.totalBuildingArea.value;\r\n" + 
				"            row.businessTypePropTables.push(btRow);\r\n" + 
				"            result.push(row);\r\n" + 
				"        });\r\n" + 
				"    });\r\n" + 
				"\r\n" + 
				"    return result;\r\n" + 
				"}";
		
		ScriptOperations scriptOps = mongoTemplate.scriptOps();
		
    	// Execute script directly
		ExecutableMongoScript echoScript = new ExecutableMongoScript(mongoQL);
    	//Object result = scriptOps.execute(echoScript, "directly execute script");     

		/*Object result = runMongoCommand(mongoQL);
		return result;
		*/
		
		String jsonSql="{distinct:'de_xy052_data', key:'sn'}";
		Document result = mongoTemplate.executeCommand(jsonSql);
		List<BasicDBObject> l = (List<BasicDBObject>)result.get("retval");
    	return result;
		
    	/*List<AggregationOperation> operations = new ArrayList<>();
    	operations.add(Aggregation.group("$props.phase.value","projectVersionId"));
    	
    	Aggregation aggregation = Aggregation.newAggregation(operations);
    	mongoTemplate.aggregate(aggregation, "de_xy052_data", Map.class);*/
    	// Register script and call it later
    	//scriptOps.register(new NamedMongoScript("echo", echoScript)); 
    	
    	
    	//ArrayList result = (ArrayList)scriptOps.call("phaseGroupTotal", "P022");
		//return result.get(0);  
    }
	
	public void aggregationExample(String projectCode,String stageCode) {
		Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(
                        Criteria.where(MongoUtils.getPropValueKey("projectCode")).is(projectCode)
                        .and(MongoUtils.getPropValueKey("stageCode")).is(stageCode)
                        .and(DataEntity.DATA_STATUS).is("0")
                ),
                Aggregation.sort(Sort.Direction.DESC,"dataVersion"),
                Aggregation.group(MongoUtils.getPropValueKey("phase"))
                        //.first("dataVersion").as("dataVersion")
                        .first(MongoUtils.getPropValueKey("phase")).as("phase")
                        .first(MongoUtils.getPropValueKey("stageVersionId")).as("stageVersionId")
                        .first(MongoUtils.getPropValueKey("projectVersionId")).as("projectVersionId")
        );
        AggregationResults<HashMap> results = mongoTemplate.aggregate(agg, "test_data", HashMap.class);
        List<HashMap> list = results.getMappedResults();
        if(!CollectionUtils.isEmpty(list)) {
            for(HashMap m : list) {
                String phase = (String)m.get("phase");
                //TODO 
            }
        }
	}
	
    private Object runMongoCommand(String script) {
        if (transactionEnabled) {
            // 开启MongoDB事务
            mongoTemplate.setSessionSynchronization(SessionSynchronization.ALWAYS);
        }
        DB db = new DB(mongo, mongoTemplate.getDb().getName());
        DBObject command = new BasicDBObject();
        ((BasicDBObject) command).put("eval", script);
        CommandResult result = db.command(command);
        return result.get("retval");
    }
}
