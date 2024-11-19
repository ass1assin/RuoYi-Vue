import request from '@/utils/request'

// 查询服务列表
export function listService(query) {
  return request({
    url: '/system/service/list',
    method: 'get',
    params: query
  })
}

// 查询服务详细
export function getService(id) {
  return request({
    url: '/system/service/' + id,
    method: 'get'
  })
}

// 新增服务
export function addService(data) {
  return request({
    url: '/system/service',
    method: 'post',
    data: data
  })
}

// 修改服务
export function updateService(data) {
  return request({
    url: '/system/service',
    method: 'put',
    data: data
  })
}

// 删除服务
export function delService(id) {
  return request({
    url: '/system/service/' + id,
    method: 'delete'
  })
}
