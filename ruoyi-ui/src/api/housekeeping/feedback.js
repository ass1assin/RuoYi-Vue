import request from '@/utils/request'

// 查询评价与反馈列表
export function listFeedback(query) {
  return request({
    url: '/housekeeping/feedback/list',
    method: 'get',
    params: query
  })
}

// 查询评价与反馈详细
export function getFeedback(id) {
  return request({
    url: '/housekeeping/feedback/' + id,
    method: 'get'
  })
}

// 新增评价与反馈
export function addFeedback(data) {
  return request({
    url: '/housekeeping/feedback',
    method: 'post',
    data: data
  })
}

// 修改评价与反馈
export function updateFeedback(data) {
  return request({
    url: '/housekeeping/feedback',
    method: 'put',
    data: data
  })
}

// 删除评价与反馈
export function delFeedback(id) {
  return request({
    url: '/housekeeping/feedback/' + id,
    method: 'delete'
  })
}
