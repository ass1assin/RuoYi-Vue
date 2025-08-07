import request from "@/utils/request";


export function genRentContract(query){
  return request({
    url:'/contract/genRentContract',
    method:'post',
    data: query
  })
}
