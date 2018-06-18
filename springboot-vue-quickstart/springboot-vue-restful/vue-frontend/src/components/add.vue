<template>
    <div>
        <h1>新增</h1>
        <hr>
        <label>
            <span>应用编号:</span>
            <input type="text" v-model="appCode">
        </label>
        <br>
        <label>
            <span>应用名称:</span>
            <input type="text" v-model="appName">
        </label>
        <br>
        <button @click="addData">确定</button>
    </div>
</template>

<script>
import axios from 'axios';
export default {
  data () {
    return {
      appCode: '',
      appName: ''
    };
  },
  methods: {
    addData () {
      axios({
        method: 'post',
        url: 'http://localhost:8080/app/add',
        params: {'appCode': this.appCode,'appName': this.appName}
      })
        .then(response => {
          console.log(response);
          this.$router.push({ path: '/' });
        })
        .catch(error => {
          console.log(error);
        });
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
tr {
  border: 1px solid #000;
}
</style>
