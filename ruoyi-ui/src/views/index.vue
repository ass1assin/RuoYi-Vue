<template>
  <div class="screen-container" style="background:#FFFFFF  center top;">
    <div class="loading" v-show="isLoading">
      <div class="loadbox"> 页面加载中...</div>
    </div>
    <div class="back"></div>
    <div class="head">
      <h1>爱康家政运营情况</h1>
    </div>
    <div class="mainbox">
      <ul class="clearfix">
        <li class="center-column">  <!-- 中间列 -->
          <!-- 今日收入 -->
          <div class="boxall" style="height:200px">
            <div class="alltitle">爱康家政收入情况</div>

            <div class="income-box">
              <div class="income-item">
                <div class="income-title">总收入<span>(元)</span></div>
                <div class="income-value">{{ todayIncome.toFixed(2) }}</div>
              </div>
              <div class="income-item">
                <div class="income-title">当月收入<span>(元)</span></div>
                <div class="income-value">{{ monthIncome.toFixed(2) }}</div>
              </div>
              <div class="income-item">
                <div class="income-title">年度收入<span>(元)</span></div>
                <div class="income-value">{{ yearIncome.toFixed(2) }}</div>
              </div>

            </div>

          </div>
          <!-- 收入趋势 - 放大并居中 -->
          <div class="boxall trend-chart" style="height:450px">  <!-- 增加高度 -->
            <div class="alltitle">各区域订单分布</div>
            <div class="navboxall" id="echart4"></div>
          </div>
        </li>

        <li class="right-column">  <!-- 右侧列 -->
          <!-- 最近停车记录 -->
          <div class="boxall" style="height:260px;">
            <div class="alltitle">最近下单记录</div>
            <div class="navboxall">
              <div class="record-table">
                <div class="record-header">
                  <span>下单时间</span>
                  <span>服务</span>
                  <span>费用</span>
                  <span>地点</span>
                  <span>时长</span>
                </div>
                <div class="record-body">
                  <ul>
                    <li v-for="record in parkingRecords" :key="record.id">
                      <span>{{ record.createTime }}</span>
                      <span>{{ record.serviceName }}</span>
                      <span>{{ record.totalPrice }}</span>
                      <span>{{ record.location }}</span>
                      <span>{{ record.orderPackage }}小时</span>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
          <div class="boxall" style="height:340px">
            <div class="alltitle">服务人员情况</div>
            <div class="navboxall">
              <div class="debt-table">
                <div class="debt-header">
                  <span>姓名</span>
                  <span>擅长服务</span>
                  <span>经历</span>
                </div>
                <div class="debt-body">
                  <ul>
                    <li v-for="debt in debtVehicles" :key="debt.id">
                      <span>{{ debt.name }}</span>
                      <span >{{ debt.serviceType }}</span>
                      <span>{{ debt.experience }}</span>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
          <!-- 长时间停车情况 -->
          <div class="boxall" style="height:320px">
<!--            <div class="boxall" style="height:260px;">-->
              <div class="alltitle">服务评论情况</div>
              <div class="navboxall">
                <div class="admin-status">
                  <div v-for="admin in adminStatus" :key="admin.userId" class="admin-item">
                    <img :src="admin.avatar" class="admin-avatar" />
                    <div class="admin-info">
                      <div class="admin-name">{{ admin.userName }}</div>
                      <div class="admin-name">{{ admin.content }}</div>
                    </div>
                  </div>
                </div>
            </div>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import { getOrder,listOrder } from '@/api/housekeeping/order'
import {listPersonnel} from '@/api/housekeeping/personnel'
import {listComments} from "@/api/system/comments";
import * as echarts from 'echarts'

