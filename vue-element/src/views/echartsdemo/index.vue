<template>
   <div class="chart-container">
     <div id="container" style="height: 100%"></div>
   </div>
</template>

<script>
    import echarts from 'echarts' //引入echart内容

    export default {
        name: "index",
      created(){//数据在加入前获取


      },
      mounted(){
        this.tests();//再渲染后执行
      },

        methods: {
          tests() {
            debugger
            const dom = document.getElementById('container')

               var myChart = echarts.init(dom, null, {
                 renderer: 'canvas',
                 useDirtyRect: false
               });
               var app = {};

               var option;

               option = {
                 title: {
                   text: '社团统计分析',
                   subtext: '展示数据',
                   left: 'center'
                 },
                 tooltip: {
                   trigger: 'item'
                 },
                 legend: {
                   orient: 'vertical',
                   left: 'left'
                 },
                 series: [
                   {
                     name: 'Access From',
                     type: 'pie',
                     radius: '50%',
                     data: [
                       { value: 1048, name: 'Search Engine' },
                       { value: 735, name: 'Direct' },
                       { value: 580, name: 'Email' },
                       { value: 484, name: 'Union Ads' },
                       { value: 300, name: 'Video Ads' }
                     ],
                     emphasis: {
                       itemStyle: {
                         shadowBlur: 10,
                         shadowOffsetX: 0,
                         shadowColor: 'rgba(0, 0, 0, 0.5)'
                       }
                     }
                   }
                 ]
               };

               if (option && typeof option === 'object') {
                 myChart.setOption(option);
               }

               window.addEventListener('resize', myChart.resize);
             }
          }

    }
</script>

<style scoped>
  .chart-container{
    position: relative;
    width: 100%;
    height: calc(100vh - 84px);
  }
</style>
