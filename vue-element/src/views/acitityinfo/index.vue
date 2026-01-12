<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.name" placeholder="社团名称" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />

      <el-input v-model="listQuery.tel" placeholder="联系电话" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />

      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        高级查询
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit"
                 @click="handleCreate" v-if="role!='Admin'">
        发布活动
      </el-button>

    </div>

    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
      @sort-change="sortChange"
    >
      <el-table-column label="ID" prop="id" sortable="custom" align="center" width="80" :class-name="getSortClass('id')">
        <template slot-scope="{row}">
          <span>{{ row.id }}</span>
        </template>
      </el-table-column>

      <el-table-column label="活动名称" width="130px">
        <template slot-scope="{row}">
          <span>{{ row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="所属社团" width="130px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.team.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="电话" width="130px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.tel }}</span>
        </template>
      </el-table-column>
      <el-table-column label="活动时间" width="180px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.acTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>

      <el-table-column label="主持人" width="130px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.host }}</span>
        </template>
      </el-table-column>

      <el-table-column label="活动地址" width="130px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.ctAddress }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" class-name="status-col" width="100px">
        <template slot-scope="{row}">
          <el-tag :type="row.status | statusFilter">
            <span v-if="row.status==1">审核通过</span>
            <span v-if="row.status==0">未审核</span>
            <span v-if="row.status==2">未拒绝</span>
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="260" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">


          <el-button type="primary" size="mini" @click="handleUpdate(row)" v-if="row.status!=1">
            编辑
          </el-button>

          <el-button  size="mini" type="danger" @click="handleDelete(row,$index)" v-if="row.status!=1">
            删除
          </el-button>

          <el-button  size="mini" type="danger" @click="handleUpdate2(row,true)" v-if="row.status==1">
            查看详细
          </el-button>

          <el-button type="primary" size="mini" @click="updateStatus(row)"
                     v-if="row.status!=1 && type==1 && role=='Admin'">
            审核
          </el-button>

        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="70px" style="width: 400px; margin-left:50px;">
        <el-form-item label="活动名称" >
          <el-input v-model="temp.name" />
        </el-form-item>
        <el-form-item label="活动电话" >
          <el-input v-model="temp.tel" />
        </el-form-item>

        <el-form-item label="活动主持" >
          <el-input v-model="temp.host" />
        </el-form-item>



        <el-form-item label="活动时间" >
          <el-date-picker v-model="temp.acTime"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="Please pick a date" />
        </el-form-item>

        <el-form-item label="活动地址">
          <el-input v-model="temp.ctAddress" :autosize="{ minRows: 2, maxRows: 4}" type="textarea" placeholder="Please input" />
        </el-form-item>
        <el-form-item label="活动介绍">
          <el-input v-model="temp.ctInfo" :autosize="{ minRows: 2, maxRows: 4}" type="textarea" placeholder="Please input" />
        </el-form-item>


        <el-form-item label="宣传图片">
            <el-upload
              class="avatar-uploader"
              action="http://localhost:9999/team/fileUpload"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">
              <img v-if="imageUrl" :src="imageUrl" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
        </el-form-item>


      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          取消
        </el-button>
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()"
                   v-if="dialogStatus==='update' && flag==false || (dialogStatus==='create' && flag==false)">
          提交
        </el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="dialogPvVisible" title="Reading statistics">
      <el-table :data="pvData" border fit highlight-current-row style="width: 100%">
        <el-table-column prop="key" label="Channel" />
        <el-table-column prop="pv" label="Pv" />
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogPvVisible = false">Confirm</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>

<script>
import { fetchList, fetchPv, createArticle, updateArticle } from '@/api/article'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination'
import request from "@/utils/request"; // secondary package based on el-pagination