export default {
  name: 'Home',
  data() {
    return {
      isLoading: true,
      todayIncome: 0,
      yearIncome: 0,
      monthIncome: 0,
      parkingRecords: [], // 订单记录
      adminStatus: [], // 评论数据
      debtVehicles: [], // 服务人员数据
      orderChart: null,
      orderList: []
    }
  },
  mounted() {
    this.loadScripts()
    this.initData()
    this.startAutoRefresh()
    this.initOrderChart()
    this.getOrderData()
  },
  beforeDestroy() {
    // 清除所有定时器
    if (this.timer) {
      clearInterval(this.timer)
    }
    if (this.statusTimer) {
      clearInterval(this.statusTimer)
    }
    window.removeEventListener('resize', this.handleResize)
    this.orderChart && this.orderChart.dispose()
  },
  methods: {
    loadScripts() {
      const loadScript = (src) => {
        return new Promise((resolve, reject) => {
          const script = document.createElement('script')
          script.src = src
          script.onload = resolve
          script.onerror = reject
          document.head.appendChild(script)
        })
      }

      // 加载CSS
      const link = document.createElement('link')
      link.rel = 'stylesheet'
      link.href = 'css/comon0.css'
      document.head.appendChild(link)

      // 按顺序加载JS文件
      Promise.all([
        loadScript('js/jquery.js'),
        loadScript('js/echarts.min.js')
      ])
        .then(() => Promise.all([
          loadScript('js/jquery.liMarquee.js'),
          loadScript('js/jquery.cxselect.min.js')
        ]))
        .then(() => loadScript('js/js.js'))
        .then(() => {
          // 初始化时钟
          this.initClock()
          // 初始化滚动
          if (window.$) {
            $('.wrap,.adduser').liMarquee({
              direction: 'up',
              scrollamount: 20,
              runshort: false
            })
          }
          // 加载收入数据并初始化表
          this.loadIncomeData()
          // 隐藏加载动画
          this.isLoading = false
        })
        .catch(error => {
          console.error('Script loading failed:', error)
          this.isLoading = false  // 即使加载失败也要隐藏加载动画
        })
    },

    initClock() {
      const updateTime = () => {
        const now = new Date()
        const timeStr = `${now.getFullYear()}年${now.getMonth() + 1}月${now.getDate()}日${now.getHours()}时${now.getMinutes()}分${now.getSeconds()}秒`
        const timeElement = document.getElementById('showTime')
        if (timeElement) {
          timeElement.innerHTML = timeStr
        }
      }

      updateTime()
      setInterval(updateTime, 1000)
    },

    // 初始化所有数据
    async initData() {
      try {
        await Promise.all([
          this.loadAdminStatus(),
          this.loadIncomeData(),
          this.loadRecentRecords(),
          this.loadDebtVehicles(),
        ])
      } catch (error) {
        console.error('加载数据失败:', error)
      }
    },

    // 加载管理员状态
    async loadAdminStatus() {
      const res = await listComments()
      console.log("res %o", res)
      // if (res.code === '200') {
        this.adminStatus = res.rows
      // }
    },

    // 加载收入相关数据
    async loadIncomeData() {
      try {
        const res = await listOrder()
        if (res.code === 200) {
          const orders = res.rows
          const now = new Date()
          const currentMonth = now.getMonth() + 1
          const currentYear = now.getFullYear()

          // 计算总收入
          this.todayIncome = orders.reduce((sum, order) => sum + order.totalPrice, 0)

          // 计算月收入
          this.monthIncome = orders.reduce((sum, order) => {
            const orderDate = new Date(order.createTime)
            if (orderDate.getMonth() + 1 === currentMonth &&
                orderDate.getFullYear() === currentYear) {
              return sum + order.totalPrice
            }
            return sum
          }, 0)

          // 计算年收入
          this.yearIncome = orders.reduce((sum, order) => {
            const orderDate = new Date(order.createTime)
            if (orderDate.getFullYear() === currentYear) {
              return sum + order.totalPrice
            }
            return sum
          }, 0)
        }
      } catch (error) {
        console.error('加载收入数据失败:', error)
      }
    },

    // 加载车位状态
    async loadParkingStatus() {
      try {
        const res = await listOrder()
        if (res.code === '200') {
          this.regions = res.data
        }
      } catch (error) {
        console.error('加载车位状态失败:', error)
      }
    },

    // 加载最近停车记录
    async loadRecentRecords() {
      // try {
        const res = await listOrder()
      console.log("ddfgdfgdf")
        // if (res.code === '200') {
      // console.log("res.data %o", res.rows)
          this.parkingRecords = res.rows
          // console.log("dddddd %o", this.parkingRecords)
      //   }
      // } catch (error) {
      //   console.error('加载停车记录失败:', error)
      // }
    },

    // 添加计算时长的方法
    calculateDuration(inTime, leftTime) {
      const start = new Date(inTime)
      const end = leftTime ? new Date(leftTime) : new Date()
      const diff = end - start

      // 转换小时和分钟
      const hours = Math.floor(diff / (1000 * 60 * 60))
      const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60))

      if (hours > 0) {
        return `${hours}小时${minutes}分钟`
      } else {
        return `${minutes}分钟`
      }
    },

    // 加载欠费车辆
    async loadDebtVehicles() {
      // try {
        const res = await listPersonnel()
        // if (res.code === '200') {
          this.debtVehicles = res.rows
        // }
      // } catch (error) {
      //   console.error('加载欠费车辆失败:', error)
      // }
    },

    // 获取当前期
    getCurrentDate() {
      return new Date().toISOString().split('T')[0]
    },

    // 启动定时刷新
    startAutoRefresh() {
      // 清除可能存在的旧定时器
      if (this.timer) {
        clearInterval(this.timer)
      }
      // 设置新的定时器，分钟刷新一次
      this.timer = setInterval(() => {
        this.initData()
      }, 60000)
    },

    // 修改更新图表的方法
    updateIncomeChart() {
      const chart = echarts.init(document.getElementById('echart4'))
      const colors = ['#00f2f1', '#00ff00', '#ffff00', '#ff00ff', '#ff0000'] // 不同区域使用不同颜色

      const option = {
        tooltip: {
          trigger: 'axis',
          formatter: function(params) {
            let result = params[0].axisValue + '日<br/>'
            params.forEach(param => {
              result += `${param.seriesName}：${param.value}元<br/>`
            })
            return result
          }
        },
        legend: {
          data: this.regions,
          textStyle: {
            color: '#fff'
          },
          top: 0
        },
        grid: {
          top: '15%',
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: Array.from({length: this.dailyIncomes[0].data.length}, (_, i) => i + 1),
          axisLabel: {
            color: '#fff',
            formatter: '{value}日'
          },
          axisLine: {
            lineStyle: {
              color: 'rgba(255,255,255,0.2)'
            }
          }
        },
        yAxis: {
          type: 'value',
          axisLabel: {
            color: '#fff',
            formatter: '{value}元'
          },
          splitLine: {
            lineStyle: {
              color: 'rgba(255,255,255,0.1)'
            }
          },
          axisLine: {
            lineStyle: {
              color: 'rgba(255,255,255,0.2)'
            }
          }
        },
        series: this.dailyIncomes.map((region, index) => ({
          name: region.name,
          type: 'line',
          smooth: true,
          symbol: 'circle',
          symbolSize: 8,
          data: region.data,
          itemStyle: {
            color: colors[index]
          },
          areaStyle: {
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
              offset: 0,
              color: colors[index].replace(')', ',0.3)')
            }, {
              offset: 1,
              color: colors[index].replace(')', ',0.1)')
            }])
          },
          lineStyle: {
            width: 2
          }
        }))
      }

      chart.setOption(option)

      // 添加自适应
      window.addEventListener('resize', () => {
        chart.resize()
      })
    },

    // 处理区域切换
    async handleRegionChange(regionId) {
      try {
        const res = await this.$request.get('/spot/selectAll', {
          params: { regionId }
        })
        if (res.code === '200') {
          this.parkingSpots = res.data.map(spot => ({
            spotId: spot.spotId,
            spotNumber: spot.spotNumber,
            spotStatus: spot.spotStatus || '空闲'
          }));

          this.startRealTimeUpdate();
        }
      } catch (error) {
        console.error('加载区域车位失败:', error)
        this.$message.error('加载车位数据失败')
      }
    },

    // 更新车位状态
    async updateParkingStatus() {
      if (!this.selectedRegion) return
      try {
        const res = await this.$request.get('/spot/status', {
          params: { regionId: this.selectedRegion }
        })
        if (res.code === '200') {
          // 更新现有数据而不是完全替换
          const newSpots = res.data;
          this.parkingSpots = this.parkingSpots.map(spot => {
            const newSpot = newSpots.find(s => s.spotId === spot.spotId);
            return newSpot || spot;
          });
        }
      } catch (error) {
        console.error('更新车位状态失败:', error)
      }
    },

    // 启动实时更新
    startRealTimeUpdate() {
      // 清除可能存在的旧定时器
      if (this.statusTimer) {
        clearInterval(this.statusTimer)
      }
      // 设置新的定时器
      this.statusTimer = setInterval(() => {
        if (this.selectedRegion) {
          this.updateParkingStatus()
        }
      }, 10000) // 每10秒更新一次
    },

    // 加载区域排名数据
    async loadRegionRanking() {
      try {
        const res = await listOrder()
        if (res.code === '200') {
          this.regionRanking = res.data
        }
      } catch (error) {
        console.error('加载月度区域排行失败:', error)
      }
    },

    // 加载区域泊位统计数据
    async loadParkingLots() {
      try {
        const res = await listOrder()
        if (res.code === '200') {
          this.parkingLots = res.data
        }
      } catch (error) {
        console.error('加载区域泊位统计失败:', error)
      }
    },

    // 更新泊位统计图表
    updateParkingChart() {
      const chart = echarts.init(document.getElementById('echart5'));

      const option = {
        tooltip: {
          show: false
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        series: this.parkingLots.map((lot, index) => ({
          name: lot.regionName,
          type: 'pie',
          radius: ['25%', '35%'],
          center: [`${20 + index * 30}%`, '35%'],
          data: [
            {
              name: '已使用',
              value: lot.total - lot.available,
              itemStyle: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  { offset: 0, color: '#F56C6C' },
                  { offset: 1, color: '#E6A23C' }
                ])
              },
              label: {
                show: true,
                position: 'inside',
                formatter: `${lot.total - lot.available}`,
                color: '#fff',
                fontSize: 12
              },
              labelLine: {
                show: false
              }
            },
            {
              name: '空闲',
              value: lot.available,
              itemStyle: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  { offset: 0, color: '#67C23A' },
                  { offset: 1, color: '#409EFF' }
                ])
              },
              label: {
                show: false
              },
              labelLine: {
                show: false
              }
            }
          ]
        })),
        graphic: this.parkingLots.map((lot, index) => [
          {
            type: 'text',
            left: `${20 + index * 30}%`,
            top: '65%',
            style: {
              text: lot.regionName,
              textAlign: 'center',
              fill: '#fff',
              fontSize: 14,
              fontWeight: 'bold'
            }
          },
          {
            type: 'text',
            left: `${20 + index * 30}%`,
            top: '75%',
            style: {
              text: `${lot.total - lot.available}/${lot.total}`,
              textAlign: 'center',
              fill: '#fff',
              fontSize: 12
            }
          }
        ]).flat()
      };

      chart.setOption(option);

      window.addEventListener('resize', () => {
        chart.resize();
      });
    },

    // 格式化月份显示
    formatMonth() {
      const now = new Date()
      this.currentMonth = `${now.getFullYear()}年${now.getMonth() + 1}月`
    },

    // 加载30分钟停车趋势数据
    async loadParkingTrends() {
      try {
        const res = await listOrder()
        if (res.code === '200') {
          // 取所有区域名称（从regions数据中提取）
          const allRegions = [...new Set(this.regions.map(r => r.regionName))]

          // 合并相同区域的数据
          const mergedData = res.data.reduce((acc, curr) => {
            const existingRegion = acc.find(item => item.regionName === curr.regionName)
            if (existingRegion) {
              existingRegion.count += curr.count
            } else {
              acc.push({
                regionName: curr.regionName,
                count: curr.count
              })
            }
            return acc
          }, [])

          // 确保所有区域都有数据，没有数据的设为0
          this.parkingTrends = allRegions.map(regionName => {
            const existingData = mergedData.find(item => item.regionName === regionName)
            return {
              regionName: regionName,
              count: existingData ? existingData.count : 0
            }
          })

          this.updateParkingTrendChart()
        }
      } catch (error) {
        console.error('加载停车趋势数据失败:', error)
        // 发生错误时也显示空数据
        if (this.regions.length > 0) {
          const allRegions = [...new Set(this.regions.map(r => r.regionName))]
          this.parkingTrends = allRegions.map(regionName => ({
            regionName: regionName,
            count: 0
          }))
          this.updateParkingTrendChart()
        }
      }
    },

    // 更新停车趋势图表
    updateParkingTrendChart() {
      const chart = echarts.init(document.getElementById('echart4'))

      const option = {
        tooltip: {
          trigger: 'axis',
          formatter: function(params) {
            return `${params[0].name}<br/>停车数量：${params[0].value}辆`
          }
        },
        grid: {
          top: '15%',
          left: '5%',
          right: '5%',
          bottom: '10%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: this.parkingTrends.map(item => item.regionName),
          axisLabel: {
            color: '#fff',
            interval: 0,  // 强制显示所有标签
            rotate: 30    // 标签旋转30度，防止重叠
          },
          axisLine: {
            lineStyle: {
              color: 'rgba(255,255,255,0.2)'
            }
          }
        },
        yAxis: {
          type: 'value',
          name: '停车数量',
          nameTextStyle: {
            color: '#fff'
          },
          axisLabel: {
            color: '#fff'
          },
          splitLine: {
            lineStyle: {
              color: 'rgba(255,255,255,0.1)'
            }
          },
          axisLine: {
            lineStyle: {
              color: 'rgba(255,255,255,0.2)'
            }
          },
          min: 0,  // 设置最小值为0
          minInterval: 10  // 设置最小间隔为1，确保显示整数
        },
        series: [{
          type: 'line',
          smooth: true,
          symbol: 'circle',
          symbolSize: 10,  // 增大数据点的大小
          data: this.parkingTrends.map(item => item.count),
          itemStyle: {
            color: '#409EFF'
          },
          areaStyle: {
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              { offset: 0, color: 'rgba(64,158,255,0.3)' },
              { offset: 1, color: 'rgba(64,158,255,0.1)' }
            ])
          },
          label: {
            show: true,
            position: 'top',
            textStyle: {
              color: '#fff'
            },
            fontSize: 14,  // 增大标签字体
            formatter: '{c}辆'
          }
        }]
      }

      chart.setOption(option)

      // 确保图表响应容器大小变化
      window.addEventListener('resize', () => {
        chart.resize()
      })
    },

    // 获取线条颜色
    getLineColor(index) {
      const colors = ['#409EFF', '#67C23A', '#E6A23C', '#F56C6C', '#909399']
      return colors[index % colors.length]
    },

    // 获取区域颜色
    getAreaColor(index, alpha) {
      const colors = ['#409EFF', '#67C23A', '#E6A23C', '#F56C6C', '#909399']
      const color = colors[index % colors.length]
      return color.replace(')', `, ${alpha})`)
    },

    // 根据数值长度设置data-length属性
    setFontSize(value) {
      const length = value.toString().length;
      return { 'data-length': length };
    },

    // 加载长时间停车记录
    async loadLongTimeRecords() {
      try {
        const res = await listOrder()
        if (res.code === '200') {
          this.longTimeRecords = res.data.map(record => ({
            id: record.recordId,
            plateNumber: record.carNumber,
            duration: this.calculateDuration(record.inTime), // 添加计算时长的方法
            region: `${record.regionName}-${record.road}`,
            status: record.status
          }))
        }
      } catch (error) {
        console.error('加载长时间停车记录失败:', error)
        this.longTimeRecords = [] // 出错时设置为空数组
      }
    },

    // 获取状态样式类
    getStatusClass(status) {
      switch (status) {
        case '异常':
          return 'status-danger'
        case '超时':
          return 'status-warning'
        default:
          return 'status-normal'
      }
    },

    // 初始化图表
    initOrderChart() {
      this.orderChart = echarts.init(document.getElementById('echart4'))
    },

    // 获取订单数据
    async getOrderData() {
      try {
        const res = await listOrder()
        if (res.code === 200) {
          this.orderList = res.rows
          this.updateOrderChart()
        }
      } catch (error) {
        console.error('获取订单数据失败:', error)
      }
    },

    // 更新图表
    updateOrderChart() {
      // 统计各区域的订单数量
      const areaStats = {}
      this.orderList.forEach(order => {
        // 从地址中提取区域名称（假设格式为"广州 xx区域 具体地址"）
        const area = order.location.split(' ')[1] || '其他'
        areaStats[area] = (areaStats[area] || 0) + 1
      })

      const option = {
        title: {
          text: '各区域订单分布',
          textStyle: {
            color: '#676f7d',
            fontSize: 14
          }
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: Object.keys(areaStats),
          axisLabel: {
            interval: 0,
            rotate: 30,
            color: '#fff'
          }
        },
        yAxis: {
          type: 'value',
          name: '订单数量',
          axisLabel: {
            color: '#fff'
          }
        },
        series: [{
          name: '订单数量',
          type: 'bar',
          data: Object.values(areaStats),
          itemStyle: {
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              { offset: 0, color: '#83bff6' },
              { offset: 0.5, color: '#188df0' },
              { offset: 1, color: '#188df0' }
            ])
          }
        }]
      }

      this.orderChart && this.orderChart.setOption(option)
    },

    // 窗口大小改变时重置图表大小
    handleResize() {
      this.orderChart && this.orderChart.resize()
    }
  },
  created() {
    window.addEventListener('resize', this.handleResize)
  }
}
</script>

