<template>
    <div>
        <h1>编辑</h1>
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
        <button @click="editData">确定</button>
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
    getData () {
      axios
        .get('http://localhost:8080/app/find', {
          params: {
            id: this.$route.query.id
          }
        })
        .then(response => {
          this.appCode = response.data.appCode;
          this.appName = response.data.appName;
          console.log(response);
        })
        .catch(error => {
          console.log(errror);
        });
    },
    editData () {
      axios({
        method: 'put',
        url: 'http://localhost:8080/app/update',
        params:{
          "id":this.$route.query.id,
          "appCode":this.appCode,
          "appName":this.appName
          }
      })
        .then(response => {
          console.log(response);
          this.$router.push({ path: '/' });
        })
        .catch(error => {
          console.log(error);
        });
    }
  },
  created () {
    this.getData();
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