export default {
  name: 'ComplexTable',
  components: { Pagination },
  directives: { waves },
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'info',
        deleted: 'danger'
      }
      return statusMap[status]
    },
    typeFilter(type) {
      return calendarTypeKeyValue[type]
    }
  },
  data() {
    return {
      imageUrl: '',
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        name: undefined,
        tel:undefined
      },

      //修改和详细展示的标识
      flag:false,
      type:1,
      //获取登录角色
      role:localStorage.getItem("roles"),

      importanceOptions: [1, 2, 3],

      sortOptions: [{ label: 'ID Ascending', key: '+id' }, { label: 'ID Descending', key: '-id' }],
      statusOptions: [
        {id:0,value:"禁用"},{id:1,value:"正常"}
      ],
      showReviewer: false,
      temp: {
        id: undefined,
        name:'',
        timestamp: new Date(),
        type: '',
        status: 0,
        images:''
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑活动',
        create: '发布活动'
      },
      dialogPvVisible: false,
      pvData: [],
      downloadLoading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    handleAvatarSuccess(res, file) {
      // this.imageUrl = URL.createObjectURL(file.raw);
      this.imageUrl ="http://localhost:9999/"+res.msg;
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/png';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 png 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },

    //审核请求
    updateStatus(row){
      request('activity/updateStatusById?id='+row.id).then(res=>{
        if(res.code == 20000){
          this.$notify({
            title: 'Success',
            message: '审核成功',
            type: 'success',
            duration: 2000
          })
          this.getList();
        }
      })

    },


    getList() {
      /**
       * 1、发送请求 返回结果集合
       * 2、根据结果集合渲染页面
       * 3、把加载框取消为false
       */
      this.listLoading = true;//没有信息之前显示加载内容
      console.log(this.listQuery)
      debugger
      request(
        {
          url : 'activity/queryAcitivityInfoList',
          method:'get',
          params:this.listQuery
        }).then(res=>{
          if(res.code==20000){
              this.list=res.data;
              this.total=res.total;
             this.listLoading = false;
          }
      }).catch(err=>{

      })

    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    handleModifyStatus(row, status) {
      this.$message({
        message: '操作Success',
        type: 'success'
      })
      row.status = status
    },
    sortChange(data) {
      const { prop, order } = data
      if (prop === 'id') {
        this.sortByID(order)
      }
    },
    sortByID(order) {
      if (order === 'ascending') {
        this.listQuery.sort = '+id'
      } else {
        this.listQuery.sort = '-id'
      }
      this.handleFilter()
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        importance: 1,
        remark: '',
        timestamp: new Date(),
        title: '',
        status: '',
        type: '',
        imageUrl:''
      }
    },
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {

      /**
       * 1、获取form单中的数据信息
       * 2、向后端服务器发送请求  完成请求操作
       * 3、服务器端处理完成后 返回状态码
       * 4、前端如果接收 添加成功
       *    进行table刷新 弹出框关闭
       */
      this.temp.images=this.imageUrl;//图片变量的赋值操作
      debugger
      console.log(this.temp);
      request.post('activity/addInfo',this.temp).then(res=>{
        if(res.code==20000){
              this.dialogFormVisible = false
              this.$notify({
                title: 'Success',
                message: '发布活动成功',
                type: 'success',
                duration: 2000
              })
          this.getList();//刷新table组件
        }
      })

    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row) // copy obj
      this.imageUrl=row.images;  //把获取到的记录信息进行重新渲染值
      this.temp.timestamp = new Date(this.temp.timestamp)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.flag=false
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },

    handleUpdate2(row,flag) {
      debugger
      this.temp = Object.assign({}, row) // copy obj
      this.imageUrl=row.images;  //把获取到的记录信息进行重新渲染值
      this.dialogStatus = 'update'
      this.flag=flag;
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      this.temp.images=this.imageUrl;//图片变量的赋值操作
      debugger
      console.log(this.temp);
      request.post('activity/updateInfo',this.temp).then(res=>{
        if(res.code==20000){
          this.dialogFormVisible = false
          this.$notify({
            title: 'Success',
            message: '新建社团成功',
            type: 'success',
            duration: 2000
          })
          this.getList();//刷新table组件
        }
      })

    },
    handleDelete(row, index) {
      /**
       * 1、获取要删除记录的id 主键
       * 2、发送请求给后端服务器
       * 3、前后端根据后端服务器返回结果判断是否成功
       * 4、成功的话 重新加载table 展示最新内容
       */
      request('activity/deleteById?id='+row.id).then(res=>{
        if(res.code == 20000){
          this.$notify({
            title: 'Success',
            message: '删除成功',
            type: 'success',
            duration: 2000
          })
          this.getList();
        }
      })

    },
    handleFetchPv(pv) {
      fetchPv(pv).then(response => {
        this.pvData = response.data.pvData
        this.dialogPvVisible = true
      })
    },
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['timestamp', 'title', 'type', 'importance', 'status']
        const filterVal = ['timestamp', 'title', 'type', 'importance', 'status']
        const data = this.formatJson(filterVal)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: 'table-list'
        })
        this.downloadLoading = false
      })
    },
    formatJson(filterVal) {
      return this.list.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
    },
    getSortClass: function(key) {
      const sort = this.listQuery.sort
      return sort === `+${key}` ? 'ascending' : 'descending'
    }
  }
}
</script>
