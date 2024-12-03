import request from '@/utils/request'

// 查询订单管理列表
export function listReceivingOrders(query) {
  return request({
    url: '/system/receivingOrders/list',
    method: 'get',
    params: query
  })
}

// 查询订单管理列表
export function getAvailablePersonnel(query) {
  return request({
    url: '/system/receivingOrders/availablePersonnel',
    method: 'get',
    params: query
  })
}

// 查询订单管理详细
export function getReceivingOrders(id) {
  return request({
    url: '/system/receivingOrders/' + id,
    method: 'get'
  })
}

// 新增订单管理
export function addReceivingOrders(data) {
  return request({
    url: '/system/receivingOrders',
    method: 'post',
    data: data
  })
}

// 修改订单管理
export function updateReceivingOrders(data) {
  return request({
    url: '/system/receivingOrders',
    method: 'put',
    data: data
  })
}

// 删除订单管理
export function delReceivingOrders(id) {
  return request({
    url: '/system/receivingOrders' + id,
    method: 'delete'
  })
}
