# SpringBoot + Spring Data MongoDB 学习笔记


本项目示例主要用于快速配置Spring Data MongoDB 运行环境


### 示例（类似select into from）

```javascript
#先将data移动到history
#再将data_new 或data_change复制到data

db.project_data.find({
    'props.projectVersionId.value': 'A0PAC5cfdKuYF87KAuDFrR'
}).forEach(function(project) {
    db.project_data_history.insert(project);
    db.project_data_history.insert(project);
});

db.project_data_new.find({
    'props.projectVersionId.value': 'A0PAC5cfdKuYF87KAuDFrR'
}).forEach(function(project) {
    db.project_data.insert(project);
});

```

### 示例：层级数据变更

```javascript
db.project_data.find({
    'props.projectVersionId.value': 'A0PAC5cfdKuYF87KAuDFrR'
}).forEach(function(project) {
    project._id = project.props.projectVersionId = UUID();
    project.reviewStatus = 0;
    project.dataVersion =+ 1;
    db.project_data_change.insert(project);
    
    db.stage_data.find({
        'props.projectVersionId.value': 'A0PAC5cfdKuYF87KAuDFrR'
    }).forEach(function(stage) {
        stage._id = stage.props.stageVersionId = UUID();
        stage.props.projectVersionId = project._id;
        stage.reviewStatus = 0;
        stage.dataVersion =+ 1;
        db.stage_data_change.insert(stage);
        
        db.business_type_data.find()({
            'props.projectVersionId.value': 'A0PAC5cfdKuYF87KAuDFrR'
        }).forEach(function(businessType) {
            businessType._id = businessType.props.businessTypeVersionId = UUID();
            businessType.props.projectVersionId = project._id;
            businessType.props.stageVersionId = stage._id;
            businessType.dataVersion =+ 1;
            db.business_type_data_change.insert(businessType);
            
            db.building_data.find({
                'props.projectVersionId.value': 'A0PAC5cfdKuYF87KAuDFrR'
            }).forEach(function(building) {
                building._id = building.props.buildingVersionId = UUID();
                building.props.projectVersionId = project._id;
                building.props.stageVersionId = stage._id;
                building.props.businessTypeVersionId = businessType._id;
                building.dataVersion =+ 1;
                db.building_data_change.insert(building);
                
                db.house_data.find({
                    'props.projectVersionId.value': 'BXX9QKNF9EsoWXh_LHi66E'
                }).forEach(function(house) {
                    house._id = house.props.houseVersionId = UUID();
                    house.props.projectVersionId = project._id;
                    house.props.stageVersionId = stage._id;
                    house.props.businessTypeVersionId = businessType._id;
                    house.props.buildingVersionId = building._id;
                    house.dataVersion =+ 1;
                    db.house_data_change.insert(house);
                });
            });
        });
    });
});
```

### 示例：层级数据汇总
```javascript
db.runCommand(
    {
        mapreduce: "stage_data_new",
        map: function() {
            emit(this.props.stageName.value, {
                totalUseArea: this.props.totalUseArea.value 
            });
        },
        reduce: function(key, values) {
            var reduced = {
                totalUseArea: 0
            };
            
            values.forEach(function(val) {
                reduced.totalUseArea += val.totalUseArea;
            });
            return reduced;
        },
        finalize: function(key, reducedVal) {
            return reducedVal;
        },
        query: {
            "props.projectVersionId.value": 'BXX9QKNF9EsoWXh_LHi66E'
        },
        out: "mrout_stage_data_new"
    }
);

db.mrout_stage_data_new.find().forEach(function(x) {
    db.project_data_new.update({
        '_id': 'BXX9QKNF9EsoWXh_LHi66E'
    }, {
        $set: {
            'props.totalUseArea.value': x.value.totalUseArea
        }
    });
});
```
