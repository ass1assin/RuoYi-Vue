import request from '@/utils/request'

// 查询服务人员管理列表
export function listPersonnel(query) {
  return request({
    url: '/housekeeping/personnel/list',
    method: 'get',
    params: query
  })
}

// 查询服务人员管理详细
export function getPersonnel(id) {
  return request({
    url: '/housekeeping/personnel/' + id,
    method: 'get'
  })
}

// 新增服务人员管理
export function addPersonnel(data) {
  return request({
    url: '/housekeeping/personnel',
    method: 'post',
    data: data
  })
}

// 修改服务人员管理
export function updatePersonnel(data) {
  return request({
    url: '/housekeeping/personnel',
    method: 'put',
    data: data
  })
}

// 删除服务人员管理
export function delPersonnel(id) {
  return request({
    url: '/housekeeping/personnel/' + id,
    method: 'delete'
  })
}