<style>
.loading {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.7);
  z-index: 999;
}
.loadbox {
  position: absolute;
  left: 50%;
  top: 40%;
  transform: translate(-50%, -50%);
  color: #fff;
  text-align: center;
}
.loadbox img {
  display: block;
  margin: 0 auto 10px;
}

/* 确保容器区域占满整个视口 */
.home-container {
  width: 100%;
  height: 100vh;
  overflow: hidden;
}

/* 复可能的图表容器高度题 */
.boxall {
  position: relative;
}
.navboxall {
  width: 100%;
  height: 100%;
}

/* 添加新的样式 */
.screen-container {
  width: 100%;
  min-height: 100%;
  padding: 20px;
  box-sizing: border-box;
  background: linear-gradient(to bottom, #1a1a1a, #2d3a4b);
}

/* 调整主容器样式 */
.mainbox {
  width: 100%;
  min-height: calc(100vh - 120px);  /* 减去头部和padding的高度 */
}

/* 整列表布局 */
.clearfix {
  display: flex;
  justify-content: space-between;
  gap: 20px;
}

.clearfix > li {
  flex: 1;
}

/* 调整图表容器 */
.boxall {
  position: relative;
  margin-bottom: 20px;
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 10px;
  box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.37);
  transition: transform 0.3s ease;
}

