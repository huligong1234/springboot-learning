<template>
  <div>
    <h1>SpringBoot+MyBatis+Vue</h1>
    <router-link to="/add">新增</router-link>
    <table>
      <tr>
        <th>ID</th>
        <th>创建时间</th>
        <th>应用编号</th>
        <th>应用名称</th>
        <th>编辑</th>
        <th>删除</th>
      </tr>
      <tr v-for="item in appData" :key="item.id">
        <td>{{item.id}}</td>
        <td>{{item.gmtCreate}}</td>
        <td>{{item.appCode}}</td>
        <td>{{item.appName}}</td>
        <td>
          <router-link :to="{ path: '/edit', query: {id: item.id} }">编辑</router-link>
        </td>
        <td @click="deleted(item.id)">删除</td>
      </tr>
    </table>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  data () {
    return {
      appData: ''
    };
  },
  methods: {
    getData () {
      axios
        .get('http://localhost:8080/app/all')
        .then(response => {
          this.appData = response.data;
          console.log(response);
        })
        .catch(error => {
          console.log(errror);
        });
    },
    deleted (id) {
      var deleteConfirm = confirm('是否删除');
      if (deleteConfirm) {
        axios
          .delete('http://localhost:8080/app/delete', {
            params: { id: id }
          })
          .then(response => {
            console.log(response);
            this.getData();
          })
          .catch(error => {
            console.log(error);
          });
      }
    }
  },
  created () {
    this.getData();
  }
};
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
table {
  border-collapse: collapse;
}
td,
th {
  border: 1px solid #000;
}
</style>
