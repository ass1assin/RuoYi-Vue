import request from '@/utils/request'

// 通用上传请求（多个文件）
export function upload(data) {
  return request({
    url: '/common/uploads',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}