.boxall:hover {
  transform: translateY(-5px);
}

/* 确保图表内容完整显示 */
.navboxall {
  width: 100%;
  height: 100%;
  min-height: 200px;  /* 设置最小高度 */
}

/* 调整表格样式 */
.table1 {
  width: 100%;
  margin: 0;
  padding: 0;
}

/* 调整滚动列表样式 */
.wrap {
  max-height: 200px;  /* 设置最大高度 */
  overflow-y: auto;  /* 允许内容滚动 */
}

/* 适配小屏幕 */
@media screen and (max-width: 1600px) {
  .clearfix {
    flex-wrap: nowrap; /* 防止换行 */
  }

  .center-column {
    flex: 1.5 !important; /* 在小屏幕上进一步增加中间列的比例 */
  }
}

/* 确保加载动画覆盖整个可滚动域 */
.loading {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.7);
  z-index: 999;
}

/* 管理员在岗情况样式 */
.admin-status {
  padding: 10px;
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
}

.admin-item {
  display: flex;
  align-items: center;
  background: rgba(255, 255, 255, 0.1);
  padding: 10px;
  border-radius: 5px;
  width: calc(50% - 10px);
}

.admin-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 10px;
}

.admin-info {
  flex: 1;
}

.admin-name {
  color: #fff;
  font-size: 14px;
  margin-bottom: 5px;
}

