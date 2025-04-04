import request from '@/utils/request'

// 查询订单管理列表
export function listOrder(query) {
  return request({
    url: '/housekeeping/order/list',
    method: 'get',
    params: query
  })
}

// 查询订单管理详细
export function getOrder(id) {
  return request({
    url: '/housekeeping/order/' + id,
    method: 'get'
  })
}


// 删除订单管理
export function delOrder(id) {
  return request({
    url: '/housekeeping/order/' + id,
    method: 'delete'
  })
}
