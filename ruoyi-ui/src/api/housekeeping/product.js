import request from '@/utils/request'

// 查询商品管理列表
export function listProduct(query) {
  return request({
    url: '/housekeeping/product/list',
    method: 'get',
    params: query
  })
}

// 查询商品管理详细
export function getProduct(id) {
  return request({
    url: '/housekeeping/product/' + id,
    method: 'get'
  })
}

// 新增商品管理
export function addProduct(data) {
  return request({
    url: '/housekeeping/product',
    method: 'post',
    data: data
  })
}

// 修改商品管理
export function updateProduct(data) {
  return request({
    url: '/housekeeping/product',
    method: 'put',
    data: data
  })
}

// 删除商品管理
export function delProduct(id) {
  return request({
    url: '/housekeeping/product/' + id,
    method: 'delete'
  })
}