.admin-state {
  font-size: 12px;
  padding: 2px 8px;
  border-radius: 10px;
}

.online {
  background: #67C23A;
  color: #fff;
}

.offline {
  background: #909399;
  color: #fff;
}

/* 欠费车辆样式 */
.debt-amount {
  color: #F56C6C !important;
}

.debt-status {
  color: #E6A23C !important;
}

/* 调整表格样式 */
.table1 td, .table1 th {
  padding: 8px;
  text-align: center;
}

.table1 tr:hover {
  background: rgba(255, 255, 255, 0.1);
}

/* 修改区域选择器样式 */
.region-selector {
  margin-bottom: 20px;
  text-align: center;
  padding: 15px;
  background: rgba(255, 255, 255, 0.05);
  border-radius: 8px;
}

.region-selector .el-radio-button__inner {
  background: rgba(0, 0, 0, 0.2);
  border-color: rgba(64, 158, 255, 0.3);
  color: #fff;
  padding: 8px 20px;
  transition: all 0.3s ease;
}

.region-selector .el-radio-button__orig-radio:checked + .el-radio-button__inner {
  background-color: #409EFF;
  border-color: #409EFF;
  color: #fff;
  box-shadow: 0 0 10px rgba(64, 158, 255, 0.5);
}

/* 修改网格容器样式 */
.parking-grid {
  height: calc(100% - 70px);
  overflow: auto;
  padding: 8px;
  background: rgba(0, 0, 0, 0.2);
  border-radius: 6px;
}

