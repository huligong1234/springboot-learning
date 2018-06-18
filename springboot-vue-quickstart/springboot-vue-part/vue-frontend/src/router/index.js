import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/components/index'
import Add from '@/components/add'
import Edit from '@/components/edit'

Vue.use(Router)

export default new Router({
  routes: [{
    path: '/',
    name: 'Index',
    component: Index
  },
  {
    path: '/add',
    name: 'Add',
    component: Add
  },
  {
    path: '/edit',
    name: 'Edit',
    component: Edit
  }
  ]
});