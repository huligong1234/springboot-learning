import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/app/findPage',
    method: 'get',
    params
  })
}

export function createApp(data) {
  return request({
    url: '/app/add',
    method: 'post',
    // data
    params: data
  })
}
/*
export function updateApp(data) {
  return request({
    url: '/app/update',
    method: 'put',
    data
  })
}
*/
export function updateApp(data) {
  return request({
    url: '/app/update',
    method: 'put',
    params: data
  })
}

export function deleteApp(data) {
  return request({
    url: '/app/delete',
    method: 'delete',
    params: data
  })
}
