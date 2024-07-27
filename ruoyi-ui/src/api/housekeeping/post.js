import request from '@/utils/request'

// 查询服务发布列表
export function listPost(query) {
  return request({
    url: '/housekeeping/post/list',
    method: 'get',
    params: query
  })
}

// 查询服务发布详细
export function getPost(id) {
  return request({
    url: '/housekeeping/post/' + id,
    method: 'get'
  })
}

// 新增服务发布
export function addPost(data) {
  return request({
    url: '/housekeeping/post',
    method: 'post',
    data: data
  })
}

// 修改服务发布
export function updatePost(data) {
  return request({
    url: '/housekeeping/post',
    method: 'put',
    data: data
  })
}

// 删除服务发布
export function delPost(id) {
  return request({
    url: '/housekeeping/post/' + id,
    method: 'delete'
  })
}