.grid-container {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(50px, 1fr)); /* 更小的网格 */
  gap: 6px; /* 更小的间距 */
  padding: 6px;
}

.grid-item {
  aspect-ratio: 1;
  border-radius: 4px;
  padding: 4px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 12px; /* 更小的字体 */
  font-weight: bold;
  cursor: pointer;
  transition: all 0.2s ease;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.grid-item.free {
  background: rgba(103, 194, 58, 0.1);
  border: 1px solid #67C23A;
  color: #67C23A;
}

.grid-item.occupied {
  background: rgba(245, 108, 108, 0.1);
  border: 1px solid #F56C6C;
  color: #F56C6C;
}

.grid-item:hover {
  transform: scale(1.05);
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
  z-index: 1;
}

/* 添加滚动条样式 */
.parking-grid::-webkit-scrollbar {
  width: 4px;
  height: 4px;
}

.parking-grid::-webkit-scrollbar-track {
  background: rgba(255, 255, 255, 0.05);
}

.parking-grid::-webkit-scrollbar-thumb {
  background: rgba(255, 255, 255, 0.2);
  border-radius: 2px;
}

.parking-grid::-webkit-scrollbar-thumb:hover {
  background: rgba(255, 255, 255, 0.3);
}

.record-table {
  width: 100%;
  height: calc(100% - 30px);
  color: #fff;
}

.record-header {
  display: grid;
  grid-template-columns: 2fr 1fr 3fr 2fr;
  padding: 10px;
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(5px);
  border-radius: 8px 8px 0 0;
  font-weight: bold;
}

.record-header span {
  text-align: center;
  //color: #fff;
}

.record-body {
  height: calc(100% - 40px);
  overflow: hidden;
}

.record-body ul {
  margin: 0;
  padding: 0;
  list-style: none;
  animation: scroll 20s linear infinite;
}

.record-body li {
  display: grid;
  grid-template-columns: 2fr 1fr 3fr 2fr;
  padding: 8px 10px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  transition: background 0.3s ease;
}

.record-body li:hover {
  background: rgba(255, 255, 255, 0.08);
}

.record-body li span {
  text-align: center;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  color: #fff;
}

/* 添加滚动动画 */
@keyframes scroll {
  0% {
    transform: translateY(0);
  }
  100% {
    transform: translateY(-100%);
  }
}

/* 鼠标悬停时暂停滚动 */
.record-body:hover ul {
  animation-play-state: paused;
}

/* 修改原有的滚动样式 */
.wrap {
  height: calc(100% - 40px);
  overflow: hidden;
}

.wrap ul {
  animation: scroll 20s linear infinite;
}

@keyframes scroll {
  0% {
    transform: translateY(0);
  }
  100% {
    transform: translateY(-100%);
  }
}

/* 鼠标悬停时暂停滚动 */
.wrap:hover ul {
  animation-play-state: paused;
}

/* 添加到有的样式中 */
.month-tag {
  font-size: 14px;
  color: #409EFF;
  margin-left: 10px;
  padding: 2px 8px;
  background: rgba(64, 158, 255, 0.1);
  border-radius: 4px;
}

.top-rank {
  font-weight: bold;
  color: #F56C6C;
}

/* 调整表格样式 */
.table1 td, .table1 th {
  padding: 12px 8px;
  text-align: center;
}

.table1 tr:nth-child(-n+4) {
  background: rgba(255, 255, 255, 0.05);
}

.table1 tr:hover {
  background: rgba(64, 158, 255, 0.1);
  transition: background 0.3s ease;
}

/* 收入统计样式优化 */
.income-box {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 30px;
}

.income-item {
  flex: 1;
  text-align: center;
  padding: 15px;
  background: rgba(255, 255, 255, 0.08);
  border-radius: 15px;
  margin: 0 10px;
  transition: all 0.3s ease;
}

.income-item:hover {
  background: rgba(255, 255, 255, 0.12);
  transform: translateY(-3px);
}

.income-title {
  color: rgba(255, 255, 255, 0.8);
  font-size: 14px;
  margin-bottom: 8px;
}

.income-title span {
  color: rgba(255, 255, 255, 0.6);
  font-size: 12px;
  margin-left: 4px;
}

.income-value {
  background: linear-gradient(45deg, #409EFF, #36D1DC);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  font-size: 24px;
  font-weight: bold;
  text-shadow: 0 0 10px rgba(64, 158, 255, 0.3);
}

/* 根据数值大小动态调整字体大小 */
.income-value {
  font-size: 20px;  /* 默认大小 */
}

/* 当数值较大时缩小字体 */
.income-value[data-length="7"] {
  font-size: 18px;
}

.income-value[data-length="8"] {
  font-size: 16px;
}

.income-value[data-length="9"] {
  font-size: 14px;
}

/* 长时间停车记录样式 */
.duration-warning {
  color: #E6A23C !important;
}

.status-normal {
  color: #67C23A !important;
}

.status-warning {
  color: #E6A23C !important;
}

.status-danger {
  color: #F56C6C !important;
}

/* 确保滚动效果正常工作 */
.wrap {
  height: calc(100% - 40px);
  overflow: hidden;
}

.wrap ul {
  animation: scroll 20s linear infinite;
  margin: 0;
  padding: 0;
}

.wrap li {
  padding: 10px 0;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.wrap li p {
  margin: 0;
  display: grid;
  grid-template-columns: 2fr 1.5fr 2fr 1fr;
  text-align: center;
}

.wrap li span {
  padding: 0 5px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

/* 欠费车辆统计样式 */
.debt-table,
.long-time-table {
  width: 100%;
  height: 100%;
  color: #fff;
}

.debt-header,
.long-time-header {
  display: grid;
  grid-template-columns: 2fr 1.5fr 1.5fr 2fr;
  padding: 10px;
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(5px);
  border-radius: 8px 8px 0 0;
  font-weight: bold;
  position: sticky;
  top: 0;
  z-index: 1;
}

.debt-body,
.long-time-body {
  height: calc(100% - 40px);
  overflow: hidden;
  position: relative;
}

.debt-body ul,
.long-time-body ul {
  margin: 0;
  padding: 0;
  list-style: none;
  position: absolute;
  width: 100%;
  animation: scrollUpDown 20s linear infinite;
}

.debt-body li,
.long-time-body li {
  display: grid;
  grid-template-columns: 2fr 1.5fr 1.5fr 2fr;
  padding: 8px 10px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.debt-body li span,
.long-time-body li span {
  text-align: center;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  padding: 0 5px;
}

/* 修改滚动动画 */
@keyframes scrollUpDown {
  0% {
    transform: translateY(0);
  }
  100% {
    transform: translateY(-50%);
  }
}

/* 鼠标悬停时暂停动画 */
.debt-body:hover ul,
.long-time-body:hover ul {
  animation-play-state: paused;
}

/* 状态样式 */
.debt-amount {
  color: #F56C6C !important;
  font-weight: bold;
}

.debt-status {
  color: #E6A23C !important;
}

.duration-warning {
  color: #E6A23C !important;
  font-weight: bold;
}

/* 优化滚动效果 */
.debt-body,
.long-time-body {
  mask-image: linear-gradient(
    to bottom,
    transparent 0%,
    black 5%,
    black 95%,
    transparent 100%
  );
  //-webkit-mask-image: linear-gradient(
  //  to bottom,
  //  transparent 0%,
  //  black 5%,
  //  black 95%,
  //  transparent 100%
  //);
}

/* 确保内容在容器内完整显示 */
.navboxall {
  height: calc(100% - 50px);
  overflow: hidden;
}

/* 修改表头文字颜色 */
.debt-header span,
.long-time-header span {
  color: rgba(255, 255, 255, 0.9);
  font-weight: bold;
}

/* 修改内容文字颜色，保持特殊状态颜色不变 */
.debt-body li span,
.long-time-body li span {
  color: rgba(255, 255, 255, 0.8);
}

/* 保持特殊状态的颜色不变 */
.debt-body li span.debt-amount {
  color: #F56C6C !important; /* 欠费金额保持红色 */
}

.debt-body li span.debt-status {
  color: #E6A23C !important; /* 状态保持橙色 */
}

.long-time-body li span.duration-warning {
  color: #E6A23C !important; /* 停车时长警告保持红色 */
}

/* 修改列布局 */
.clearfix {
  display: flex;
  justify-content: space-between;
  gap: 20px;
}

.clearfix > li {
  flex: 1;
}

/* 调整中间列的宽度和样式 */
.center-column {
  flex: 1.2 !important; /* 让中间列稍微宽一些 */
  display: flex;
  flex-direction: column;
}

/* 趋势图表容器样式 */
.trend-chart {
  flex-grow: 1;
  margin: 20px 0;
  background: rgba(0, 0, 0, 0.2) !important; /* 稍微深一点的背景 */
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
}

/* 确保图表容器充分利用空间 */
.trend-chart .navboxall {
  height: calc(100% - 40px);
}

/* 适配小屏幕 */
@media screen and (max-width: 1600px) {
  .clearfix {
    flex-wrap: nowrap; /* 防止换行 */
  }

  .center-column {
    flex: 1.5 !important; /* 在小屏幕上进一步增加中间列的比例 */
  }
}

/* 优化图表标题样式 */
.trend-chart .alltitle {
  font-size: 16px;
  padding: 15px;
  background: #304156;
  border-radius: 5px 5px 0 0;
}

/* 优化基础容器样式 */
.screen-container {
  width: 100%;
  min-height: 100%;
  padding: 20px;
  box-sizing: border-box;
  background: linear-gradient(to bottom, #1a1a1a, #2d3a4b);
}

.mainbox {
  width: 100%;
  min-height: calc(100vh - 120px);
}

/* 优化头部样式 */
.head {
  text-align: center;
  padding: 20px 0;
}

.head h1 {
  color: #fff;
  font-size: 28px;
  text-shadow: 0 0 10px rgba(255,255,255,0.3);
  margin: 0;
}

/* 优化卡片样式 */
.boxall {
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 10px;
  box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.37);
  margin-bottom: 20px;
  transition: transform 0.3s ease;
}

.boxall:hover {
  transform: translateY(-5px);
}

.alltitle {
  color: #fff;
  font-size: 16px;
  padding: 15px;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 10px 10px 0 0;
}

/* 优化收入展示框样式 */
.income-box {
  padding: 20px;
}

.income-item {
  background: rgba(255, 255, 255, 0.08);
  border-radius: 15px;
  padding: 15px;
  transition: all 0.3s ease;
}

.income-item:hover {
  background: rgba(255, 255, 255, 0.12);
  transform: translateY(-3px);
}

.income-value {
  background: linear-gradient(45deg, #409EFF, #36D1DC);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  font-size: 24px;
  font-weight: bold;
}

/* 优化表格样式 */
.record-header, .debt-header {
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(5px);
  border-radius: 8px 8px 0 0;
}

.record-body li, .debt-body li {
  transition: background 0.3s ease;
}

.record-body li:hover, .debt-body li:hover {
  background: rgba(255, 255, 255, 0.08);
}

/* 优化滚动条样式 */
::-webkit-scrollbar {
  width: 6px;
  height: 6px;
}

::-webkit-scrollbar-track {
  background: rgba(255, 255, 255, 0.05);
  border-radius: 3px;
}

::-webkit-scrollbar-thumb {
  background: rgba(255, 255, 255, 0.2);
  border-radius: 3px;
}

::-webkit-scrollbar-thumb:hover {
  background: rgba(255, 255, 255, 0.3);
}

/* 调整文字颜色 */
.income-title {
  color: rgba(255, 255, 255, 0.8);
  font-size: 14px;
  margin-bottom: 8px;
}

.income-title span {
  color: rgba(255, 255, 255, 0.6);
  font-size: 12px;
  margin-left: 4px;
}

.record-header span,
.debt-header span {
  font-weight: bold;
  text-align: center;
}

.record-body li span,
.debt-body li span {
  color: rgba(255, 255, 255, 0.8);
}

.admin-name {
  color: rgba(255, 255, 255, 0.9);
  font-size: 14px;
  margin-bottom: 5px;
}

.admin-info {
  color: rgba(255, 255, 255, 0.7);
}

/* 特殊状态的文字颜色 */
.debt-amount {
  color: #ff6b6b !important;
}

.debt-status {
  color: #ffd93d !important;
}

.duration-warning {
  color: #ffd93d !important;
}

.status-normal {
  color: #6ce675 !important;
}

.status-warning {
  color: #ffd93d !important;
}

.status-danger {
  color: #ff6b6b !important;
}

/* 表格hover效果 */
.record-body li:hover span,
.debt-body li:hover span {
  color: rgba(255, 255, 255, 1);
}

/* 确保图表文字可见 */
.trend-chart .alltitle {
  color: rgba(255, 255, 255, 0.9);
}

/* 调整加载文字颜色 */
.loadbox {
  color: rgba(255, 255, 255, 0.9);
}

/* 调整服务人员表格文字颜色 */
.debt-header {
  color: #676f7d;
}

.debt-header span {
  color: #676f7d;
}

.debt-body li {
  color: #676f7d;
}

.debt-body li span {
  color: #676f7d;
}

/* 服务人员表格特定字段颜色 */
.debt-body li span.name {
  color: #676f7d;
}

.debt-body li span.serviceType {
  color: #ffd93d;  /* 擅长服务使用醒目的黄色 */
}

.debt-body li span.experience {
  color: #67C23A;  /* 经验使用绿色 */
}

/* 表格标题文字颜色 */
.alltitle {
  color: #676f7d;
  font-weight: bold;
}

/* 确保所有表头文字清晰可见 */
.record-header,
.debt-header,
.long-time-header {
  color: #676f7d;
  font-weight: bold;
}

/* 表格内容文字颜色 */
.record-body li span,
.debt-body li span,
.long-time-body li span {
  color: #676f7d;
}

/* Hover 效果 */
.record-body li:hover span,
.debt-body li:hover span,
.long-time-body li:hover span {
  color: #ffffff;
}

/* 评论文字颜色 */
.admin-item {
  color: #676f7d;
}

.admin-name {
  color: #676f7d;
}

.admin-info {
  color: #d1d1d1;
}

/* 收入数据文字颜色 */
.income-title {
  color: #676f7d;
}

.income-title span {
  color: #d1d1d1;
}



/* 加载提示文字颜色 */
.loadbox {
  color: #e1e1e1;
}

</style>
